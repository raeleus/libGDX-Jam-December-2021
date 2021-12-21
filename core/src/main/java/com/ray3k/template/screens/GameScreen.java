package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.stripe.PopTable;
import com.ray3k.template.*;
import com.ray3k.template.OgmoReader.*;
import com.ray3k.template.entities.*;
import com.ray3k.template.entities.PlayerEntity.*;
import com.ray3k.template.entities.PowerupEntity.*;
import com.ray3k.template.screens.DialogDebug.*;
import com.ray3k.template.screens.DialogPause.*;
import space.earlygrey.shapedrawer.ShapeDrawer;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.Values.*;

public class GameScreen extends JamScreen {
    public static GameScreen gameScreen;
    public static final Color BG_COLOR = new Color();
    public Stage stage;
    public boolean paused;
    public Label healthLabel;
    public Action slowAction;

    public static HealthBar healthBar = new HealthBar();
    public static MobileControlsUi mobileControlsUi = new MobileControlsUi(new ExtendViewport(1024, 576), skin);

    @Override
    public void show() {
        super.show();
    
        gameScreen = this;
        BG_COLOR.set(108 / 255f, 109 / 255f, 122 / 255f, 1f);
    
        paused = false;
    
        stage = new Stage(new ScreenViewport(), batch);
        
        var root = new Table();
        root.setFillParent(true);
        root.align(Align.topLeft);
        root.padLeft(200 + 32);
        root.padTop(31);
        stage.addActor(root);
        
        healthLabel = new Label("", skin);
        root.add(healthLabel);
        
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (!paused && keycode == Keys.ESCAPE) {
                    paused = true;
                
                    DialogPause dialogPause = new DialogPause(GameScreen.this);
                    dialogPause.show(stage);
                    dialogPause.addListener(new PauseListener() {
                        @Override
                        public void resume() {
                            paused = false;
                        }

                        @Override
                        public void respawn() {
                            paused = false;
                            PlayerEntity.player.hurt(9999, 0, 0);
                        }

                        @Override
                        public void quit() {
                            core.transition(new MenuScreen());
                        }
                    });
                }
                return super.keyDown(event, keycode);
            }
        });
    
        stage.addListener(new DebugListener());

        if (NATIVES.isMobile())
            Gdx.input.setInputProcessor(new InputMultiplexer(stage, mobileControlsUi, this));
        else
            Gdx.input.setInputProcessor(new InputMultiplexer(stage, this));

        camera = new OrthographicCamera();
        viewport = new FitViewport(1024, 576, camera);
    
        entityController.clear();
        shapeDrawer = new ShapeDrawer(batch, textures_textures.findRegion("game/white"));
        loadLevel(roomToLoad);
    }
    
    public void loadLevel(String name) {
        var ogmoReader = new OgmoReader();
        ogmoReader.addListener(new OgmoAdapter() {
            String layerName;
    
            @Override
            public void level(String ogmoVersion, int width, int height, int offsetX, int offsetY,
                              ObjectMap<String, OgmoValue> valuesMap) {
                levelWidth = width;
                levelHeight = height;
                levelZoom = valuesMap.get("zoom").asFloat();
            }
    
            @Override
            public void layer(String name, int gridCellWidth, int gridCellHeight, int offsetX, int offsetY) {
                layerName = name;
            }
    
            @Override
            public void entity(String name, int id, int x, int y, int width, int height, boolean flippedX,
                               boolean flippedY, int originX, int originY, int rotation, Array<EntityNode> nodes,
                               ObjectMap<String, OgmoValue> valuesMap) {
                switch (name) {
                    case "player":
                        if (valuesMap.get("spawn").asInt() == spawnIndex) {
                            var player = new PlayerEntity();
                            entityController.add(player);
                            player.teleport(x, y);
    
                            var cam = new CameraEntity(player, levelWidth, levelHeight);
                            cam.zoom = levelZoom;
    
                            entityController.add(cam);
                        }
                        break;
                    case "bounds":
                        var bounds = new BoundsEntity();
                        float minX = x;
                        float minY = y;
                        float maxX = x;
                        float maxY = y;
        
                        for (var node : nodes) {
                            if (node.x < minX) minX = node.x;
                            if (node.x > maxX) maxX = node.x;
                            if (node.y < minY) minY = node.y;
                            if (node.y > maxY) maxY = node.y;
                        }
                        bounds.setCollisionBox(0, 0, maxX - minX, maxY - minY, nullCollisionFilter);
                        entityController.add(bounds);
                        bounds.teleport(minX, minY);
                        bounds.depth = DEPTH_DEBUG;
                        break;
                    case "platform":
                        var platform = new PlatformEntity();
                        minX = x;
                        minY = y;
                        maxX = x;
                        maxY = y;
        
                        for (var node : nodes) {
                            if (node.x < minX) minX = node.x;
                            if (node.x > maxX) maxX = node.x;
                            if (node.y < minY) minY = node.y;
                            if (node.y > maxY) maxY = node.y;
                        }
                        platform.setCollisionBox(0, 0, maxX - minX, maxY - minY, nullCollisionFilter);
                        entityController.add(platform);
                        platform.teleport(minX, minY);
                        platform.depth = DEPTH_DEBUG;
                        break;
                    case "exit":
                        var exit = new ExitEntity();
                        minX = x;
                        minY = y;
                        maxX = x;
                        maxY = y;
    
                        for (var node : nodes) {
                            if (node.x < minX) minX = node.x;
                            if (node.x > maxX) maxX = node.x;
                            if (node.y < minY) minY = node.y;
                            if (node.y > maxY) maxY = node.y;
                        }
                        exit.setCollisionBox(0, 0, maxX - minX, maxY - minY, nullCollisionFilter);
                        entityController.add(exit);
                        exit.teleport(minX, minY);
                        exit.depth = DEPTH_DEBUG;
                        exit.nextRoom = valuesMap.get("nextroom").asString();
                        exit.transitionDirection = valuesMap.get("transitiondirection").asFloat();
                        exit.spawnIndex = valuesMap.get("spawn").asInt();
                        break;
                    case "heart":
                        var heart = new HeartEntity();
                        entityController.add(heart);
                        heart.teleport(x, y);
                        break;
                    case "powerup_shotgun":
                        if (!PlayerEntity.enabledWeapons.contains(Weapon.SHOTGUN, false)) {
                            var powerup = new PowerupEntity();
                            entityController.add(powerup);
                            powerup.teleport(x, y);
                            powerup.setPowerupType(PowerupType.SHOTGUN);
                        }
                        break;
                    case "powerup_cross":
                        if (!PlayerEntity.enabledWeapons.contains(Weapon.CROSS, false)) {
                            var powerup = new PowerupEntity();
                            entityController.add(powerup);
                            powerup.teleport(x, y);
                            powerup.setPowerupType(PowerupType.CROSS);
                        }
                        break;
                    case "powerup_grenade":
                        if (!PlayerEntity.enabledWeapons.contains(Weapon.GRENADE, false)) {
                            var powerup = new PowerupEntity();
                            entityController.add(powerup);
                            powerup.teleport(x, y);
                            powerup.setPowerupType(PowerupType.GRENADE);
                        }
                        break;
                    case "powerup_wings1":
                        if (!PlayerEntity.enabledWings.contains(PowerupType.WINGS1, false)) {
                            var powerup = new PowerupEntity();
                            entityController.add(powerup);
                            powerup.teleport(x, y);
                            powerup.setPowerupType(PowerupType.WINGS1);
                        }
                        break;
                    case "powerup_wings2":
                        if (!PlayerEntity.enabledWings.contains(PowerupType.WINGS2, false)) {
                            var powerup = new PowerupEntity();
                            entityController.add(powerup);
                            powerup.teleport(x, y);
                            powerup.setPowerupType(PowerupType.WINGS2);
                        }
                        break;
                    case "powerup_wings3":
                        if (!PlayerEntity.enabledWings.contains(PowerupType.WINGS3, false)) {
                            var powerup = new PowerupEntity();
                            entityController.add(powerup);
                            powerup.teleport(x, y);
                            powerup.setPowerupType(PowerupType.WINGS3);
                        }
                        break;
                    case "blob":
                        var blob = new BlobEntity();
                        entityController.add(blob);
                        blob.teleport(x, y);
                        break;
                    case "jellyfish":
                        var jellyfish = new JellyfishEntity();
                        entityController.add(jellyfish);
                        jellyfish.teleport(x, y);
                        jellyfish.skeleton.getRootBone().setRotation(rotation);
                        break;
                    case "dangler":
                        var dangler = new DanglerEntity();
                        entityController.add(dangler);
                        dangler.teleport(x, y);
                        break;
                    case "spinner":
                        var spinner = new SpinnerEntity();
                        entityController.add(spinner);
                        spinner.teleport(x, y);
                        spinner.skeleton.getRootBone().setRotation(rotation);
                        break;
                    case "bat":
                        var bat = new BatEntity();
                        entityController.add(bat);
                        bat.teleport(x, y);
                        break;
                    case "pirate":
                        var pirate = new PirateEntity();
                        entityController.add(pirate);
                        pirate.teleport(x, y);
                        break;
                    case "slime-dog":
                        var slimeDog = new SlimeDogEntity();
                        entityController.add(slimeDog);
                        slimeDog.teleport(x, y);
                        break;
                    case "jumper":
                        var jumper = new JumperEntity();
                        entityController.add(jumper);
                        jumper.teleport(x, y);
                        break;
                    case "shooter":
                        var shooter = new ShooterEntity();
                        entityController.add(shooter);
                        if (flippedX) shooter.skeleton.setScaleX(-1);
                        shooter.teleport(x, y);
                        break;
                    case "frozen":
                        var frozen = new FrozenEntity();
                        entityController.add(frozen);
                        frozen.teleport(x, y);
                        break;
                    case "glutton":
                        var glutton = new GluttonEntity();
                        entityController.add(glutton);
                        glutton.teleport(x, y);
                        break;
                    case "moth":
                        var moth = new MothEntity();
                        entityController.add(moth);
                        moth.teleport(x, y);
                        break;
                    case "skeleton":
                        var skeleton = new SkeletonEntity();
                        entityController.add(skeleton);
                        skeleton.teleport(x, y);
                        break;
                    case "wolf":
                        var wolf = new WolfEntity();
                        entityController.add(wolf);
                        wolf.teleport(x, y);
                        break;
                    case "pod":
                        var pod = new PodEntity();
                        entityController.add(pod);
                        pod.teleport(x, y);
                        break;
                    case "washer":
                        var washer = new WasherEntity();
                        entityController.add(washer);
                        washer.teleport(x, y);
                        break;
                    case "robot":
                        var robot = new RobotEntity();
                        entityController.add(robot);
                        robot.teleport(x, y);
                        break;
                    case "vacuum":
                        var vacuum = new VacuumEntity();
                        entityController.add(vacuum);
                        vacuum.teleport(x, y);
                        break;
                    case "toaster":
                        var toaster = new ToasterEntity();
                        entityController.add(toaster);
                        toaster.teleport(x, y);
                        break;
                    case "tank":
                        var tank = new TankEntity();
                        entityController.add(tank);
                        tank.teleport(x, y);
                        break;
                    case "groxar":
                        if (!defeatedGroxar) {
                            var groxar = new GroxarEntity();
                            entityController.add(groxar);
                            groxar.teleport(x, y);
                        }
                        break;
                    case "zebra":
                        if (!defeatedZebra) {
                            var zebra = new ZebraEntity();
                            entityController.add(zebra);
                            zebra.teleport(x, y);
                        }
                        break;
                    case "panda":
                        if (!defeatedPanda) {
                            var panda = new PandaEntity();
                            entityController.add(panda);
                            panda.teleport(x, y);
                        }
                        break;
                    case "lyze":
                        if (!defeatedLyze) {
                            var lyze = new LyzeEntity();
                            lyze.teleport(x, y);
                            for (var node : nodes) {
                                var point = new Vector2();
                                point.set(node.x, node.y);
                                lyze.points.add(point);
                            }
                            entityController.add(lyze);
                        }
                        break;
                    case "john":
                        if (!defeatedJohn) {
                            var john = new JohnEntity();
                            entityController.add(john);
                            john.teleport(x, y);
                        }
                        break;
                    case "door":
                        var door = new DoorEntity();
                        entityController.add(door);
                        door.teleport(x, y);
                        break;
                    case "dialog":
                        DialogMaker.show(valuesMap.get("index").asInt());
                        break;
                    case "podspawner":
                        var podSpawner = new PodSpawnerEntity();
                        entityController.add(podSpawner);
                        break;
                }
            }
    
            @Override
            public void decal(int x, int y, float originX, float originY, float scaleX, float scaleY, int rotation,
                              String texture, String folder, ObjectMap<String, OgmoValue> valuesMap) {
                var decal = new DecalEntity(Resources.textures_textures.createSprite(folder + "/" + Utils.filePathNoExtension(texture)), x, y);
                switch (layerName) {
                    case "background":
                        decal.depth = DEPTH_BACKGROUND;
                        break;
                    case "foreground":
                        decal.depth = DEPTH_FOREGROUND;
                        break;
                    case "stationary":
                        decal.depth = DEPTH_STATIONARY;
                        decal.panning = false;
                        break;
                }
                entityController.add(decal);
            }
        });
        ogmoReader.readFile(Gdx.files.internal("levels/" + name + ".json"));
    }
    
    private final static Vector2 temp = new Vector2();
    
    public void showInventory() {
        if (slowAction != null) stage.getRoot().removeAction(slowAction);
        slowAction = new TemporalAction(.5f, Interpolation.smooth) {
            @Override
            protected void update(float percent) {
                deltaMultiplier = (1 - percent) *  (1 - .05f) + .05f;
            }
        };
        stage.addAction(slowAction);
        var selectionImage = new Image(skin.getDrawable("powerup-selection"));
        var whipImage = new ImageButton(skin.getDrawable("powerup-whip"));
        whipImage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MOBILE_CONTROLS.setJustPressedOnly(Binding.JUMP, true);
            }
        });
        var shotgunImage = new ImageButton(skin.getDrawable("powerup-shotgun"));
        shotgunImage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MOBILE_CONTROLS.setJustPressedOnly(Binding.LEFT, true);
            }
        });
        var grenadeImage = new ImageButton(skin.getDrawable("powerup-grenade"));
        grenadeImage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MOBILE_CONTROLS.setJustPressedOnly(Binding.RIGHT, true);
            }
        });
        var crossImage = new ImageButton(skin.getDrawable("powerup-cross"));
        crossImage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MOBILE_CONTROLS.setJustPressedOnly(Binding.DOWN, true);
            }
        });
        
        var popTable = new PopTable() {
            float hideTimer;
            boolean stopInput;
            @Override
            public void act(float delta) {
                super.act(delta);
                
                if (!stopInput) {
                    if (PlayerEntity.enabledWeapons.contains(Weapon.WHIP, false) && isBindingJustPressed(Binding.JUMP)) {
                        temp.set(whipImage.getX(Align.center) - selectionImage.getWidth() / 2,
                                whipImage.getY(Align.center) - selectionImage.getHeight() / 2);
                        whipImage.localToActorCoordinates(this, temp);
                        selectionImage.addAction(Actions.moveTo(temp.x, temp.y, .2f, Interpolation.smooth));
                        hideTimer = .1f;
                        PlayerEntity.player.weapon = Weapon.WHIP;
                    } else if (PlayerEntity.enabledWeapons.contains(Weapon.SHOTGUN, false) && isBindingJustPressed(Binding.LEFT)) {
                        temp.set(shotgunImage.getX(Align.center) - selectionImage.getWidth() / 2,
                                shotgunImage.getY(Align.center) - selectionImage.getHeight() / 2);
                        shotgunImage.localToActorCoordinates(this, temp);
                        selectionImage.addAction(Actions.moveTo(temp.x, temp.y, .2f, Interpolation.smooth));
                        hideTimer = .1f;
                        PlayerEntity.player.weapon = Weapon.SHOTGUN;
                    } else if (PlayerEntity.enabledWeapons.contains(Weapon.GRENADE, false) && isBindingJustPressed(Binding.RIGHT)) {
                        temp.set(grenadeImage.getX(Align.center) - selectionImage.getWidth() / 2,
                                grenadeImage.getY(Align.center) - selectionImage.getHeight() / 2);
                        grenadeImage.localToActorCoordinates(this, temp);
                        selectionImage.addAction(Actions.moveTo(temp.x, temp.y, .2f, Interpolation.smooth));
                        hideTimer = .1f;
                        PlayerEntity.player.weapon = Weapon.GRENADE;
                    } else if (PlayerEntity.enabledWeapons.contains(Weapon.CROSS, false) && isBindingJustPressed(Binding.DOWN)) {
                        temp.set(crossImage.getX(Align.center) - selectionImage.getWidth() / 2,
                                crossImage.getY(Align.center) - selectionImage.getHeight() / 2);
                        crossImage.localToActorCoordinates(this, temp);
                        selectionImage.addAction(Actions.moveTo(temp.x, temp.y, .2f, Interpolation.smooth));
                        hideTimer = .1f;
                        PlayerEntity.player.weapon = Weapon.CROSS;
                    }
    
                    if (isBindingPressed(Binding.INVENTORY)) {
                        hideTimer = .1f;
                    }
                }
                
                if (hideTimer > 0) {
                    hideTimer -= delta;
                    if (hideTimer < 0) {
                        stopInput = true;
                        hide();
                        PlayerEntity.player.selectingWeapon = false;
                        if (slowAction != null) stage.getRoot().removeAction(slowAction);
                        slowAction = Actions.sequence(new TemporalAction(1f, Interpolation.smooth) {
                            @Override
                            protected void update(float percent) {
                                deltaMultiplier = percent *  (1 - .05f) + .05f;
                            }
                        });
                        stage.addAction(slowAction);
                    }
                }
            }
        };
        popTable.setKeepCenteredInWindow(true);
        
        popTable.defaults().uniform().space(30);
        
        var stack = new Stack();
        if (PlayerEntity.enabledWeapons.contains(Weapon.WHIP, false)) stack.add(whipImage);
        popTable.add(stack).colspan(3);
        if (PlayerEntity.player.weapon == Weapon.WHIP) {
            var image = new Image(skin.getDrawable("powerup-selected"));
            stack.add(image);
        }
        
        popTable.row();
        stack = new Stack();
        if (PlayerEntity.enabledWeapons.contains(Weapon.SHOTGUN, false)) stack.add(shotgunImage);
        popTable.add(stack);
        if (PlayerEntity.player.weapon == Weapon.SHOTGUN) {
            var image = new Image(skin.getDrawable("powerup-selected"));
            stack.add(image);
        }
    
        popTable.add();
        
        stack = new Stack();
        if (PlayerEntity.enabledWeapons.contains(Weapon.GRENADE, false)) stack.add(grenadeImage);
        popTable.add(stack);
        if (PlayerEntity.player.weapon == Weapon.GRENADE) {
            var image = new Image(skin.getDrawable("powerup-selected"));
            stack.add(image);
        }
        
        popTable.row();
        stack = new Stack();
        if (PlayerEntity.enabledWeapons.contains(Weapon.CROSS, false)) stack.add(crossImage);
        popTable.add(stack).colspan(3);
        if (PlayerEntity.player.weapon == Weapon.CROSS) {
            var image = new Image(skin.getDrawable("powerup-selected"));
            stack.add(image);
        }
        
        popTable.show(stage);
        
        selectionImage.setPosition(popTable.getWidth() / 2, popTable.getHeight() / 2, Align.center);
        popTable.addActor(selectionImage);
        
    }

    @Override
    public void act(float delta) {
        if (!paused) {
            entityController.act(delta * deltaMultiplier);
            vfxManager.update(delta * deltaMultiplier);
        }
        stage.act(delta);
        if (NATIVES.isMobile())
            mobileControlsUi.act(delta);
        healthBar.act(delta);
        GameScreen.gameScreen.healthLabel.setText(Integer.toString(playerHealth));
    }

    @Override
    public void draw(float delta) {
        batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(Color.WHITE);
        vfxManager.cleanUpBuffers();
        vfxManager.beginInputCapture();
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        entityController.draw(paused ? 0 : delta);
        batch.end();
        vfxManager.endInputCapture();
        vfxManager.applyEffects();
        vfxManager.renderToScreen();
    
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        stage.draw();
        if (NATIVES.isMobile())
            mobileControlsUi.draw();
        healthBar.draw(stage.getViewport(), batch);
    }
    
    @Override
    public void resize(int width, int height) {
        if (width + height != 0) {
            vfxManager.resize(width, height);
            viewport.update(width, height);
        
            stage.getViewport().update(width, height, true);
        }
    }
    
    @Override
    public void dispose() {
    
    }
    
    @Override
    public void hide() {
        super.hide();
        vfxManager.removeAllEffects();
        entityController.dispose();
    }
    
    @Override
    public void pause() {
    
    }
    
    @Override
    public void resume() {
    
    }
}
