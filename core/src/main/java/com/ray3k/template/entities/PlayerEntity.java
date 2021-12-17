package com.ray3k.template.entities;

import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.*;

import static com.ray3k.template.Core.Binding.*;
import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineDragonQueen.*;
import static com.ray3k.template.Resources.Values.*;

public class PlayerEntity extends Entity {
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationStand, true);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, nullCollisionFilter);
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        if (isBindingPressed(LEFT)) {
            if (deltaX > -playerMaxSpeed) {
                deltaX -= playerAcceleration * delta;
                if (deltaX < -playerMaxSpeed) deltaX = -playerMaxSpeed;
                if (animationState.getCurrent(0).getAnimation() != animationRun) animationState.setAnimation(0, animationRun, true);
                skeleton.setScale(-1, 1);
            }
        } else if (isBindingPressed(RIGHT)) {
            if (deltaX < playerMaxSpeed) {
                deltaX += playerAcceleration * delta;
                if (deltaX > playerMaxSpeed) deltaX = playerMaxSpeed;
                if (animationState.getCurrent(0).getAnimation() != animationRun) animationState.setAnimation(0, animationRun, true);
                skeleton.setScale(1, 1);
            }
        } else {
            deltaX = Utils.approach(deltaX, 0, playerDeceleration * delta);
            if (animationState.getCurrent(0).getAnimation() != animationStand) animationState.setAnimation(0, animationStand, true);
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
    
    }
}
