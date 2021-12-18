package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.SkeletonData;
import com.ray3k.template.Resources.*;

import static com.ray3k.template.Core.*;

public class EnemyProjectileEntity extends Entity {
    public boolean collideWithBounds;
    public float life = -1;
    public float damage;
    public float force;
    public float forceDirection;
    public boolean useVelocityAsForceDirection = true;
    public boolean deactivated;
    
    public EnemyProjectileEntity(SkeletonData skeletonData, AnimationStateData animationData, Animation animation) {
        setSkeletonData(skeletonData, animationData);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, collisionFilter);
        if (animation != null) animationState.setAnimation(0, animation, true);
    }
    
    public EnemyProjectileEntity(float xOffset, float yOffset, float width, float height) {
        setCollisionBox(xOffset, yOffset, width, height, collisionFilter);
    }
    
    @Override
    public void create() {
        depth = DEPTH_PROJECTILES;
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        if (life > 0) {
            life -= delta;
            if (life <= 0) destroy = true;
        }
        
        if (isOutside(0, 0, levelWidth, levelHeight)) {
            destroy = true;
        }
    }
    
    @Override
    public void draw(float delta) {
        if (Values.debugging) {
            shapeDrawer.setColor(Color.YELLOW);
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
                if (collideWithBounds) destroy = true;
            } else if (collision.other.userData instanceof PlayerEntity) {
                if (!deactivated) {
                    destroy = true;
                    var player = (PlayerEntity) collision.other.userData;
                    player.hurt(damage, force, useVelocityAsForceDirection ? getDirection() : forceDirection);
                }
            }
        }
    }
    
    private static final ProjectileCollisionFilter collisionFilter = new ProjectileCollisionFilter();
    
    private static class ProjectileCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof BoundsEntity) return Response.cross;
            if (other.userData instanceof  PlayerEntity) return Response.cross;
            return null;
        }
    }
}
