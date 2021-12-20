package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.esotericsoftware.spine.Slot;
import com.ray3k.template.*;
import com.ray3k.template.Resources.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpinePanda.*;
import static com.ray3k.template.Resources.Values.*;

public class PandaEntity extends Entity implements Enemy {
    private float timer;
    private float bulletTimer;
    private float health;
    private Slot bbox;
    private final static Vector2 temp = new Vector2();
    
    @Override
    public void hurt(float damage, float force, float forceDirection) {
        health -= damage;
        if (health <= 0) {
            destroy = true;
            var die = new DieAnimEntity(skeletonData, animationData, animationState.getCurrent(2).getAnimation(),
                    animationState.getCurrent(0).getTrackTime(), animationDie, x, y,
                    skeleton.getRootBone().getRotation(), true);
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
        health = pandaHealth;
        setSkeletonData(skeletonData, animationData);
        animationData.setDefaultMix(.5f);
        animationState.setAnimation(0, animationStand, true);
        animationState.setAnimation(2, animationPosition1, true);
        bbox = skeleton.findSlot("bbox");
        setCollisionBox(bbox, skeletonBounds, pandaFilter);
        depth = DEPTH_ENEMY;
        timer = pandaAnimDelay;
        bulletTimer = 5f;
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        var player = PlayerEntity.player;
        float playerDirection = Utils.pointDirection(getBboxCenterX(), getBboxCenterY(), player.getBboxCenterX(), player.getBboxCenterY());
        setCollisionBox(bbox, skeletonBounds, pandaFilter);
        
        timer -= delta;
        if (timer < 0) {
            timer = pandaAnimDelay;
            randomAttack();
        }
        
        bulletTimer -= delta;
        if (bulletTimer < 0) {
            if (animationState.getCurrent(0).getAnimation() != animationThrow) animationState.setAnimation(3, animationThrow, true);
            bulletTimer = pandaBulletDelay;
            
            var proj = new EnemyProjectileEntity(SpinePandaChute.skeletonData,
                    SpinePandaChute.animationData, SpinePandaChute.animationAnimation);
            entityController.add(proj);
            var bone = skeleton.findBone("weapon");
            temp.set(0, 0);
            bone.localToWorld(temp);
            proj.teleport(temp.x, temp.y);
            proj.collideWithBounds = false;
            proj.damage = shooterDamage;
            proj.force = shooterForce;
            proj.setMotion(shooterAttackSpeed, playerDirection);
        }
    }
    
    private void randomAttack() {
        int index = MathUtils.random(1, 6);
        
        switch (index) {
            case 1:
                animationState.setAnimation(2, animationPosition1, true);
                break;
            case 2:
                animationState.setAnimation(2, animationPosition2, true);
                break;
            case 3:
                animationState.setAnimation(2, animationPosition3, true);
                break;
            case 4:
                animationState.setAnimation(2, animationPosition4, true);
                break;
            case 5:
                animationState.setAnimation(2, animationPosition5, true);
                break;
            case 6:
                animationState.setAnimation(2, animationPosition6, true);
                break;
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
        bossAlive = false;
        playExplore();
        defeatedPanda = true;
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
                PlayerEntity.player.hurt(pandaDamage, pandaForce, playerToRight ? pandaForceDirection : 180 - pandaForceDirection);
            }
        }
    }
    
    private final static PandaFilter pandaFilter = new PandaFilter();
    
    private static class PandaFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof PlayerEntity) return Response.cross;
            
            return null;
        }
    }
}
