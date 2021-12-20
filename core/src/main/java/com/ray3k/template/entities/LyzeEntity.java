package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.Event;
import com.esotericsoftware.spine.Slot;
import com.ray3k.template.Resources.*;
import com.ray3k.template.*;

import java.util.ArrayList;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpineLyze.*;
import static com.ray3k.template.Resources.Values.*;

public class LyzeEntity extends Entity implements Enemy {
    private float timer;
    private float bulletTimer;
    private float health;
    private Slot bbox;
    private final static Vector2 temp = new Vector2();
    public final Array<Vector2> points = new Array<>();
    private final Vector2 target = new Vector2();
    private boolean hittable;
    
    @Override
    public void hurt(float damage, float force, float forceDirection) {
        if (hittable) {
            health -= damage;
            if (health <= 0) {
                destroy = true;
                var die = new DieAnimEntity(skeletonData, animationData, animationState.getCurrent(0).getAnimation(),
                        animationState.getCurrent(0).getTrackTime(), animationDie, x, y,
                        skeleton.getRootBone().getRotation(), true);
                entityController.add(die);
            }
            animationState.setAnimation(1, animationHurt, false);
            animationState.addEmptyAnimation(1, 0, 0);
        }
    }
    
    @Override
    public float getX() {
        return x;
    }
    
    @Override
    public float getY() {
        return y;
    }
    
    @Override
    public void create() {
        bossAlive = true;
        playBattle();
        health = lyzeHealth;
        setSkeletonData(skeletonData, animationData);

        animationState.setAnimation(0, animationJump, false);
        bbox = skeleton.findSlot("bbox");
        setCollisionBox(bbox, skeletonBounds, lyzeFilter);
        depth = DEPTH_ENEMY;
        timer = lyzeAnimDelay;
        target.set(points.first());
        
        animationState.addListener(new AnimationStateAdapter() {
            @Override
            public void event(TrackEntry entry, Event event) {
                if (event.getData().getName().equals("hittable")) {
                    hittable = Boolean.parseBoolean(event.getString());
                }
            }
        });
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        var player = PlayerEntity.player;
        float playerDirection = Utils.pointDirection(getBboxCenterX(), getBboxCenterY(), player.getBboxCenterX(), player.getBboxCenterY());
        boolean playerToRight = player.x > x;
        
        if (timer > 0) {
            timer -= delta;
            if (timer <= 0) {
                bulletTimer = lyzeBulletDelay;
                animationState.setAnimation(0, animationLand, false);
                teleport(target.x, target.y);
                playerToRight = player.x > x;
                skeleton.setScaleX(playerToRight ? 1 : -1);
            }
        }
        
        if (bulletTimer > 0) {
            bulletTimer -= delta;
            if (bulletTimer <= 0) {
                animationState.setAnimation(0, animationShoot, false);
                animationState.addAnimation(0, animationJump, false, 0);
                temp.set(0,0);
                skeleton.findBone("weapon").localToWorld(temp);
                var beam = new LyzeBeamEntity();
                entityController.add(beam);
                beam.teleport(temp.x, temp.y);
                beam.skeleton.setScaleX(2);
                boolean facingRight = skeleton.getScaleX() > 0;
                if (!facingRight) beam.skeleton.setScaleX(beam.skeleton.getScaleX() * -1);
                
                world.queryRay(temp.x, temp.y,facingRight ? 1 : -1,0,beamFilter, tempItems);
                if (tempItems.size() > 0) {
                    PlayerEntity.player.hurt(lyzeDamage, lyzeForce, facingRight ? lyzeForceDirection : 180 - lyzeForceDirection);
                }
                
                randomAttack();
            }
        }
    }
    
    public static final ArrayList<Item> tempItems = new ArrayList<>();
    
    private void randomAttack() {
        timer = lyzeAnimDelay;
        int index = MathUtils.random(points.size - 1);
        target.set(points.get(index));
    }
    
    @Override
    public void draw(float delta) {
        if (Values.debugging) {
            shapeDrawer.setColor(Color.ORANGE);
            shapeDrawer.filledRectangle(getBboxLeft(), getBboxBottom(), bboxWidth, bboxHeight);
        }
    }
    
    @Override
    public void destroy() {
        bossAlive = false;
        playExplore();
        defeatedLyze = true;
        sfx_bossDie.play(sfx);
    }
    
    @Override
    public void projectedCollision(Result result) {
    
    }
    
    @Override
    public void collision(Collisions collisions) {
        if (hittable) {
            var player = PlayerEntity.player;
            float playerDirection = Utils.pointDirection(getBboxCenterX(), getBboxCenterY(), player.getBboxCenterX(),
                    player.getBboxCenterY());
            boolean playerToRight = playerDirection < 90 || playerDirection > 270;

            for (int i = 0; i < collisions.size(); i++) {
                var collision = collisions.get(i);
                if (collision.other.userData instanceof PlayerEntity) {
                    PlayerEntity.player.hurt(lyzeDamage, lyzeForce,
                            playerToRight ? lyzeForceDirection : 180 - lyzeForceDirection);
                }
            }
        }
    }
    
    private final static LyzeFilter lyzeFilter = new LyzeFilter();
    
    private static class LyzeFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof PlayerEntity) return Response.cross;
            
            return null;
        }
    }
    
    private final static BeamFilter beamFilter = new BeamFilter();
    
    private static class BeamFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (item.userData instanceof PlayerEntity) return Response.cross;
            
            return null;
        }
    }
}
