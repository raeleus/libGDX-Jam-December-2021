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
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpineWolf.*;
import static com.ray3k.template.Resources.Values.*;

public class WolfEntity extends Entity implements Enemy {
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
        float playerDirection = Utils.pointDirection(x, y, PlayerEntity.player.x, PlayerEntity.player.y);
        goRight = playerDirection < 90 || playerDirection > 270;
        gravityY = wolfGravity;
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
        animationState.setAnimation(0, animationRun, true);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, collisionFilter);
        depth = DEPTH_ENEMY;
        
        gravityY = wolfGravity;
        goRight = MathUtils.randomBoolean();
        deltaX = goRight ? wolfMoveSpeed : -wolfMoveSpeed;
        health = wolfHealth;
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
            gravityY = wolfGravity;
        } else {
            gravityY = 0;
        }
        
        deltaX = Utils.approach(deltaX, goRight ? wolfMoveSpeed : -wolfMoveSpeed, wolfAcceleration * delta);
        
        skeleton.setScaleX(goRight ? 1 : -1);
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
                player.hurt(wolfDamage, wolfForce, player.x < x ? 180 - wolfForceDirection : wolfForceDirection);
            }
        }
    }
    
    private static final WolfCollisionFilter collisionFilter = new WolfCollisionFilter();
    
    private static class WolfCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof BoundsEntity) return Response.bounce;
            if (other.userData instanceof  PlayerEntity) return Response.cross;
            return null;
        }
    }
}
