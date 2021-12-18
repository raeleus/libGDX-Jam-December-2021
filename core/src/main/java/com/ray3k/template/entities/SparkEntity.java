package com.ray3k.template.entities;

import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineSpark.*;

public class SparkEntity extends Entity {
    
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationAnimation, false);
        animationState.setTimeScale(.7f + MathUtils.random(.6f));
        depth = DEPTH_PARTICLES;
        animationState.addListener(new AnimationStateAdapter() {
            @Override
            public void end(TrackEntry entry) {
            
            }
    
            @Override
            public void complete(TrackEntry entry) {
                destroy = true;
            }
        });
        skeleton.getRootBone().setRotation(MathUtils.random(360f));
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
