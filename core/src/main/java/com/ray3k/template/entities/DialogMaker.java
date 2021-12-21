package com.ray3k.template.entities;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;
import com.ray3k.stripe.PopTable;
import com.ray3k.stripe.PopTable.TableShowHideListener;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.screens.GameScreen.*;

public class DialogMaker {
    public static void show(int dialog) {
        switch (dialog) {
            case 0:
                if (!defeatedZebra) {
                    deltaMultiplier = 0;
                    var pop = createTable(() -> showMessageZebra1());
            
                    var image = new Image(skin.getDrawable("icon-zebra"));
                    image.setScaling(Scaling.none);
                    pop.add(image);
            
                    var label = new TypingLabel(
                            "Ahh Dragon Queen, at long last! You will finally meet your doom. HA HA HA HA HA!", skin);
                    label.setWrap(true);
                    pop.add(label).grow();
            
                    pop.show(gameScreen.stage);
                }
                break;
            case 1:
                if (!defeatedGroxar) {
                    deltaMultiplier = 0;
                    var pop = createTable(() -> showMessageGroxar1());
            
                    var image = new Image(skin.getDrawable("icon-groxar"));
                    image.setScaling(Scaling.none);
                    pop.add(image);
            
                    var label = new TypingLabel(
                            "...", skin);
                    label.setWrap(true);
                    pop.add(label).grow();
            
                    pop.show(gameScreen.stage);
                }
                break;
            case 2:
                if (!defeatedPanda) {
                    deltaMultiplier = 0;
                    var pop = createTable(() -> showMessagePanda1());
            
                    var image = new Image(skin.getDrawable("icon-panda"));
                    image.setScaling(Scaling.none);
                    pop.add(image);
            
                    var label = new TypingLabel(
                            "I will melt your bones and make a candle out of it.", skin);
                    label.setWrap(true);
                    pop.add(label).grow();
            
                    pop.show(gameScreen.stage);
                }
                break;
            case 3:
                if (!defeatedLyze) {
                    deltaMultiplier = 0;
                    var pop = createTable(() -> showMessageLyze1());
            
                    var image = new Image(skin.getDrawable("icon-lyze"));
                    image.setScaling(Scaling.none);
                    pop.add(image);
            
                    var label = new TypingLabel(
                            "Duty requires that I subdue you. Take note that I derive no pleasure from taking your life.", skin);
                    label.setWrap(true);
                    pop.add(label).grow();
            
                    pop.show(gameScreen.stage);
                }
                break;
            case 4:
                if (!defeatedJohn) {
                    deltaMultiplier = 0;
                    var pop = createTable(() -> showMessageJohn1());
            
                    var image = new Image(skin.getDrawable("icon-john"));
                    image.setScaling(Scaling.none);
                    pop.add(image);
            
                    var label = new TypingLabel(
                            "HEEEEEEEELLLP MEEEEEEEE...", skin);
                    label.setWrap(true);
                    pop.add(label).grow();
            
                    pop.show(gameScreen.stage);
                }
                break;
        }
    }
    
    private static void showMessageZebra1() {
        var pop = createTable(() -> showMessageZebra2());
    
        var image = new Image(skin.getDrawable("icon-dq"));
        image.setScaling(Scaling.none);
        pop.add(image);
    
        var label = new TypingLabel("The Ferocious Zebra! I thought you were dead.", skin);
        label.setWrap(true);
        pop.add(label).grow();
    
        pop.show(gameScreen.stage);
    }
    
    private static void showMessageZebra2() {
        var pop = createTable(() -> {
            deltaMultiplier = 1;
        });
        
        var image = new Image(skin.getDrawable("icon-zebra"));
        image.setScaling(Scaling.none);
        pop.add(image);
        
        var label = new TypingLabel("I was and YOU will be too. Have at you!!!", skin);
        label.setWrap(true);
        pop.add(label).grow();
        
        pop.show(gameScreen.stage);
    }
    
    private static void showMessageGroxar1() {
        var pop = createTable(() -> {
            deltaMultiplier = 1;
        });
        
        var image = new Image(skin.getDrawable("icon-dq"));
        image.setScaling(Scaling.none);
        pop.add(image);
        
        var label = new TypingLabel("What???", skin);
        label.setWrap(true);
        pop.add(label).grow();
        
        pop.show(gameScreen.stage);
    }
    
    private static void showMessagePanda1() {
        var pop = createTable(() -> {
            deltaMultiplier = 1;
        });
        
        var image = new Image(skin.getDrawable("icon-dq"));
        image.setScaling(Scaling.none);
        pop.add(image);
        
        var label = new TypingLabel("Ooof, not another boss... Ready yourself, mortal!", skin);
        label.setWrap(true);
        pop.add(label).grow();
        
        pop.show(gameScreen.stage);
    }
    
    private static void showMessageLyze1() {
        var pop = createTable(() -> {
            deltaMultiplier = 1;
        });
        
        var image = new Image(skin.getDrawable("icon-dq"));
        image.setScaling(Scaling.none);
        pop.add(image);
        
        var label = new TypingLabel("Let's just get this over with, feathered one.", skin);
        label.setWrap(true);
        pop.add(label).grow();
        
        pop.show(gameScreen.stage);
    }
    
    private static void showMessageJohn1() {
        var pop = createTable(() -> {
            deltaMultiplier = 1;
        });
        
        var image = new Image(skin.getDrawable("icon-dq"));
        image.setScaling(Scaling.none);
        pop.add(image);
        
        var label = new TypingLabel("OMG, John! What did they do to you? I shall... release you.", skin);
        label.setWrap(true);
        pop.add(label).grow();
        
        pop.show(gameScreen.stage);
    }
    
    private static PopTable createTable(Runnable runnable) {
        var pop = new PopTable() {
            @Override
            public void act(float delta) {
                super.act(delta);
                if (Core.isAnyKeyJustPressed() && !Core.isAnyBindingJustPressed(Binding.LEFT, Binding.RIGHT, Binding.JUMP, Binding.DOWN)) {
                    hide();
                }
            }
        };
        pop.setBackground(skin.getDrawable("dialog-10"));
        pop.setHideOnUnfocus(true);
        pop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pop.hide();
            }
        });
        pop.addListener(new TableShowHideListener() {
            @Override
            public void tableShown(Event event) {
            
            }
        
            @Override
            public void tableHidden(Event event) {
                runnable.run();
            }
        });
        return pop;
    }
}
