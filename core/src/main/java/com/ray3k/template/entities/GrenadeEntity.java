package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.Resources.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineGrenade.*;
import static com.ray3k.template.Resources.Values.*;

public class GrenadeEntity extends Entity {
    boolean goRight;
    
    public GrenadeEntity(boolean goRight) {
        this.goRight = goRight;
    }
    
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationLaunch, false);
        animationState.addAnimation(0, animationAnimation,true, 0);
        setMotion(grenadeThrowSpeed, goRight ? grenadeThrowAngle : 180 - grenadeThrowAngle);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, collisionFilter);
        depth = DEPTH_PROJECTILES;
        gravityY = grenadeGravity;
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
    
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
            if (collision.normal.y == 1) {
                destroy = true;
                var fire = new GrenadeFireEntity();
                fire.setPosition(x, getBboxBottom());
                entityController.add(fire);
            }
        }
    }
    
    private static final GrenadeCollisionFilter collisionFilter = new GrenadeCollisionFilter();
    
    private static class GrenadeCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof BoundsEntity) return Response.slide;
            return null;
        }
    }
}
