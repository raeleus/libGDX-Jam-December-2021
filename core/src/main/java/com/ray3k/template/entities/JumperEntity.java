package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.Resources.*;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineJumper.*;
import static com.ray3k.template.Resources.Values.*;
import static com.ray3k.template.entities.PlayerEntity.*;

public class JumperEntity extends Entity implements Enemy {
    private boolean goRight;
    public float health;
    private final static Vector2 temp = new Vector2();
    
    @Override
    public void hurt(float damage, float force, float forceDirection) {
        temp.x = force;
        temp.rotateDeg(forceDirection);
        temp.x *= jumperHurtForceDampenerX;
        temp.y *= jumperHurtForceDampenerY;
        deltaX += temp.x;
        deltaY += temp.y;
        
        health -= damage;
        if (health <= 0) {
            destroy = true;
            var die = new DieAnimEntity(skeletonData, animationData, animationState.getCurrent(0).getAnimation(), animationState.getCurrent(0).getTrackTime(), animationDie, x, y, skeleton.getRootBone().getRotation(), true);
            entityController.add(die);
        }
        float playerDirection = Utils.pointDirection(x, y, player.x, player.y);
        goRight = playerDirection < 90 || playerDirection > 270;
        gravityY = jumperGravity;
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
        
        gravityY = jumperGravity;
        goRight = MathUtils.randomBoolean();
        deltaX = goRight ? jumperMoveSpeed : -jumperMoveSpeed;
        health = jumperHealth;
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        boolean inAir = world.check(item, x, y - 10, boundsFilter).projectedCollisions.size() == 0;
        if (inAir) gravityY = jumperGravity;
        if (!inAir) deltaX = Utils.approach(deltaX, goRight ? jumperMoveSpeed : -jumperMoveSpeed, jumperAcceleration * delta);
        else deltaX = Utils.approach(deltaX, 0, jumperDeceleration * delta);
    
        boolean facingRight = deltaX > 0;
        skeleton.setScaleX(facingRight ? -1 : 1);
        if (!inAir) {
            animationState.setAnimation(0, animationJump, false);
            animationState.addAnimation(0, animationStand, true, 0);
            setMotion(jumperJumpSpeed, facingRight ? jumperAttackAngle : 180 - jumperAttackAngle);

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
                player.hurt(jumperDamage, jumperForce, player.x < x ? 180 - jumperForceDirection : jumperForceDirection);
            }
        }
    }
    
    private static final JumperCollisionFilter collisionFilter = new JumperCollisionFilter();
    
    private static class JumperCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof BoundsEntity) return Response.bounce;
            if (other.userData instanceof  PlayerEntity) return Response.cross;
            return null;
        }
    }
    
    private final static BoundsCollisionFilter boundsFilter = new BoundsCollisionFilter();
    
    private static class BoundsCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof BoundsEntity) return Response.cross;
            return null;
        }
    }
}
