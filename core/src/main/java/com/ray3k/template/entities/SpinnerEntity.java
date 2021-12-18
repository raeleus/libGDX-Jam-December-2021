package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.Event;
import com.ray3k.template.*;
import com.ray3k.template.Resources.*;

import java.util.ArrayList;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineSpinner.*;
import static com.ray3k.template.Resources.Values.*;

public class SpinnerEntity extends Entity implements Enemy {
    public float health;
    private final static Vector2 temp = new Vector2();
    private static final ArrayList<Item> itemsTemp = new ArrayList<>();
    private boolean inAttack;
    
    @Override
    public void hurt(float damage, float force, float forceDirection) {
        temp.x = force;
        temp.rotateDeg(forceDirection);
        temp.x *= spinnerHurtForceDampenerX;
        temp.y *= spinnerHurtForceDampenerY;
        deltaX += temp.x;
        deltaY += temp.y;
        
        health -= damage;
        if (health <= 0) {
            destroy = true;
            var die = new DieAnimEntity(skeletonData, animationData, animationState.getCurrent(0).getAnimation(), animationState.getCurrent(0).getTrackTime(), animationDie, x, y, skeleton.getRootBone().getRotation(), true);
            entityController.add(die);
        }
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
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, collisionFilter);
        depth = DEPTH_ENEMY;
        health = spinnerHealth;
        animationState.setAnimation(0, animationAnimation, false);
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        if (isOutside(0, 0, Core.levelWidth, Core.levelHeight, spinnerDestroyBorder)) destroy = true;
        if (!inAttack) {
            setSpeed(Utils.approach(getSpeed(), 0, spinnerDeceleration * delta));
    
            for (int i = 0; i < spinnerDetectRays; i++) {
                temp.set(1, 0);
                float angle = skeleton.getRootBone().getRotation() + 270 - spinnerDetectAngle / 2 + i * spinnerDetectAngle / (spinnerDetectRays - 1);
                angle %= 360;
                temp.rotateDeg(angle);
                world.queryRay(getBboxCenterX(), getBboxCenterY(), temp.x, temp.y, playerDetectFilter, itemsTemp);
                if (itemsTemp.size() > 0) {
                    setMotion(spinnerMoveSpeed, angle);
                    inAttack = true;
                    animationState.setAnimation(0, animationFall, true);
                    break;
                }
            }
        }
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
            if (collision.other.userData instanceof PlayerEntity) {
                var player = (PlayerEntity) collision.other.userData;
                float playerDirection = Utils.pointDirection(getBboxCenterX(),getBboxCenterY(), player.getBboxCenterX(),player.getBboxCenterY());
                player.hurt(spinnerDamage, spinnerForce, playerDirection);
            }
        }
    }
    
    private static final SpinnerCollisionFilter collisionFilter = new SpinnerCollisionFilter();
    
    private static class SpinnerCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof  PlayerEntity) return Response.cross;
            return null;
        }
    }
    
    private static final PlayerDetectFilter playerDetectFilter = new PlayerDetectFilter();
    
    private static class PlayerDetectFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (item.userData instanceof  PlayerEntity) return Response.cross;
            return null;
        }
    }
}
