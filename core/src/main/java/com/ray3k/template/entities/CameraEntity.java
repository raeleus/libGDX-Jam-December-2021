package com.ray3k.template.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response.Result;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;

public class CameraEntity extends Entity {
    private PlayerEntity player;
    private float levelWidth;
    private float levelHeight;
    
    public CameraEntity(PlayerEntity player, float levelWidth, float levelHeight) {
        this.player = player;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
    }
    
    @Override
    public void create() {
    
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        setPosition(player.x, player.y + 150);
        if (Core.isButtonPressed(Buttons.LEFT)) camera.zoom = 2f;
        else camera.zoom = 1f;
        
        viewport.setMinWorldWidth(1024 / camera.zoom);
        viewport.setMaxWorldWidth(levelWidth / camera.zoom);
        viewport.setMinWorldHeight(576 / camera.zoom);
        viewport.setMaxWorldHeight(levelHeight / camera.zoom);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        if (camera.viewportWidth * camera.zoom > levelWidth) x = levelWidth / 2;
        else {
            if (x - camera.viewportWidth / 2 * camera.zoom < 0) x = camera.viewportWidth / 2 * camera.zoom;
            if (x + camera.viewportWidth / 2 * camera.zoom > levelWidth)
                x = levelWidth - camera.viewportWidth / 2 * camera.zoom;
        }
    
        if (camera.viewportHeight * camera.zoom > levelHeight) y = levelHeight / 2;
        else {
            if (y - camera.viewportHeight / 2 * camera.zoom < 0) y = camera.viewportHeight / 2 * camera.zoom;
            if (y + camera.viewportHeight / 2 * camera.zoom > levelHeight)
                y = levelHeight - camera.viewportHeight / 2 * camera.zoom;
        }
        camera.position.set(x, y, 0);
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
