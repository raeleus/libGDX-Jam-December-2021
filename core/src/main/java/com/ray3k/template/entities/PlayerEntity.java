package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.jbump.*;
import com.dongbat.jbump.Response.Result;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Event;
import com.ray3k.template.*;
import com.ray3k.template.Resources.*;
import com.ray3k.template.screens.*;

import static com.ray3k.template.Core.Binding.*;
import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SpineDragonQueen.*;
import static com.ray3k.template.Resources.Values.*;
import static com.ray3k.template.entities.PlayerEntity.Weapon.*;

public class PlayerEntity extends Entity {
    public static PlayerEntity player;
    public boolean selectingWeapon;
    private float jumpTime;
    private int jumps;
    public Weapon weapon;
    public static Array<Weapon> enabledWeapons = new Array<>();
    public enum Weapon {
        WHIP, GRENADE, SHOTGUN, CROSS
    }
    private Bone weaponBone;
    private static final Vector2 temp = new Vector2();
    
    @Override
    public void create() {
        player = this;
        setSkeletonData(skeletonData, animationData);
        animationState.setAnimation(0, animationStand, true);
        animationData.setDefaultMix(.2f);
        setCollisionBox(skeleton.findSlot("bbox"), skeletonBounds, collisionFilter);
        gravityY = playerGravity;
        weapon = WHIP;
        weaponBone = skeleton.findBone("weapon");
        animationState.addListener(new AnimationStateAdapter() {
            @Override
            public void event(TrackEntry entry, Event event) {
                event.getData().getName().equals("spark");
                
                for (int i = 0; i < 20; i++) {
                    float offset = 100 + MathUtils.random(300f);
                    if (skeleton.getScaleX() < 0) offset *= -1;
                    var spark = new SparkEntity();
                    temp.set(0, 0);
                    weaponBone.localToWorld(temp);
                    spark.teleport(temp.x + offset, temp.y);
                    entityController.add(spark);
                }
            }
        });
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        boolean inAir = world.check(item, x, y - 1, collisionFilter).projectedCollisions.size() == 0;
        
        if (!selectingWeapon && isBindingPressed(LEFT)) {
            if (deltaX > -playerMaxSpeed) {
                deltaX -= (inAir ? playerAccelerationWhileJumping : playerAcceleration) * delta;
                if (deltaX < -playerMaxSpeed) deltaX = -playerMaxSpeed;
            }
            if (!inAir && animationState.getCurrent(0).getAnimation() != animationRun) animationState.setAnimation(0, animationRun, true);
            skeleton.setScale(-1, 1);
        } else if (!selectingWeapon && isBindingPressed(RIGHT)) {
            if (deltaX < playerMaxSpeed) {
                deltaX += (inAir ? playerAccelerationWhileJumping : playerAcceleration) * delta;
                if (deltaX > playerMaxSpeed) deltaX = playerMaxSpeed;
            }
            if (!inAir && animationState.getCurrent(0).getAnimation() != animationRun) animationState.setAnimation(0, animationRun, true);
            skeleton.setScale(1, 1);
        } else {
            deltaX = Utils.approach(deltaX, 0, (inAir ? playerDecelerationWhileJumping : playerDeceleration) * delta);
            if (!inAir && animationState.getCurrent(0).getAnimation() != animationStand) animationState.setAnimation(0, animationStand, true);
        }
        
        if ((!inAir || jumps < playerMaxJumps) && !selectingWeapon && isBindingJustPressed(JUMP)) {
            if (animationState.getCurrent(2) != null) animationState.setEmptyAnimation(2, .2f);
            jumpTime = 0;
            deltaY += playerJumpSpeed;
            animationState.setAnimation(0, animationJump, false);
            if (inAir) animationState.setAnimation(1, animationFlap, false);
            
            if (!inAir) jumps = 1;
            else jumps++;
            inAir = true;
        }
        
        if (inAir) {
            if (!selectingWeapon && isBindingPressed(JUMP)) {
                if (jumpTime < playerJumpHoldTime) {
                    if (deltaY < playerJumpSpeed) {
                        deltaY = playerJumpSpeed;
                    }
                }
            }
            jumpTime += delta;
        }
        
        if (!selectingWeapon && isBindingJustPressed(INVENTORY)) {
            GameScreen.gameScreen.showInventory();
            selectingWeapon = true;
        }
        
        if (isBindingJustPressed(ATTACK)) {
            var anim = animationState.getCurrent(2);
            Animation targetAnimation = null;
            
            if (weapon == WHIP) {
                if (inAir) targetAnimation = animationWhipJump;
                else targetAnimation = animationWhip;
            }
            
            if (targetAnimation != null && (anim == null || anim.getAnimation() != targetAnimation)) {
                animationState.setAnimation(2, targetAnimation, false);
                animationState.addEmptyAnimation(2, .2f, 0);
            }
        }
    }
    
    @Override
    public void draw(float delta) {
        if (Values.debugging) {
            shapeDrawer.setColor(Color.PURPLE);
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
                if (collision.normal.x > 0 && deltaX < 0) deltaX = 0;
                if (collision.normal.x < 0 && deltaX > 0) deltaX = 0;
                if (collision.normal.y > 0 && deltaY < 0) deltaY = 0;
                if (collision.normal.y < 0 && deltaY > 0) deltaY = 0;
            }
        }
    }

    private final static PlayerCollisionFilter collisionFilter = new PlayerCollisionFilter();
    
    private static class PlayerCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof  BoundsEntity) return Response.slide;
            return null;
        }
    }
}
