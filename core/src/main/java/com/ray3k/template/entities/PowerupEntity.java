package com.ray3k.template.entities;

import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpinePowerup.*;

public class PowerupEntity extends Entity {
    public PowerupType powerupType;
    
    public enum PowerupType {
        SHOTGUN, CROSS, GRENADE, WINGS1, WINGS2, WINGS3
    }
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, nullCollisionFilter);
        depth = DEPTH_POWERUPS;
    }
    
    public void setPowerupType(PowerupType powerupType) {
        this.powerupType = powerupType;
        switch (powerupType) {
            case SHOTGUN:
                animationState.setAnimation(0, animationShotgun, false);
                break;
            case CROSS:
                animationState.setAnimation(0, animationCross, false);
                break;
            case GRENADE:
                animationState.setAnimation(0, animationGrenade, false);
                break;
            case WINGS1:
            case WINGS2:
            case WINGS3:
                animationState.setAnimation(0, animationWings, false);
                break;
        }
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
    
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
