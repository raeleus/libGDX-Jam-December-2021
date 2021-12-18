package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.*;
import com.ray3k.template.Resources.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineBlob.*;
import static com.ray3k.template.Resources.Values.*;

public class BlobEntity extends Entity implements Enemy {
    private boolean goRight;
    public float health;
    
    @Override
    public void hurt(float damage, float force, float forceDirection) {
        System.out.println("damage = " + damage);
        addMotion(force, forceDirection);
        health -= damage;
        if (health <= 0) {
            destroy = true;
            var die = new DieAnimEntity(skeletonData, animationData, animationState.getCurrent(0).getAnimation(), animationState.getCurrent(0).getTrackTime(), animationDie, x, y, skeleton.getRootBone().getRotation(), true);
            entityController.add(die);
        }
        float playerDirection = Utils.pointDirection(x, y, PlayerEntity.player.x, PlayerEntity.player.y);
        goRight = playerDirection < 90 || playerDirection > 270;
        gravityY = blobGravity;
        animationState.setAnimation(1, animationHurt, false);
    }
    
    @Override
    public float getX() {
        return x;
    }
    
    @Override
    public float getY() {
        return y;
    }
    
    @Override
    public void create() {
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationAnimation, true);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, collisionFilter);
        depth = DEPTH_ENEMY;
        
        gravityY = blobGravity;
        goRight = MathUtils.randomBoolean();
        deltaX = goRight ? blobMoveSpeed : -blobMoveSpeed;
        health = blobHealth;
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        deltaX = Utils.approach(deltaX, goRight ? blobMoveSpeed : -blobMoveSpeed, blobAcceleration * delta);
    }
    
    @Override
    public void draw(float delta) {
        if (Values.debugging) {
            shapeDrawer.setColor(Color.ORANGE);
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
                if (collision.normal.x != 0) {
                    deltaX *= -1;
                    goRight = !goRight;
                }
                
                if (collision.normal.y != 0) {
                    deltaY = 0;
                }
                
                if (collision.normal.y == 1) {
                    gravityY = 0;
                }
            } else if (collision.other.userData instanceof PlayerEntity) {
                var player = (PlayerEntity) collision.other.userData;
                player.hurt(blobDamage, blobForce, player.x < x ? 180 - blobForceDirection : blobForceDirection);
            }
        }
    }
    
    private static final BlobCollisionFilter collisionFilter = new BlobCollisionFilter();
    
    private static class BlobCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof BoundsEntity) return Response.bounce;
            if (other.userData instanceof  PlayerEntity) return Response.cross;
            return null;
        }
    }
}
