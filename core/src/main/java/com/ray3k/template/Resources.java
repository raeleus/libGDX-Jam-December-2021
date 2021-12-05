package com.ray3k.template;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.SkeletonData;
import java.lang.String;

public class Resources {
    public static TextureAtlas textures_textures;

    public static Skin skin_skin;

    public static Sound sfx_canPop;

    public static Sound sfx_canShake;

    public static Sound sfx_canSpray;

    public static Sound sfx_crack;

    public static Sound sfx_good;

    public static Sound sfx_grinder;

    public static Sound sfx_intro;

    public static Sound sfx_poot;

    public static Music bgm_audioSample;

    public static Music bgm_menu;

    public static void loadResources(AssetManager assetManager) {
        textures_textures = assetManager.get("textures/textures.atlas");
        SpineLibgdx.skeletonData = assetManager.get("spine/libgdx.json");
        SpineLibgdx.animationData = assetManager.get("spine/libgdx.json-animation");
        SpineLibgdx.animationAnimation = SpineLibgdx.skeletonData.findAnimation("animation");
        SpineLibgdx.animationStand = SpineLibgdx.skeletonData.findAnimation("stand");
        SpineLibgdx.skinDefault = SpineLibgdx.skeletonData.findSkin("default");
        SpineRay3k.skeletonData = assetManager.get("spine/ray3k.json");
        SpineRay3k.animationData = assetManager.get("spine/ray3k.json-animation");
        SpineRay3k.animationAnimation = SpineRay3k.skeletonData.findAnimation("animation");
        SpineRay3k.animationStand = SpineRay3k.skeletonData.findAnimation("stand");
        SpineRay3k.skinDefault = SpineRay3k.skeletonData.findSkin("default");
        skin_skin = assetManager.get("skin/skin.json");
        SkinSkinStyles.ibLevel8 = skin_skin.get("level-8", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibOptions = skin_skin.get("options", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibLevel13 = skin_skin.get("level-13", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibDefaults = skin_skin.get("defaults", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibLevel5 = skin_skin.get("level-5", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibDefault = skin_skin.get("default", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibMortar = skin_skin.get("mortar", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibLevel10 = skin_skin.get("level-10", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibLevel2 = skin_skin.get("level-2", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibMine = skin_skin.get("mine", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibLevel15 = skin_skin.get("level-15", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibLevel7 = skin_skin.get("level-7", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibAssault = skin_skin.get("assault", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibSniper = skin_skin.get("sniper", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibLevel12 = skin_skin.get("level-12", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibBomb = skin_skin.get("bomb", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibLevel4 = skin_skin.get("level-4", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibMilitia = skin_skin.get("militia", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibLevel9 = skin_skin.get("level-9", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibLevel1 = skin_skin.get("level-1", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibLevel14 = skin_skin.get("level-14", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibOk = skin_skin.get("ok", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibLevel6 = skin_skin.get("level-6", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibLevel11 = skin_skin.get("level-11", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibHeavy = skin_skin.get("heavy", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibCredits = skin_skin.get("credits", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibLevel3 = skin_skin.get("level-3", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibPlay = skin_skin.get("play", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.ibBindings = skin_skin.get("bindings", ImageButton.ImageButtonStyle.class);
        SkinSkinStyles.lDefault = skin_skin.get("default", Label.LabelStyle.class);
        SkinSkinStyles.spDefault = skin_skin.get("default", ScrollPane.ScrollPaneStyle.class);
        SkinSkinStyles.sDefaultHorizontal = skin_skin.get("default-horizontal", Slider.SliderStyle.class);
        SkinSkinStyles.tbToggle = skin_skin.get("toggle", TextButton.TextButtonStyle.class);
        SkinSkinStyles.tbDefault = skin_skin.get("default", TextButton.TextButtonStyle.class);
        SkinSkinStyles.tfDefault = skin_skin.get("default", TextField.TextFieldStyle.class);
        SkinSkinStyles.ttDefault = skin_skin.get("default", TextTooltip.TextTooltipStyle.class);
        SkinSkinStyles.wDefault = skin_skin.get("default", Window.WindowStyle.class);
        sfx_canPop = assetManager.get("sfx/can-pop.mp3");
        sfx_canShake = assetManager.get("sfx/can-shake.mp3");
        sfx_canSpray = assetManager.get("sfx/can-spray.mp3");
        sfx_crack = assetManager.get("sfx/crack.mp3");
        sfx_good = assetManager.get("sfx/good.mp3");
        sfx_grinder = assetManager.get("sfx/grinder.mp3");
        sfx_intro = assetManager.get("sfx/intro.mp3");
        sfx_poot = assetManager.get("sfx/poot.mp3");
        bgm_audioSample = assetManager.get("bgm/audio-sample.mp3");
        bgm_menu = assetManager.get("bgm/menu.mp3");
    }

    public static class SpineLibgdx {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationStand;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineRay3k {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationStand;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SkinSkinStyles {
        public static ImageButton.ImageButtonStyle ibLevel8;

        public static ImageButton.ImageButtonStyle ibOptions;

        public static ImageButton.ImageButtonStyle ibLevel13;

        public static ImageButton.ImageButtonStyle ibDefaults;

        public static ImageButton.ImageButtonStyle ibLevel5;

        public static ImageButton.ImageButtonStyle ibDefault;

        public static ImageButton.ImageButtonStyle ibMortar;

        public static ImageButton.ImageButtonStyle ibLevel10;

        public static ImageButton.ImageButtonStyle ibLevel2;

        public static ImageButton.ImageButtonStyle ibMine;

        public static ImageButton.ImageButtonStyle ibLevel15;

        public static ImageButton.ImageButtonStyle ibLevel7;

        public static ImageButton.ImageButtonStyle ibAssault;

        public static ImageButton.ImageButtonStyle ibSniper;

        public static ImageButton.ImageButtonStyle ibLevel12;

        public static ImageButton.ImageButtonStyle ibBomb;

        public static ImageButton.ImageButtonStyle ibLevel4;

        public static ImageButton.ImageButtonStyle ibMilitia;

        public static ImageButton.ImageButtonStyle ibLevel9;

        public static ImageButton.ImageButtonStyle ibLevel1;

        public static ImageButton.ImageButtonStyle ibLevel14;

        public static ImageButton.ImageButtonStyle ibOk;

        public static ImageButton.ImageButtonStyle ibLevel6;

        public static ImageButton.ImageButtonStyle ibLevel11;

        public static ImageButton.ImageButtonStyle ibHeavy;

        public static ImageButton.ImageButtonStyle ibCredits;

        public static ImageButton.ImageButtonStyle ibLevel3;

        public static ImageButton.ImageButtonStyle ibPlay;

        public static ImageButton.ImageButtonStyle ibBindings;

        public static Label.LabelStyle lDefault;

        public static ScrollPane.ScrollPaneStyle spDefault;

        public static Slider.SliderStyle sDefaultHorizontal;

        public static TextButton.TextButtonStyle tbToggle;

        public static TextButton.TextButtonStyle tbDefault;

        public static TextField.TextFieldStyle tfDefault;

        public static TextTooltip.TextTooltipStyle ttDefault;

        public static Window.WindowStyle wDefault;
    }

    public static class Values {
        public static float jumpVelocity = 10.0f;

        public static String name = "Raeleus";

        public static boolean godMode = true;

        public static int id = 10;

        public static Range speedLimitRange = new Range(0.0f, 10.0f);

        public static float speedLimit = 5.0f;
    }

    public static class Range {
        public float min;

        public float max;

        Range(float min, float max) {
            this.min = min;
            this.max = max;
        }
    }
}
