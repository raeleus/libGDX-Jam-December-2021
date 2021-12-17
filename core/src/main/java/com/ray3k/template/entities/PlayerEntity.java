package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.dongbat.jbump.*;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.*;
import com.ray3k.template.Resources.*;

import static com.ray3k.template.Core.Binding.*;
import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineDragonQueen.*;
import static com.ray3k.template.Resources.Values.*;

public class PlayerEntity extends Entity {
    private float jumpTime;
    private int jumps;
    
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationStand, true);
        animationData.setDefaultMix(.2f);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, collisionFilter);
        gravityY = playerGravity;
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        boolean inAir = world.check(item, x, y - 1, collisionFilter).projectedCollisions.size() == 0;
        
        if (isBindingPressed(LEFT)) {
            if (deltaX > -playerMaxSpeed) {
                deltaX -= (inAir ? playerAccelerationWhileJumping : playerAcceleration) * delta;
                if (deltaX < -playerMaxSpeed) deltaX = -playerMaxSpeed;
            }
            if (!inAir && animationState.getCurrent(0).getAnimation() != animationRun) animationState.setAnimation(0, animationRun, true);
            skeleton.setScale(-1, 1);
        } else if (isBindingPressed(RIGHT)) {
            if (deltaX < playerMaxSpeed) {
                deltaX += (inAir ? playerAccelerationWhileJumping : playerAcceleration) * delta;
                if (deltaX > playerMaxSpeed) deltaX = playerMaxSpeed;
            }
            if (!inAir && animationState.getCurrent(0).getAnimation() != animationRun) animationState.setAnimation(0, animationRun, true);
            skeleton.setScale(1, 1);
        } else {
            deltaX = Utils.approach(deltaX, 0, (inAir ? playerDecelerationWhileJumping : playerDeceleration) * delta);
            if (!inAir && animationState.getCurrent(0).getAnimation() != animationStand) animationState.setAnimation(0, animationStand, true);
        }

        if ((!inAir || jumps < playerMaxJumps) && isBindingJustPressed(UP)) {
            jumpTime = 0;
            deltaY += playerJumpSpeed;
            animationState.setAnimation(0, animationJump, false);
            if (inAir) animationState.setAnimation(1, animationFlap, false);
            
            if (!inAir) jumps = 1;
            else jumps++;
            inAir = true;
        }
        
        if (inAir) {
            if (isBindingPressed(UP)) {
                if (jumpTime < playerJumpHoldTime) {
                    if (deltaY < playerJumpSpeed) {
                        deltaY = playerJumpSpeed;
                    }
                }
            }
            jumpTime += delta;
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
            }
        }
    }

    private final static PlayerCollisionFilter collisionFilter = new PlayerCollisionFilter();
    
    private static class PlayerCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof  BoundsEntity) return Response.slide;
            return null;
        }
    }
}
