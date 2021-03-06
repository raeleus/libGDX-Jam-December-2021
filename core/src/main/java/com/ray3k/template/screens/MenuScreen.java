package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.template.*;
import com.ray3k.template.entities.*;
import com.ray3k.template.entities.PlayerEntity.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.Values.*;

public class MenuScreen extends JamScreen {
    private Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    
    @Override
    public void show() {
        super.show();
    
        
        
        stage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(stage);
    
        bgm_explore.setLooping(true);
        bgm_explore.setVolume(0);
        bgm_explore.setPosition(bgm_battle.getPosition());
        bgm_explore.play();
        stage.addAction(new TemporalAction(1.0f) {
            @Override
            protected void update(float percent) {
                bgm_explore.setVolume(percent * bgm);
                bgm_battle.setVolume((1-percent) * bgm);
            }
        });
    
        sceneBuilder.build(stage, skin, Gdx.files.internal("menus/main.json"));
        TextButton textButton = stage.getRoot().findActor("play");
        textButton.addListener(sndChangeListener);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setInputProcessor(null);
                Values.playerMaxJumps = 1;
                PlayerEntity.enabledWeapons.clear();
                PlayerEntity.enabledWeapons.add(Weapon.WHIP);
                PlayerEntity.enabledWings.clear();
                roomToLoad = "level1";
                spawnIndex = 0;
                defeatedGroxar = false;
                defeatedJohn = false;
                defeatedLyze = false;
                defeatedPanda = false;
                defeatedZebra = false;
                bossAlive = false;
                playerHealth = playerMaxHealth;
                PlayerEntity.weapon = Weapon.WHIP;
                core.transition(new GameScreen());
            }
        });
    
        textButton = stage.getRoot().findActor("options");
        textButton.addListener(sndChangeListener);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setInputProcessor(null);
                core.transition(new OptionsScreen());
            }
        });
    
        textButton = stage.getRoot().findActor("credits");
        textButton.addListener(sndChangeListener);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setInputProcessor(null);
                core.transition(new CreditsScreen());
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
    public void pause() {
    
    }
    
    @Override
    public void resume() {
    
    }
    
    @Override
    public void dispose() {
    
    }
}
