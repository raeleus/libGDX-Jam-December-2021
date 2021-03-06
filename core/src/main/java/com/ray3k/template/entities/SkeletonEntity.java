package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
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
import com.ray3k.template.Resources.*;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpineSkeleton.*;
import static com.ray3k.template.Resources.Values.*;
import static com.ray3k.template.entities.PlayerEntity.*;

public class SkeletonEntity extends Entity implements Enemy {
    private boolean goRight;
    public float health;
    
    @Override
    public void hurt(float damage, float force, float forceDirection) {
        addMotion(force, forceDirection);
        health -= damage;
        if (health <= 0) {
            destroy = true;
            var die = new DieAnimEntity(skeletonData, animationData, animationState.getCurrent(0).getAnimation(), animationState.getCurrent(0).getTrackTime(), animationDie, x, y, skeleton.getRootBone().getRotation(), true);
            entityController.add(die);
        }
        float playerDirection = Utils.pointDirection(x, y, player.x, player.y);
        goRight = playerDirection < 90 || playerDirection > 270;
        gravityY = skeletonGravity;
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
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationWalk, true);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, collisionFilter);
        depth = DEPTH_ENEMY;
        
        gravityY = skeletonGravity;
        goRight = MathUtils.randomBoolean();
        deltaX = goRight ? skeletonMoveSpeed : -skeletonMoveSpeed;
        health = skeletonHealth;
        
        animationState.addListener(new AnimationStateAdapter() {
            @Override
            public void event(TrackEntry entry, Event event) {
                if (event.getData().getName().equals("attack")) {
                    boolean facingRight = skeleton.getScaleX() < 0;
                    var bone = skeleton.findBone("weapon");
                    var proj = new EnemyProjectileEntity(facingRight ? bone.getX() : bone.getX() - 275, bone.getY(), 275, 50);
                    entityController.add(proj);
                    proj.teleport(x, y);
                    proj.canBeDeactivated = false;
                    proj.life = .2f;
                    proj.damage = skeletonDamage;
                    proj.force = skeletonForce;
                    proj.forceDirection = facingRight ? 0 : 180;
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
        boolean inAir = world.check(item, x, y - 1, collisionFilter).projectedCollisions.size() == 0;
        var moveX = x + (deltaX + gravityX * delta) * delta;
        var moveY = y + (deltaY + gravityY * delta) * delta;
        var result = world.check(item, moveX + bboxX, moveY + bboxY - 1, PlayerEntity.platformFilter);
        for (int i = 0; i < result.projectedCollisions.size(); i++) {
            var collision = result.projectedCollisions.get(i);
            if (collision.normal.y == 1 && !collision.overlaps) {
                world.update(item, result.goalX, result.goalY);
                deltaY = 0;
                inAir = false;
                break;
            }
        }
    
        if (inAir) {
            gravityY = skeletonGravity;
        } else {
            gravityY = 0;
        }
        
        deltaX = Utils.approach(deltaX, goRight ? skeletonMoveSpeed : -skeletonMoveSpeed, skeletonAcceleration * delta);
        
        if (animationState.getCurrent(0).getAnimation() == animationWalk) {
            boolean facingRight = deltaX > 0;
            skeleton.setScaleX(facingRight ? -1 : 1);
            float playerDistance = Utils.pointDistance(getBboxCenterX(), getBboxCenterY(), player.getBboxCenterX(),
                    player.getBboxCenterY());
            float playerDirection = Utils.pointDirection(getBboxCenterX(), getBboxCenterY(), player.getBboxCenterX(),
                    player.getBboxCenterY());
            boolean playerFacingRight = playerDirection < 90 || playerDirection > 270;
            if (playerDistance < skeletonAttackDistance && playerFacingRight == facingRight) {
                animationState.setAnimation(0, animationAttack, false);
                animationState.addAnimation(0, animationWalk, true, 0);
                setMotion(skeletonAttackSpeed, facingRight ? skeletonAttackAngle : 180 - skeletonAttackAngle);
            }
        }
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
        sfx_die.play(sfx);
    }
    
    @Override
    public void projectedCollision(Result result) {
    
    }
    
    @Override
    public void collision(Collisions collisions) {
        for (int i = 0; i < collisions.size(); i++) {
            var collision = collisions.get(i);
            if (collision.other.userData instanceof BoundsEntity) {
                if (collision.normal.x != 0) {
                    deltaX *= -1;
                    goRight = !goRight;
                }
                
                if (collision.normal.y != 0) {
                    deltaY = 0;
                }
                
                if (collision.normal.y == 1) {
                    gravityY = 0;
                }
            } else if (collision.other.userData instanceof PlayerEntity) {
                var player = (PlayerEntity) collision.other.userData;
                player.hurt(skeletonDamage, skeletonForce, player.x < x ? 180 - skeletonForceDirection : skeletonForceDirection);
            }
        }
    }
    
    private static final SkeletonCollisionFilter collisionFilter = new SkeletonCollisionFilter();
    
    private static class SkeletonCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof BoundsEntity) return Response.bounce;
            if (other.userData instanceof  PlayerEntity) return Response.cross;
            return null;
        }
    }
}
