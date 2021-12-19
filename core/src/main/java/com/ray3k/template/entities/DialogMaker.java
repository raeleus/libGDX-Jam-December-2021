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
        deltaMultiplier = 0;
        
        switch (dialog) {
            case 0:
                if (!defeatedZebra) {
                    var pop = createTable(() -> showMessage2());
    
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
        }
    }
    
    private static void showMessage2() {
        var pop = createTable(() -> showMessage3());
    
        var image = new Image(skin.getDrawable("icon-dq"));
        image.setScaling(Scaling.none);
        pop.add(image);
    
        var label = new TypingLabel("The Ferocious Zebra! I thought you were dead.", skin);
        label.setWrap(true);
        pop.add(label).grow();
    
        pop.show(gameScreen.stage);
    }
    
    private static void showMessage3() {
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
    
    private static PopTable createTable(Runnable runnable) {
        var pop = new PopTable() {
            @Override
            public void act(float delta) {
                super.act(delta);
                if (Core.isAnyKeyPressed()) {
//                    hide();
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
