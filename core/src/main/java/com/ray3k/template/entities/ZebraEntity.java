package com.ray3k.template.entities;

import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.Event;
import com.ray3k.template.*;
import com.ray3k.template.Resources.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineZebra.*;
import static com.ray3k.template.Resources.Values.*;

public class ZebraEntity extends Entity implements Enemy {
    private enum Mode {
        SHOOT, FLYING_SHOOT, FLYING_TAIL, HIDE
    }
    private float timer;
    private float timer2;
    private float health;
    private float targetX;
    private float targetY;
    private static ZebraEntity zebra;
    
    private Mode mode;
    
    @Override
    public void hurt(float damage, float force, float forceDirection) {
        health -= damage;
        if (health <= 0) {
            destroy = true;
            var die = new DieAnimEntity(skeletonData, animationData, animationState.getCurrent(0).getAnimation(),
                    animationState.getCurrent(0).getTrackTime(), animationDie, x, y,
                    skeleton.getRootBone().getRotation(), true);
            entityController.add(die);
        }
        gravityY = zebraGravity;
        animationState.setAnimation(1, animationHurt, false);
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
        zebra = this;
        bossAlive = true;
        health = zebraHealth;
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationStand, true);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, zebraFilter);
        mode = Mode.SHOOT;
        depth = DEPTH_ENEMY;
        gravityY = zebraGravity;
    
        animationState.addListener(new AnimationStateAdapter() {
            @Override
            public void event(TrackEntry entry, Event event) {
                if (event.getData().getName().equals("shoot")) {
                    var player = PlayerEntity.player;
                    var bone = skeleton.findBone("weapon");
                    var weaponX = x + bone.getX();
                    var weaponY = y + bone.getY();
                    float playerDirection = Utils.pointDirection(weaponX, weaponY, player.getBboxCenterX(), player.getBboxCenterY());
                    var proj = new EnemyProjectileEntity(SpineGluttonProjectile.skeletonData,
                            SpineGluttonProjectile.animationData, SpineGluttonProjectile.animationAnimation);
                    entityController.add(proj);
                    proj.teleport(weaponX, weaponY);
                    proj.collideWithBounds = true;
                    proj.damage = zebraDamage;
                    proj.force = zebraBulletForce;
                    proj.forceDirection = 180 - zebraForceDirection;
                    proj.useVelocityAsForceDirection = false;
                    proj.setMotion(zebraBulletSpeed, playerDirection);
                } else if (event.getData().getName().equals("strike")) {
                    var proj = new EnemyProjectileEntity(-30, -400, 60, 400);
                    entityController.add(proj);
                    proj.teleport(x, y);
                    proj.canBeDeactivated = false;
                    proj.life = .2f;
                    proj.damage = zebraDamage;
                    proj.force = zebraForce;
                    proj.forceDirection = 180 - zebraForceDirection;
                    proj.useVelocityAsForceDirection = false;
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
        boolean playerToRight = playerDirection < 90 || playerDirection > 270;
        
        if (mode == Mode.SHOOT) {
            timer -= delta;
            if (timer < 0) {
                animationState.setAnimation(2, animationShoot, false);
                timer = MathUtils.random(zebraBulletDelayMin,  zebraBulletDelayMax);
            }
            if (player.health <= 50) {
                targetX = x;
                targetY = y;
                gravityY = 0;
                randomAttack();
                animationState.setAnimation(3, animationFly, true);
            }
        } else if (mode == Mode.FLYING_SHOOT) {
            timer -= delta;
            timer2 -= delta;
    
            gravityY = 0;
            moveTowards(zebraMoveSpeed, player.x + 200, targetY + zebraFlyShootHeight, delta);
            
            if (timer < 0) {
                animationState.setAnimation(2, animationShoot, false);
                timer = MathUtils.random(zebraBulletDelayMin,  zebraBulletDelayMax);
            }
            if (timer2 < 0) {
                randomAttack();
            }
            if (player.health < 10) mode = Mode.HIDE;
        } else if (mode == Mode.FLYING_TAIL) {
            timer2 -= delta;
    
            gravityY = 0;
            moveTowards(zebraMoveSpeed, player.x, targetY + zebraFlyTailHeight, delta);

            if (timer2 < 0) {
                animationState.setEmptyAnimation(4, 0);
                randomAttack();
            }
            if (player.health < 10) mode = Mode.HIDE;
        } else if (mode == Mode.HIDE) {
            System.out.println("hide");
            animationState.setEmptyAnimation(2, 0);
            animationState.setEmptyAnimation(4, 0);
            moveTowards(zebraMoveSpeed, x, 100000, delta);
            if (isOutside(camera.position.x - camera.viewportWidth * camera.zoom / 2, camera.position.y - camera.viewportHeight * camera.zoom / 2,
                    camera.viewportWidth * camera.zoom, camera.viewportHeight * camera.zoom, 50)) {
                destroy = true;
                var fly = new ZebraFlyEntity();
                entityController.add(fly);
                fly.setPosition(camera.position.x - camera.viewportWidth * camera.zoom / 2, camera.position.y - camera.viewportHeight * camera.zoom / 2);
            }
        }
    }
    
    private void randomAttack() {
        int index = MathUtils.random(1);
        
        switch (index) {
            case 0:
                timer = MathUtils.random(zebraBulletDelayMin,  zebraBulletDelayMax);
                timer2 = zebraFlyDelay;
                mode = Mode.FLYING_SHOOT;
                break;
            case 1:
                animationState.setAnimation(4, animationStrike, true);
                timer2 = zebraFlyDelay;
                mode = Mode.FLYING_TAIL;
                break;
        }
    }
    
    @Override
    public void draw(float delta) {
    
    }
    
    @Override
    public void destroy() {
    
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
                if (mode != Mode.HIDE) PlayerEntity.player.hurt(zebraDamage, zebraForce, playerToRight ? zebraForceDirection : 180 - zebraForceDirection);
            } else if (collision.other.userData instanceof BoundsEntity) {
                if (collision.normal.x != 0) deltaX = 0;
                if (collision.normal.y == 1) {
                    gravityY = 0;
                    deltaY = 0;
                }
            }
        }
    }
    
    private final static ZebraFilter zebraFilter = new ZebraFilter();
    
    private static class ZebraFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (zebra.mode != Mode.HIDE) {
                if (other.userData instanceof PlayerEntity) return Response.cross;
                if (other.userData instanceof BoundsEntity) return Response.bounce;
            }
            return null;
        }
    }
}
