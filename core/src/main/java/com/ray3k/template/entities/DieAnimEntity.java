package com.ray3k.template.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.Slot;
import com.ray3k.template.screens.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.Values.*;

public class DieAnimEntity extends Entity {
    private float explosionTimer;
    private boolean explosions;
    public boolean reloadOnDeath;
    private Slot bbox;
    public boolean creditsOnDeath;
    
    public DieAnimEntity(SkeletonData skeletonData, AnimationStateData animationStateData, Animation currentAnimation, float time, Animation deathAnimation, float x, float y, float rotation, boolean explosions) {
        this.explosions = explosions;
        depth = DEPTH_ENEMY;
        setSkeletonData(skeletonData, animationStateData);
        bbox = skeleton.findSlot("bbox");
        setCollisionBox(bbox, skeletonBounds, nullCollisionFilter);
        animationState.setAnimation(0, currentAnimation, false);
        animationState.getCurrent(0).setTrackTime(time);
        animationState.getCurrent(0).setTimeScale(0);
        animationState.setAnimation(1, deathAnimation, false);
        setPosition(x, y);
        skeleton.getRootBone().setRotation(rotation);
        animationState.addListener(new AnimationStateAdapter() {
            @Override
            public void complete(TrackEntry entry) {
                if (entry.getTrackIndex() == 1) {
                    destroy = true;
                    Gdx.app.postRunnable(() -> {
                        if (reloadOnDeath) core.transition(new GameScreen());
                        else if (creditsOnDeath) core.transition(new CreditsScreen());
                    });
                }
            }
        });
    }
    
    @Override
    public void create() {
    
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        setCollisionBox(bbox, skeletonBounds, nullCollisionFilter);
        explosionTimer -= delta;
        if (explosions && explosionTimer < 0) {
            for (int i = 0; i < explosionCount; i++) {
                explosionTimer = explosionDelay;
                var explosion = new ExplosionEntity();
                explosion.setPosition(getBboxLeft() + MathUtils.random(bboxWidth),
                        getBboxBottom() + MathUtils.random(bboxHeight));
                entityController.add(explosion);
            }
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
