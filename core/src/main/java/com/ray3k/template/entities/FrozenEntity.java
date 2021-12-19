package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.Resources.*;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineFrozen.*;
import static com.ray3k.template.Resources.Values.*;

public class FrozenEntity extends Entity implements Enemy {
    public float health;
    private final static Vector2 temp = new Vector2();
    private float shotTimer;
    
    @Override
    public void hurt(float damage, float force, float forceDirection) {
        temp.x = force;
        temp.rotateDeg(forceDirection);
        temp.x *= batHurtForceDampenerX;
        temp.y *= batHurtForceDampenerY;
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
        animationState.setAnimation(0, animationStand, true);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, collisionFilter);
        depth = DEPTH_ENEMY;
        health = frozenHealth;
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        float playerDistance = Utils.pointDistance(getBboxCenterX(),getBboxCenterY(), PlayerEntity.player.getBboxCenterX(), PlayerEntity.player.getBboxCenterY());
        if (playerDistance < frozenChaseDistance) {
            float playerDirection = Utils.pointDirection(getBboxCenterX(), getBboxCenterY(),
                    PlayerEntity.player.getBboxCenterX(), PlayerEntity.player.getBboxCenterY());
            addMotion(frozenAcceleration * delta, playerDirection);
            if (getSpeed() > frozenMoveSpeed) setSpeed(Utils.approach(getSpeed(), frozenMoveSpeed, frozenDeceleration * delta));
            skeleton.setScaleX(deltaX < 0 ? 1 : -1);
    
            shotTimer -= delta;
            if (shotTimer <= 0) {
                shotTimer = frozenShotDelay;
                var proj = new EnemyProjectileEntity(SpineGluttonProjectile.skeletonData,
                        SpineGluttonProjectile.animationData, SpineGluttonProjectile.animationFrozen);
                entityController.add(proj);
                var bone = skeleton.findBone("weapon");
                proj.teleport(x + bone.getX(), y + bone.getY());
                proj.collideWithBounds = true;
                proj.damage = shooterDamage;
                proj.force = shooterForce;
                proj.setMotion(shooterAttackSpeed, playerDirection);
            }
        } else {
            setSpeed(Utils.approach(getSpeed(), 0, frozenDeceleration * delta));
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
                player.hurt(frozenDamage, frozenForce, playerDirection);
            }
        }
    }
    
    private static final FrozenCollisionFilter collisionFilter = new FrozenCollisionFilter();
    
    private static class FrozenCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof  PlayerEntity) return Response.cross;
            return null;
        }
    }
}
