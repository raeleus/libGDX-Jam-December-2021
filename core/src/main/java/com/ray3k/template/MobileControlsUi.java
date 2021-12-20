package com.ray3k.template;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ray3k.template.entities.PlayerEntity;

import static com.ray3k.template.Core.MOBILE_CONTROLS;
import static com.ray3k.template.Core.skin;

public class MobileControlsUi extends Stage {
    private ImageButton controlsButton, upButton, downButton;
    private ImageButton attackButton, inventoryButton;

    public MobileControlsUi(Viewport viewport, Skin skin) {
        super(viewport);

        addUpDownControls();
        addLeftRightControls();

        addAttackButton(skin);
        addInventoryButton();
    }

    private void addInventoryButton() {
        Table root = new Table();
        root.setFillParent(true);

        inventoryButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("textures/x.png"))));
        inventoryButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MOBILE_CONTROLS.setPressed(Core.Binding.INVENTORY, true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MOBILE_CONTROLS.setPressed(Core.Binding.INVENTORY, false);
            }
        });

        Table rightSide = new Table();

        rightSide.add(inventoryButton).row();

        root.add(rightSide).align(Align.topRight).pad(45).expand();
        addActor(root);
    }

    private void addAttackButton(Skin skin) {
        Table root = new Table();
        root.setFillParent(true);

        attackButton = new ImageButton(skin.getDrawable("powerup-whip"));
        attackButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MOBILE_CONTROLS.setPressed(Core.Binding.ATTACK, true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MOBILE_CONTROLS.setPressed(Core.Binding.ATTACK, false);
            }
        });

        Table rightSide = new Table();

        rightSide.add(attackButton).row();

        root.add(rightSide).align(Align.bottomRight).pad(140).expand();
        addActor(root);
    }

    private void addUpDownControls() {
        Table root = new Table();
        root.setFillParent(true);

        upButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("textures/up.png"))));
        upButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MOBILE_CONTROLS.setPressed(Core.Binding.JUMP, true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MOBILE_CONTROLS.setPressed(Core.Binding.JUMP, false);
            }
        });
        downButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("textures/down.png"))));
        downButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MOBILE_CONTROLS.setPressed(Core.Binding.DOWN, true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MOBILE_CONTROLS.setPressed(Core.Binding.DOWN, false);
            }
        });

        Table rightSide = new Table();

        rightSide.add(upButton).row();
        rightSide.add(downButton).row();

        root.add(rightSide).align(Align.bottomRight).pad(24).expand();
        addActor(root);
    }

    private void addLeftRightControls() {
        Table root = new Table();
        root.setFillParent(true);

        Table leftSide = new Table();

        controlsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("textures/controls.png"))));
        controlsButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean leftPressed = x < controlsButton.getWidth() / 2f;
                MOBILE_CONTROLS.setPressed(Core.Binding.LEFT, leftPressed);
                MOBILE_CONTROLS.setPressed(Core.Binding.RIGHT, !leftPressed);

                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                boolean leftPressed = x < controlsButton.getWidth() / 2f;
                MOBILE_CONTROLS.setPressed(Core.Binding.LEFT, leftPressed);
                MOBILE_CONTROLS.setPressed(Core.Binding.RIGHT, !leftPressed);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MOBILE_CONTROLS.setPressed(Core.Binding.LEFT, false);
                MOBILE_CONTROLS.setPressed(Core.Binding.RIGHT, false);
            }
        });

        leftSide.add(controlsButton);

        root.add(leftSide).align(Align.bottomLeft).pad(32).expand();
        addActor(root);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        Drawable whipImage = skin.getDrawable("powerup-whip");
        Drawable shotgunImage = skin.getDrawable("powerup-shotgun");
        Drawable grenadeImage = skin.getDrawable("powerup-grenade");
        Drawable crossImage =  skin.getDrawable("powerup-cross");

        switch (PlayerEntity.weapon) {
            case WHIP:
                attackButton.getStyle().imageUp = whipImage;
                break;
            case GRENADE:
                attackButton.getStyle().imageUp = grenadeImage;
                break;
            case SHOTGUN:
                attackButton.getStyle().imageUp = shotgunImage;
                break;
            case CROSS:
                attackButton.getStyle().imageUp = crossImage;
                break;
        }
    }
}
