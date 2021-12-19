package com.ray3k.template.entities;

import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.esotericsoftware.spine.Animation;
import com.ray3k.template.*;

import static com.ray3k.template.Resources.SpineDoor.*;
import static com.ray3k.template.Resources.Values.*;

public class DoorEntity extends Entity {
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationClose, false);
        animationData.setDefaultMix(.25f);
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        float playerDistance = Utils.pointDistance(this, PlayerEntity.player);
    
        Animation animation = null;
        if (!Core.bossAlive && playerDistance < doorDistanceOpen) animation = animationOpen;
        else if (playerDistance > doorDistanceClose) animation = animationClose;
        
        if (animation != null && animationState.getCurrent(0).getAnimation() != animation) {
            animationState.setAnimation(0, animation, false);
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
