package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.Resources.*;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineSlimeDog.*;
import static com.ray3k.template.Resources.Values.*;
import static com.ray3k.template.entities.PlayerEntity.*;

public class SlimeDogEntity extends Entity implements Enemy {
    private boolean goRight;
    public float health;
    private float jumpTimer;
    
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
        gravityY = slimeDogGravity;
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
        animationState.setAnimation(0, animationStand, true);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, collisionFilter);
        depth = DEPTH_ENEMY;
        
        gravityY = slimeDogGravity;
        goRight = MathUtils.randomBoolean();
        deltaX = goRight ? slimeDogMoveSpeed : -slimeDogMoveSpeed;
        health = slimeDogHealth;
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        boolean inAir = world.check(item, x, y - 1, collisionFilter).projectedCollisions.size() == 0;
        if (inAir) gravityY = slimeDogGravity;
        deltaX = Utils.approach(deltaX, goRight ? slimeDogMoveSpeed : -slimeDogMoveSpeed, slimeDogAcceleration * delta);
        
        if (animationState.getCurrent(0).getAnimation() == animationStand) {
            boolean facingRight = deltaX > 0;
            skeleton.setScaleX(facingRight ? -1 : 1);
            float playerDistance = Utils.pointDistance(getBboxCenterX(), getBboxCenterY(), player.getBboxCenterX(),
                    player.getBboxCenterY());
            float playerDirection = Utils.pointDirection(getBboxCenterX(), getBboxCenterY(), player.getBboxCenterX(),
                    player.getBboxCenterY());
            boolean playerFacingRight = playerDirection < 90 || playerDirection > 270;
            if (playerDistance < slimeDogAttackDistance && playerFacingRight == facingRight) {
                animationState.setAnimation(0, animationAttack, false);
                animationState.addAnimation(0, animationStand, true, 0);
                var proj = new EnemyProjectileEntity(-300, 100, 275, 50);
                entityController.add(proj);
                proj.teleport(x, y);
                proj.canBeDeactivated = false;
                proj.life = .2f;
                proj.damage = slimeDogDamage;
                proj.force = slimeDogForce;
                proj.forceDirection = facingRight ? 0 : 180;
                proj.useVelocityAsForceDirection = false;
            }
            
            jumpTimer -= delta;
            if (!inAir && jumpTimer < 0 && player.y - y > slimeDogYAgro) {
                jumpTimer = slimeDogJumpDelay;
                deltaY += slimeDogJumpSpeed;
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
                player.hurt(slimeDogDamage, slimeDogForce, player.x < x ? 180 - slimeDogForceDirection : slimeDogForceDirection);
            }
        }
    }
    
    private static final SlimeDogCollisionFilter collisionFilter = new SlimeDogCollisionFilter();
    
    private static class SlimeDogCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof BoundsEntity) return Response.bounce;
            if (other.userData instanceof  PlayerEntity) return Response.cross;
            return null;
        }
    }
}
