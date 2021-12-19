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
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Event;
import com.ray3k.template.Resources.*;
import com.ray3k.template.*;
import com.ray3k.template.entities.PowerupEntity.*;
import com.ray3k.template.screens.*;

import java.util.ArrayList;

import static com.ray3k.template.Core.Binding.*;
import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineDragonQueen.*;
import static com.ray3k.template.Resources.Values.*;
import static com.ray3k.template.entities.PlayerEntity.Weapon.*;

public class PlayerEntity extends Entity {
    public static PlayerEntity player;
    public boolean selectingWeapon;
    private float jumpTime;
    private int jumps;
    public Weapon weapon;
    public static Array<Weapon> enabledWeapons = new Array<>();
    public static Array<PowerupType> enabledWings = new Array<>();
    private boolean shotgunCharge;
    private float damageTimer;
    public float health;
    
    public enum Weapon {
        WHIP, GRENADE, SHOTGUN, CROSS
    }
    
    private Bone weaponBone;
    private static final Vector2 temp = new Vector2();
    private static final ArrayList<Item> itemsTemp = new ArrayList<>();
    
    public void hurt(float damage, float force, float forceDirection) {
        if (damageTimer <= 0) {
            damageTimer = playerDamageTimer;
            health -= damage;
            addMotion(force, forceDirection);
            animationState.setAnimation(3, animationHurt, false);
            animationState.addEmptyAnimation(3, 0, 0);
            if (health <= 0) {
                var die = new DieAnimEntity(skeletonData, animationData, animationState.getCurrent(0).getAnimation(), animationState.getCurrent(0).getTrackTime(), animationDie, x, y, skeleton.getRootBone().getRotation(), false);
                entityController.add(die);
                die.depth = DEPTH_DEATH_ANIMATION;
                die.reloadOnDeath = true;
                var bone = die.skeleton.findBone("white");
                bone.setScale(camera.viewportWidth * camera.zoom, camera.viewportHeight * camera.zoom);
                bone.setPosition(camera.position.x - x, camera.position.y - y);
                
                destroy = true;
            }
        }
    }
    
    @Override
    public void create() {
        player = this;
        depth = DEPTH_PLAYER;
        health = playerMaxHealth;
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationStand, true);
        animationData.setDefaultMix(.2f);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, collisionFilter);
        gravityY = playerGravity;
        weapon = WHIP;
        weaponBone = skeleton.findBone("weapon");
        animationState.addListener(new AnimationStateAdapter() {
            @Override
            public void event(TrackEntry entry, Event event) {
                var name = event.getData().getName();
                
                switch (name) {
                    case "spark":
                        temp.set(0, 0);
                        weaponBone.localToWorld(temp);
                        itemsTemp.clear();
                        boolean aimRight = skeleton.getScaleX() > 0;
                        if (aimRight) world.queryRect(temp.x + 100, temp.y - whipDetectHeight / 2f, whipDetectWidth, whipDetectHeight,
                                whipFilter, itemsTemp);
                        else world.queryRect(temp.x - 100 - whipDetectWidth, temp.y - whipDetectHeight / 2f, whipDetectWidth, whipDetectHeight,
                                whipFilter, itemsTemp);
                        
                        for (var item : itemsTemp) {
                            if (item.userData instanceof Enemy) {
                                var enemy = (Enemy) item.userData;
                                enemy.hurt(whipDamage, whipForce, aimRight ? whipForceDirection : 180 - whipForceDirection);
                            } else if (item.userData instanceof EnemyProjectileEntity) {
                                var proj = (EnemyProjectileEntity) item.userData;
                                if (proj.canBeDeactivated) {
                                    proj.deactivated = true;
                                    proj.setDirection(MathUtils.random(360f));
                                }
                            }
                        }
                        
                        for (int i = 0; i < 20; i++) {
                            float offset = 100 + MathUtils.random(300f);
                            if (!aimRight) offset *= -1;
                            var spark = new SparkEntity();
                            spark.teleport(temp.x + offset, temp.y);
                            entityController.add(spark);
                        }
                        break;
                    case "throw":
                        if (weapon == CROSS) {
                            var goRight = skeleton.getScaleX() > 0;
                            var cross = new CrossEntity(goRight);
                            temp.set(0, 0);
                            weaponBone.localToWorld(temp);
                            cross.teleport(temp.x, temp.y);
                            entityController.add(cross);
                        } else if (weapon == GRENADE) {
                            var goRight = skeleton.getScaleX() > 0;
                            var grenade = new GrenadeEntity(goRight);
                            temp.set(0, 0);
                            weaponBone.localToWorld(temp);
                            grenade.teleport(temp.x, temp.y);
                            entityController.add(grenade);
                            grenade.addMotion(getSpeed(), getDirection());
                        }
                        break;
                    case "shoot":
                        var goRight = skeleton.getScaleX() > 0;
                        if (shotgunCharge) {
                            float angle = goRight ? shotgunChargeAngle : 180 - shotgunChargeAngle;
                            var bullet = new ShotgunBulletEntity();
                            temp.set(0, 0);
                            weaponBone.localToWorld(temp);
                            bullet.teleport(temp.x, temp.y);
                            entityController.add(bullet);
                            bullet.setMotion(shotgunProjectileSpeed, angle);
                            bullet.skeleton.getRootBone().setRotation(angle);
    
                            angle = goRight ? 0 : 180;
                            bullet = new ShotgunBulletEntity();
                            temp.set(0, 0);
                            weaponBone.localToWorld(temp);
                            bullet.teleport(temp.x, temp.y);
                            entityController.add(bullet);
                            bullet.setMotion(shotgunProjectileSpeed, angle);
                            bullet.skeleton.getRootBone().setRotation(angle);
    
                            angle = goRight ? 360 - shotgunChargeAngle : 180 + shotgunChargeAngle;
                            bullet = new ShotgunBulletEntity();
                            temp.set(0, 0);
                            weaponBone.localToWorld(temp);
                            bullet.teleport(temp.x, temp.y);
                            entityController.add(bullet);
                            bullet.setMotion(shotgunProjectileSpeed, angle);
                            bullet.skeleton.getRootBone().setRotation(angle);
                        } else {
                            var bullet = new ShotgunBulletEntity();
                            temp.set(0, 0);
                            weaponBone.localToWorld(temp);
                            bullet.teleport(temp.x, temp.y);
                            entityController.add(bullet);
                            bullet.setMotion(shotgunProjectileSpeed, goRight ? 0 : 180);
                        }
                        break;
                }
            }
        });
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        damageTimer -= delta;
        if (!isBindingPressed(ATTACK) || weapon != SHOTGUN) {
            shotgunCharge = false;
            if (animationState.getCurrent(2) != null) animationState.getCurrent(2).setTimeScale(1);
        }
        boolean inAir = world.check(item, x, y - 1, collisionFilter).projectedCollisions.size() == 0;
        
        if (!selectingWeapon && isBindingPressed(LEFT)) {
            if (deltaX > -playerMaxSpeed) {
                deltaX -= (inAir ? playerAccelerationWhileJumping : playerAcceleration) * delta;
                if (deltaX < -playerMaxSpeed) deltaX = -playerMaxSpeed;
            }
            if (!inAir && animationState.getCurrent(0).getAnimation() != animationRun)
                animationState.setAnimation(0, animationRun, true);
            skeleton.setScale(-1, 1);
        } else if (!selectingWeapon && isBindingPressed(RIGHT)) {
            if (deltaX < playerMaxSpeed) {
                deltaX += (inAir ? playerAccelerationWhileJumping : playerAcceleration) * delta;
                if (deltaX > playerMaxSpeed) deltaX = playerMaxSpeed;
            }
            if (!inAir && animationState.getCurrent(0).getAnimation() != animationRun)
                animationState.setAnimation(0, animationRun, true);
            skeleton.setScale(1, 1);
        } else {
            deltaX = Utils.approach(deltaX, 0, (inAir ? playerDecelerationWhileJumping : playerDeceleration) * delta);
            if (!inAir && animationState.getCurrent(0).getAnimation() != animationStand)
                animationState.setAnimation(0, animationStand, true);
        }
        
        if ((!inAir || jumps < playerMaxJumps) && !selectingWeapon && isBindingJustPressed(JUMP)) {
            jumpTime = 0;
            deltaY += playerJumpSpeed;
            animationState.setAnimation(0, animationJump, false);
            if (inAir) animationState.setAnimation(1, animationFlap, false);
            
            if (!inAir) jumps = 1;
            else jumps++;
            inAir = true;
        }
        
        if (inAir) {
            if (!selectingWeapon && isBindingPressed(JUMP)) {
                if (jumpTime < playerJumpHoldTime) {
                    if (deltaY < playerJumpSpeed) {
                        deltaY = playerJumpSpeed;
                    }
                }
            }
            jumpTime += delta;
        }
        
        if (!selectingWeapon && isBindingJustPressed(INVENTORY)) {
            GameScreen.gameScreen.showInventory();
            selectingWeapon = true;
        }
        
        if (isBindingJustPressed(ATTACK)) {
            var anim = animationState.getCurrent(2);
            Animation targetAnimation = null;
            
            switch (weapon) {
                case WHIP:
                    if (inAir) targetAnimation = animationWhipJump;
                    else targetAnimation = animationWhip;
                    break;
                case CROSS:
                    targetAnimation = animationThrow;
                    break;
                case GRENADE:
                    targetAnimation = animationThrow;
                    break;
                case SHOTGUN:
                    if (inAir) targetAnimation = animationShootJump;
                    else targetAnimation = animationShoot;
                    break;
            }
            
            if (targetAnimation != null && (anim == null || anim.getAnimation() != targetAnimation)) {
                animationState.setAnimation(2, targetAnimation, false);
                animationState.getCurrent(2).setAlpha(1);
                animationState.addEmptyAnimation(2, .2f, 0);
                if (weapon == SHOTGUN) {
                    shotgunCharge = true;
                    animationState.getCurrent(2).setTimeScale(shotgunChargeTimescale);
                }
            }
        }
    }
    
    @Override
    public void draw(float delta) {
        if (Values.debugging) {
            shapeDrawer.setColor(Color.PURPLE);
            shapeDrawer.filledRectangle(getBboxLeft(), getBboxBottom(), bboxWidth, bboxHeight);
        }
    }
    
    @Override
    public void destroy() {
    
    }
    
    @Override
    public void projectedCollision(Result result) {
    
    }
    
    @Override
    public void collision(Collisions collisions) {
        for (int i = 0; i < collisions.size(); i++) {
            var collision = collisions.get(i);
            if (collision.other.userData instanceof BoundsEntity) {
                if (collision.normal.x > 0 && deltaX < 0) deltaX = 0;
                if (collision.normal.x < 0 && deltaX > 0) deltaX = 0;
                if (collision.normal.y > 0 && deltaY < 0) deltaY = 0;
                if (collision.normal.y < 0 && deltaY > 0) deltaY = 0;
            } else if (collision.other.userData instanceof HeartEntity) {
                if (health < playerMaxHealth) {
                    var heart = (HeartEntity) collision.other.userData;
                    heart.destroy = true;
                    health += heartHeal;
                    if (health > playerMaxHealth) health = playerMaxHealth;
                }
            } else if (collision.other.userData instanceof PowerupEntity) {
                var powerup = (PowerupEntity) collision.other.userData;
                powerup.destroy = true;
                if (powerup.powerupType == PowerupType.SHOTGUN) {
                    enabledWeapons.add(SHOTGUN);
                } else if (powerup.powerupType == PowerupType.CROSS) {
                    enabledWeapons.add(CROSS);
                } else if (powerup.powerupType == PowerupType.GRENADE) {
                    enabledWeapons.add(GRENADE);
                } else if (powerup.powerupType == PowerupType.WINGS1) {
                    playerMaxJumps++;
                    enabledWings.add(PowerupType.WINGS1);
                } else if (powerup.powerupType == PowerupType.WINGS2) {
                    playerMaxJumps++;
                    enabledWings.add(PowerupType.WINGS2);
                } else if (powerup.powerupType == PowerupType.WINGS3) {
                    playerMaxJumps++;
                    enabledWings.add(PowerupType.WINGS3);
                }
            }
        }
    }
    
    private final static PlayerCollisionFilter collisionFilter = new PlayerCollisionFilter();
    
    private static class PlayerCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof BoundsEntity) return Response.bounce;
            if (other.userData instanceof HeartEntity) return Response.cross;
            if (other.userData instanceof PowerupEntity) return Response.cross;
            return null;
        }
    }
    
    private final static WhipFilter whipFilter = new WhipFilter();
    
    private static class WhipFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (item.userData instanceof Enemy) return Response.cross;
            if (item.userData instanceof EnemyProjectileEntity) return Response.cross;
            return null;
        }
    }
}
