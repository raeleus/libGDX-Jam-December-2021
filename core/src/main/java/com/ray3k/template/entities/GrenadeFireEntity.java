package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.ray3k.template.Resources.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineGrenadeFire.*;
import static com.ray3k.template.Resources.Values.*;

public class GrenadeFireEntity extends Entity {
    private ObjectMap<Enemy, Float> hitMap = new ObjectMap<>();
    
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationAnimation, false);
        animationState.setTimeScale(.7f + MathUtils.random(.6f));
        depth = DEPTH_PARTICLES;
        animationState.addListener(new AnimationStateAdapter() {
            @Override
            public void complete(TrackEntry entry) {
                destroy = true;
            }
        });
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, collisionFilter);
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        var iter = hitMap.iterator();
        while (iter.hasNext) {
            var entry = iter.next();
            entry.value = entry.value - delta;
            hitMap.put(entry.key, entry.value);
            if (entry.value <= 0) {
                iter.remove();
            }
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
        for (int i = 0; i < collisions.size(); i++) {
            var collision = collisions.get(i);
        
            if (collision.other.userData instanceof Enemy) {
                var enemy = (Enemy) collision.other.userData;
                if (!hitMap.containsKey(enemy)) {
                    enemy.hurt(grenadeDamagePerSecond, grenadeForce,
                            x < enemy.getX() ? grenadeForceDirection : 180 - grenadeForceDirection);
                    hitMap.put(enemy, grenadeHitDelay);
                }
            }
        }
    }
    
    private final static GrenadeFireCollisionFilter collisionFilter = new GrenadeFireCollisionFilter();
    
    private static class GrenadeFireCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof Enemy) return Response.cross;
            return null;
        }
    }
}
