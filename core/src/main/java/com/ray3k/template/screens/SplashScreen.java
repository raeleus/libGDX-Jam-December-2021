package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.template.*;
import com.ray3k.template.Resources.*;
import com.ray3k.template.entities.*;
import com.ray3k.template.entities.PlayerEntity.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.Values.*;

public class SplashScreen extends JamScreen {
    private Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    
    @Override
    public void show() {
        super.show();
        
        stage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        
        sceneBuilder.build(stage, skin, Gdx.files.internal("menus/splash.json"));
        
        stage.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(null);
                core.transition(new LibgdxScreen());
            }
        });
    
        stage.addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayerEntity.enabledWeapons.clear();
                PlayerEntity.enabledWeapons.add(Weapon.WHIP);
                PlayerEntity.enabledWeapons.add(Weapon.CROSS);
                PlayerEntity.enabledWeapons.add(Weapon.GRENADE);
                PlayerEntity.enabledWeapons.add(Weapon.SHOTGUN);
                PlayerEntity.weapon = Weapon.WHIP;
                playerMaxJumps = 4;
                godMode = true;
                Gdx.input.setInputProcessor(null);
                roomToLoad = "level1";
                spawnIndex = 0;
                defeatedGroxar = false;
                defeatedJohn = false;
                defeatedLyze = false;
                defeatedPanda = false;
                defeatedZebra = false;
                bossAlive = false;
                playerHealth = playerMaxHealth;
                core.transition(new GameScreen());
            }
        });
    }
    
    @Override
    public void act(float delta) {
        stage.act(delta);
    }
    
    @Override
    public void draw(float delta) {
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void dispose() {
        stage.dispose();
    }
    
    @Override
    public void pause() {
    
    }
    
    @Override
    public void resume() {
    
    }
}
