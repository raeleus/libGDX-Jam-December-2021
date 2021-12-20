package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.Resources.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpineCross.*;
import static com.ray3k.template.Resources.Values.*;

public class CrossEntity extends Entity {
    boolean goRight;
    private ObjectMap<Enemy, Float> hitMap = new ObjectMap<>();
    
    public CrossEntity(boolean goRight) {
        this.goRight = goRight;
    }
    
    @Override
    public void create() {
        sfx_cross.play(sfx);
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationAnimation, true);
        setMotion(crossLaunchSpeed, goRight ? 0f : 180f);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, collisionFilter);
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
                    enemy.hurt(crossDamage, crossForce,
                            x < enemy.getX() ? crossForceDirection : 180 - crossForceDirection);
                    hitMap.put(enemy, crossHitDelay);
                }
            }
        }
    }
    
    private static final CrossCollisionFilter collisionFilter = new CrossCollisionFilter();
    
    private static class CrossCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof Enemy) return Response.cross;
            return null;
        }
    }
}
