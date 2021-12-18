package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.Resources.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineCross.*;
import static com.ray3k.template.Resources.Values.*;

public class CrossEntity extends Entity {
    boolean goRight;
    
    public CrossEntity(boolean goRight) {
        this.goRight = goRight;
    }
    
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationAnimation, true);
        setMotion(crossLaunchSpeed, goRight ? 0f : 180f);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, nullCollisionFilter);
        depth = DEPTH_PROJECTILES;
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        float rotation = skeleton.getRootBone().getRotation();
        rotation += crossRotationMultiplier * getSpeed() * delta;
        skeleton.getRootBone().setRotation(rotation);
        addMotion(crossAcceleration * delta, goRight ? 180f : 0f);
        if (goRight && getBboxRight() < camera.position.x - camera.viewportWidth / 2 * camera.zoom ||
        !goRight && getBboxLeft() > camera.position.x + camera.viewportWidth / 2 * camera.zoom) {
            destroy = true;
        }
    }
    
    @Override
    public void draw(float delta) {
        if (Values.debugging) {
            shapeDrawer.setColor(Color.GREEN);
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
    
    }
}
