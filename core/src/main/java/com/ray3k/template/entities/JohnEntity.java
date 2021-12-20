package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.esotericsoftware.spine.Slot;
import com.ray3k.template.Resources.*;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpineJohn.*;
import static com.ray3k.template.Resources.Values.*;

public class JohnEntity extends Entity implements Enemy {
    private float health;
    
    @Override
    public void hurt(float damage, float force, float forceDirection) {
        health -= damage;
        if (health <= 0) {
            destroy = true;
            var die = new DieAnimEntity(skeletonData, animationData, animationState.getCurrent(0).getAnimation(),
                    animationState.getCurrent(0).getTrackTime(), animationDie, x, y,
                    skeleton.getRootBone().getRotation(), true);
            die.creditsOnDeath = true;
            entityController.add(die);
        }
        animationState.setAnimation(1, animationHurt, false);
        animationState.addEmptyAnimation(1, 0, 0);
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
        bossAlive = true;
        playBattle();
        health = johnHealth;
        setSkeletonData(skeletonData, animationData);
        animationData.setDefaultMix(.5f);
        animationState.setAnimation(0, animationStand, true);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, johnFilter);
        depth = DEPTH_ENEMY;
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
            shapeDrawer.setColor(Color.ORANGE);
            shapeDrawer.filledRectangle(getBboxLeft(), getBboxBottom(), bboxWidth, bboxHeight);
        }
    }
    
    @Override
    public void destroy() {
        bossAlive = false;
        playExplore();
        defeatedJohn = true;
        sfx_bossDie.play(sfx);
    }
    
    @Override
    public void projectedCollision(Result result) {
    
    }
    
    @Override
    public void collision(Collisions collisions) {
        var player = PlayerEntity.player;
        float playerDirection = Utils.pointDirection(getBboxCenterX(), getBboxCenterY(), player.getBboxCenterX(), player.getBboxCenterY());
        boolean playerToRight = playerDirection < 90 || playerDirection > 270;
        
        for (int i = 0; i < collisions.size(); i++) {
            var collision = collisions.get(i);
            if (collision.other.userData instanceof PlayerEntity) {
                PlayerEntity.player.hurt(johnDamage, johnForce, playerToRight ? johnForceDirection : 180 - johnForceDirection);
            }
        }
    }
    
    private final static JohnFilter johnFilter = new JohnFilter();
    
    private static class JohnFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof PlayerEntity) return Response.cross;
            
            return null;
        }
    }
}
