package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.Resources.*;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineShooter.*;
import static com.ray3k.template.Resources.Values.*;
import static com.ray3k.template.entities.PlayerEntity.*;

public class ShooterEntity extends Entity implements Enemy {
    public float health;
    public float shotTimer;
    
    @Override
    public void hurt(float damage, float force, float forceDirection) {
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
        animationState.setAnimation(0, animationStand, true);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, nullCollisionFilter);
        depth = DEPTH_ENEMY;
        health = shooterHealth;
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        boolean facingRight = skeleton.getScaleX() < 0;
        float playerDistance = Utils.pointDistance(getBboxCenterX(), getBboxCenterY(), player.getBboxCenterX(),
                player.getBboxCenterY());
        float playerDirection = Utils.pointDirection(getBboxCenterX(), getBboxCenterY(), player.getBboxCenterX(),
                player.getBboxCenterY());
        boolean playerFacingRight = playerDirection < 90 || playerDirection > 270;
        
        shotTimer -= delta;
        if (shotTimer <= 0 && playerDistance < shooterAttackDistance && playerFacingRight == facingRight) {
            shotTimer = shooterShotDelay;
            var proj = new EnemyProjectileEntity(SpineGluttonProjectile.skeletonData,
                    SpineGluttonProjectile.animationData, SpineGluttonProjectile.animationShooter);
            entityController.add(proj);
            var bone = skeleton.findBone("weapon");
            proj.teleport(x + bone.getX(), y + bone.getY());
            proj.collideWithBounds = true;
            proj.damage = shooterDamage;
            proj.force = shooterForce;
            proj.setMotion(shooterAttackSpeed, facingRight ? 0 : 180);
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

    }
}
