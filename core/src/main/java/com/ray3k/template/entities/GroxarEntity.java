package com.ray3k.template.entities;

import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineGroxar.*;
import static com.ray3k.template.Resources.Values.*;

public class GroxarEntity extends Entity implements Enemy {
    private enum Mode {
        STATUE, CRACKING, DASH_ATTACK, LEAP_ATTACK, HOPPING_ATTACK
    }
    private float timer;
    private float health;
    
    private Mode mode;
    
    @Override
    public void hurt(float damage, float force, float forceDirection) {
        if (mode != Mode.STATUE && mode != Mode.CRACKING) {
            addMotion(force, forceDirection);
            health -= damage;
            if (health <= 0) {
                destroy = true;
                var die = new DieAnimEntity(skeletonData, animationData, animationState.getCurrent(0).getAnimation(),
                        animationState.getCurrent(0).getTrackTime(), animationDie, x, y,
                        skeleton.getRootBone().getRotation(), true);
                entityController.add(die);
            }
            gravityY = groxarGravity;
            animationState.setAnimation(1, animationHurt, false);
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
        health = groxarHealth;
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationStatue, true);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, groxarFilter);
        mode = Mode.STATUE;
        depth = DEPTH_ENEMY;
        animationState.addListener(new AnimationStateAdapter() {
            @Override
            public void complete(TrackEntry entry) {
                if (entry.getAnimation() == animationStatueBreak) {
                    mode = Mode.DASH_ATTACK;
                    timer = groxarDashDelay;
                }
            }
        });
        gravityY = groxarGravity;
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        boolean inAir = world.check(item, getBboxLeft(), getBboxBottom() - 10, groundFilter).projectedCollisions.size() == 0;
        
        var player = PlayerEntity.player;
        float playerDirection = Utils.pointDirection(getBboxCenterX(), getBboxCenterY(), player.getBboxCenterX(), player.getBboxCenterY());
        boolean playerToRight = playerDirection < 90 || playerDirection > 270;
        
        if (!inAir) deltaX = Utils.approach(deltaX, 0, groxarGroundFriction * delta);
        
        if (mode == Mode.DASH_ATTACK) {
            timer -= delta;
            if (timer < 0) {
                setMotion(groxarDashSpeed, playerDirection);
                gravityY = groxarGravity;
                skeleton.setScaleX(player.x > x ? -1 : 1);
                randomAttack();
            }
        } else if (mode == Mode.LEAP_ATTACK) {
            timer -= delta;
            if (timer < 0 && !inAir) {
                setMotion(groxarLeapSpeed, playerToRight ? groxarLeapAngle : 180 - groxarLeapAngle);
                gravityY = groxarGravity;
                skeleton.setScaleX(player.x > x ? -1 : 1);
                randomAttack();
            }
        } else if (mode == Mode.HOPPING_ATTACK) {
            if (!inAir) {
                setMotion(groxarHoppingSpeed, playerToRight ? groxarHoppingAngle : 180 - groxarHoppingAngle);
                gravityY = groxarGravity;
                skeleton.setScaleX(player.x > x ? -1 : 1);
            }
            timer -= delta;
            if (timer < 0) {
                randomAttack();
            }
        }
    }
    
    private void randomAttack() {
        int index = MathUtils.random(2);
        
        switch (index) {
            case 0:
                timer = groxarDashDelay;
                mode = Mode.DASH_ATTACK;
                break;
            case 1:
                timer = groxarLeapDelay;
                mode = Mode.LEAP_ATTACK;
                break;
            case 2:
                timer = groxarHoppingDelay;
                mode = Mode.HOPPING_ATTACK;
                break;
        }
    }
    
    @Override
    public void draw(float delta) {
    
    }
    
    @Override
    public void destroy() {
        bossAlive = false;
    }
    
    @Override
    public void projectedCollision(Result result) {
    
    }
    
    @Override
    public void collision(Collisions collisions) {
        var player = PlayerEntity.player;
        float playerDirection = Utils.pointDirection(getBboxCenterX(), getBboxCenterY(), player.getBboxCenterX(), player.getBboxCenterY());
        boolean playerToRight = playerDirection < 90 || playerDirection > 270;
        
        for (int i = 0; i < collisions.size(); i++) {
            var collision = collisions.get(i);
            if (collision.other.userData instanceof PlayerEntity) {
                if (mode == Mode.STATUE) {
                    animationState.setAnimation(0, animationStatueBreak, false);
                    animationState.addAnimation(0, animationStand, true, 0);
                    mode = Mode.CRACKING;
                } else if (mode != Mode.CRACKING) {
                    PlayerEntity.player.hurt(groxarDamage, groxarForce, playerToRight ? groxarForceDirection : 180 - groxarForceDirection);
                }
            } else if (collision.other.userData instanceof BoundsEntity) {
                if (collision.normal.x != 0) deltaX = 0;
                if (collision.normal.y == 1) {
                    gravityY = 0;
                    deltaY = 0;
                }
            }
        }
    }
    
    private final static GroxarFilter groxarFilter = new GroxarFilter();
    
    private static class GroxarFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof PlayerEntity) return Response.cross;
            if (other.userData instanceof  BoundsEntity) return Response.bounce;
            return null;
        }
    }
    
    private final static GroundFilter groundFilter = new GroundFilter();
    
    private static class GroundFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof  BoundsEntity) return Response.bounce;
            return null;
        }
    }
}
