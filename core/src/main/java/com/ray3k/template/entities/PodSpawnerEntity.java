package com.ray3k.template.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;

import static com.ray3k.template.Core.*;

public class PodSpawnerEntity extends Entity {
    private float timer;
    private final static Vector2 temp = new Vector2();
    @Override
    public void create() {
    
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        timer -= delta;
        if (bossAlive && timer < 0) {
            timer = 5;
            var pod = new PodEntity();
            entityController.add(pod);
            temp.set(camera.viewportWidth * camera.zoom, camera.viewportHeight * camera.zoom);
            temp.rotateDeg(MathUtils.random(360f));
            temp.add(camera.position.x, camera.position.y);
            pod.setPosition(temp.x, temp.y);
        }
    }
    
    @Override
    public void draw(float delta) {
    
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
