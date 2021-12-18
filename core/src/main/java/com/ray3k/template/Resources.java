package com.ray3k.template;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
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

public class Resources {
    public static TextureAtlas textures_textures;

    public static Skin skin_skin;

    public static Sound sfx_canPop;

    public static Sound sfx_canShake;

    public static Sound sfx_canSpray;

    public static Sound sfx_click;

    public static Sound sfx_crack;

    public static Sound sfx_good;

    public static Sound sfx_grinder;

    public static Sound sfx_intro;

    public static Sound sfx_poot;

    public static Music bgm_audioSample;

    public static Music bgm_menu;

    public static void loadResources(AssetManager assetManager) {
        textures_textures = assetManager.get("textures/textures.atlas");
        SpineBat.skeletonData = assetManager.get("spine/bat.json");
        SpineBat.animationData = assetManager.get("spine/bat.json-animation");
        SpineBat.animationDie = SpineBat.skeletonData.findAnimation("die");
        SpineBat.animationFly = SpineBat.skeletonData.findAnimation("fly");
        SpineBat.animationHurt = SpineBat.skeletonData.findAnimation("hurt");
        SpineBat.skinDefault = SpineBat.skeletonData.findSkin("default");
        SpineBlob.skeletonData = assetManager.get("spine/blob.json");
        SpineBlob.animationData = assetManager.get("spine/blob.json-animation");
        SpineBlob.animationAnimation = SpineBlob.skeletonData.findAnimation("animation");
        SpineBlob.animationDie = SpineBlob.skeletonData.findAnimation("die");
        SpineBlob.animationHurt = SpineBlob.skeletonData.findAnimation("hurt");
        SpineBlob.skinDefault = SpineBlob.skeletonData.findSkin("default");
        SpineClock.skeletonData = assetManager.get("spine/clock.json");
        SpineClock.animationData = assetManager.get("spine/clock.json-animation");
        SpineClock.animationAttack = SpineClock.skeletonData.findAnimation("attack");
        SpineClock.animationDie = SpineClock.skeletonData.findAnimation("die");
        SpineClock.animationHurt = SpineClock.skeletonData.findAnimation("hurt");
        SpineClock.animationStand = SpineClock.skeletonData.findAnimation("stand");
        SpineClock.skinDefault = SpineClock.skeletonData.findSkin("default");
        SpineCross.skeletonData = assetManager.get("spine/cross.json");
        SpineCross.animationData = assetManager.get("spine/cross.json-animation");
        SpineCross.animationAnimation = SpineCross.skeletonData.findAnimation("animation");
        SpineCross.skinDefault = SpineCross.skeletonData.findSkin("default");
        SpineDangler.skeletonData = assetManager.get("spine/dangler.json");
        SpineDangler.animationData = assetManager.get("spine/dangler.json-animation");
        SpineDangler.animationDie = SpineDangler.skeletonData.findAnimation("die");
        SpineDangler.animationHurt = SpineDangler.skeletonData.findAnimation("hurt");
        SpineDangler.animationStand = SpineDangler.skeletonData.findAnimation("stand");
        SpineDangler.skinDefault = SpineDangler.skeletonData.findSkin("default");
        SpineDragonQueen.skeletonData = assetManager.get("spine/dragon-queen.json");
        SpineDragonQueen.animationData = assetManager.get("spine/dragon-queen.json-animation");
        SpineDragonQueen.animationDie = SpineDragonQueen.skeletonData.findAnimation("die");
        SpineDragonQueen.animationFlap = SpineDragonQueen.skeletonData.findAnimation("flap");
        SpineDragonQueen.animationHurt = SpineDragonQueen.skeletonData.findAnimation("hurt");
        SpineDragonQueen.animationJump = SpineDragonQueen.skeletonData.findAnimation("jump");
        SpineDragonQueen.animationRun = SpineDragonQueen.skeletonData.findAnimation("run");
        SpineDragonQueen.animationShoot = SpineDragonQueen.skeletonData.findAnimation("shoot");
        SpineDragonQueen.animationShootDown = SpineDragonQueen.skeletonData.findAnimation("shoot-down");
        SpineDragonQueen.animationShootUp = SpineDragonQueen.skeletonData.findAnimation("shoot-up");
        SpineDragonQueen.animationShootJump = SpineDragonQueen.skeletonData.findAnimation("shootJump");
        SpineDragonQueen.animationStand = SpineDragonQueen.skeletonData.findAnimation("stand");
        SpineDragonQueen.animationThrow = SpineDragonQueen.skeletonData.findAnimation("throw");
        SpineDragonQueen.animationWhip = SpineDragonQueen.skeletonData.findAnimation("whip");
        SpineDragonQueen.animationWhipDown = SpineDragonQueen.skeletonData.findAnimation("whip-down");
        SpineDragonQueen.animationWhipJump = SpineDragonQueen.skeletonData.findAnimation("whip-jump");
        SpineDragonQueen.animationWhipUp = SpineDragonQueen.skeletonData.findAnimation("whip-up");
        SpineDragonQueen.skinDefault = SpineDragonQueen.skeletonData.findSkin("default");
        SpineExplosion.skeletonData = assetManager.get("spine/explosion.json");
        SpineExplosion.animationData = assetManager.get("spine/explosion.json-animation");
        SpineExplosion.animationAnimation = SpineExplosion.skeletonData.findAnimation("animation");
        SpineExplosion.skinDefault = SpineExplosion.skeletonData.findSkin("default");
        SpineFlame.skeletonData = assetManager.get("spine/flame.json");
        SpineFlame.animationData = assetManager.get("spine/flame.json-animation");
        SpineFlame.animationAnimation = SpineFlame.skeletonData.findAnimation("animation");
        SpineFlame.skinDefault = SpineFlame.skeletonData.findSkin("default");
        SpineFrozen.skeletonData = assetManager.get("spine/frozen.json");
        SpineFrozen.animationData = assetManager.get("spine/frozen.json-animation");
        SpineFrozen.animationDie = SpineFrozen.skeletonData.findAnimation("die");
        SpineFrozen.animationHurt = SpineFrozen.skeletonData.findAnimation("hurt");
        SpineFrozen.animationStand = SpineFrozen.skeletonData.findAnimation("stand");
        SpineFrozen.skinDefault = SpineFrozen.skeletonData.findSkin("default");
        SpineGluttonProjectile.skeletonData = assetManager.get("spine/glutton-projectile.json");
        SpineGluttonProjectile.animationData = assetManager.get("spine/glutton-projectile.json-animation");
        SpineGluttonProjectile.animationAnimation = SpineGluttonProjectile.skeletonData.findAnimation("animation");
        SpineGluttonProjectile.skinDefault = SpineGluttonProjectile.skeletonData.findSkin("default");
        SpineGlutton.skeletonData = assetManager.get("spine/glutton.json");
        SpineGlutton.animationData = assetManager.get("spine/glutton.json-animation");
        SpineGlutton.animationDie = SpineGlutton.skeletonData.findAnimation("die");
        SpineGlutton.animationHurt = SpineGlutton.skeletonData.findAnimation("hurt");
        SpineGlutton.animationWalk = SpineGlutton.skeletonData.findAnimation("walk");
        SpineGlutton.skinDefault = SpineGlutton.skeletonData.findSkin("default");
        SpineGrenadeFire.skeletonData = assetManager.get("spine/grenade-fire.json");
        SpineGrenadeFire.animationData = assetManager.get("spine/grenade-fire.json-animation");
        SpineGrenadeFire.animationAnimation = SpineGrenadeFire.skeletonData.findAnimation("animation");
        SpineGrenadeFire.skinDefault = SpineGrenadeFire.skeletonData.findSkin("default");
        SpineGrenade.skeletonData = assetManager.get("spine/grenade.json");
        SpineGrenade.animationData = assetManager.get("spine/grenade.json-animation");
        SpineGrenade.animationAnimation = SpineGrenade.skeletonData.findAnimation("animation");
        SpineGrenade.animationLaunch = SpineGrenade.skeletonData.findAnimation("launch");
        SpineGrenade.skinDefault = SpineGrenade.skeletonData.findSkin("default");
        SpineGroxar.skeletonData = assetManager.get("spine/groxar.json");
        SpineGroxar.animationData = assetManager.get("spine/groxar.json-animation");
        SpineGroxar.animationAttack = SpineGroxar.skeletonData.findAnimation("attack");
        SpineGroxar.animationDie = SpineGroxar.skeletonData.findAnimation("die");
        SpineGroxar.animationHurt = SpineGroxar.skeletonData.findAnimation("hurt");
        SpineGroxar.animationStand = SpineGroxar.skeletonData.findAnimation("stand");
        SpineGroxar.animationStatue = SpineGroxar.skeletonData.findAnimation("statue");
        SpineGroxar.animationStatueBreak = SpineGroxar.skeletonData.findAnimation("statue-break");
        SpineGroxar.skinDefault = SpineGroxar.skeletonData.findSkin("default");
        SpineHit.skeletonData = assetManager.get("spine/hit.json");
        SpineHit.animationData = assetManager.get("spine/hit.json-animation");
        SpineHit.animationAnimation = SpineHit.skeletonData.findAnimation("animation");
        SpineHit.skinDefault = SpineHit.skeletonData.findSkin("default");
        SpineJellyfish.skeletonData = assetManager.get("spine/jellyfish.json");
        SpineJellyfish.animationData = assetManager.get("spine/jellyfish.json-animation");
        SpineJellyfish.animationDie = SpineJellyfish.skeletonData.findAnimation("die");
        SpineJellyfish.animationHurt = SpineJellyfish.skeletonData.findAnimation("hurt");
        SpineJellyfish.animationMove = SpineJellyfish.skeletonData.findAnimation("move");
        SpineJellyfish.skinDefault = SpineJellyfish.skeletonData.findSkin("default");
        SpineJohn.skeletonData = assetManager.get("spine/john.json");
        SpineJohn.animationData = assetManager.get("spine/john.json-animation");
        SpineJohn.animationDie = SpineJohn.skeletonData.findAnimation("die");
        SpineJohn.animationHurt = SpineJohn.skeletonData.findAnimation("hurt");
        SpineJohn.animationStand = SpineJohn.skeletonData.findAnimation("stand");
        SpineJohn.skinDefault = SpineJohn.skeletonData.findSkin("default");
        SpineJumper.skeletonData = assetManager.get("spine/jumper.json");
        SpineJumper.animationData = assetManager.get("spine/jumper.json-animation");
        SpineJumper.animationDie = SpineJumper.skeletonData.findAnimation("die");
        SpineJumper.animationHurt = SpineJumper.skeletonData.findAnimation("hurt");
        SpineJumper.animationJump = SpineJumper.skeletonData.findAnimation("jump");
        SpineJumper.skinDefault = SpineJumper.skeletonData.findSkin("default");
        SpineLibgdx.skeletonData = assetManager.get("spine/libgdx.json");
        SpineLibgdx.animationData = assetManager.get("spine/libgdx.json-animation");
        SpineLibgdx.animationAnimation = SpineLibgdx.skeletonData.findAnimation("animation");
        SpineLibgdx.animationStand = SpineLibgdx.skeletonData.findAnimation("stand");
        SpineLibgdx.skinDefault = SpineLibgdx.skeletonData.findSkin("default");
        SpineLyzeBeam.skeletonData = assetManager.get("spine/lyze-beam.json");
        SpineLyzeBeam.animationData = assetManager.get("spine/lyze-beam.json-animation");
        SpineLyzeBeam.animationAnimation = SpineLyzeBeam.skeletonData.findAnimation("animation");
        SpineLyzeBeam.animationPrep = SpineLyzeBeam.skeletonData.findAnimation("prep");
        SpineLyzeBeam.skinDefault = SpineLyzeBeam.skeletonData.findSkin("default");
        SpineLyze.skeletonData = assetManager.get("spine/lyze.json");
        SpineLyze.animationData = assetManager.get("spine/lyze.json-animation");
        SpineLyze.animationDie = SpineLyze.skeletonData.findAnimation("die");
        SpineLyze.animationHurt = SpineLyze.skeletonData.findAnimation("hurt");
        SpineLyze.animationJump = SpineLyze.skeletonData.findAnimation("jump");
        SpineLyze.animationLand = SpineLyze.skeletonData.findAnimation("land");
        SpineLyze.animationShoot = SpineLyze.skeletonData.findAnimation("shoot");
        SpineLyze.skinDefault = SpineLyze.skeletonData.findSkin("default");
        SpineMoth.skeletonData = assetManager.get("spine/moth.json");
        SpineMoth.animationData = assetManager.get("spine/moth.json-animation");
        SpineMoth.animationAnimation = SpineMoth.skeletonData.findAnimation("animation");
        SpineMoth.animationDie = SpineMoth.skeletonData.findAnimation("die");
        SpineMoth.animationHurt = SpineMoth.skeletonData.findAnimation("hurt");
        SpineMoth.skinDefault = SpineMoth.skeletonData.findSkin("default");
        SpinePandaChute.skeletonData = assetManager.get("spine/panda-chute.json");
        SpinePandaChute.animationData = assetManager.get("spine/panda-chute.json-animation");
        SpinePandaChute.animationAnimation = SpinePandaChute.skeletonData.findAnimation("animation");
        SpinePandaChute.skinDefault = SpinePandaChute.skeletonData.findSkin("default");
        SpinePanda.skeletonData = assetManager.get("spine/panda.json");
        SpinePanda.animationData = assetManager.get("spine/panda.json-animation");
        SpinePanda.animationDie = SpinePanda.skeletonData.findAnimation("die");
        SpinePanda.animationHurt = SpinePanda.skeletonData.findAnimation("hurt");
        SpinePanda.animationPosition1 = SpinePanda.skeletonData.findAnimation("position1");
        SpinePanda.animationPosition2 = SpinePanda.skeletonData.findAnimation("position2");
        SpinePanda.animationPosition3 = SpinePanda.skeletonData.findAnimation("position3");
        SpinePanda.animationPosition4 = SpinePanda.skeletonData.findAnimation("position4");
        SpinePanda.animationPosition5 = SpinePanda.skeletonData.findAnimation("position5");
        SpinePanda.animationPosition6 = SpinePanda.skeletonData.findAnimation("position6");
        SpinePanda.animationStand = SpinePanda.skeletonData.findAnimation("stand");
        SpinePanda.animationThrow = SpinePanda.skeletonData.findAnimation("throw");
        SpinePanda.skinDefault = SpinePanda.skeletonData.findSkin("default");
        SpinePirate.skeletonData = assetManager.get("spine/pirate.json");
        SpinePirate.animationData = assetManager.get("spine/pirate.json-animation");
        SpinePirate.animationAttack = SpinePirate.skeletonData.findAnimation("attack");
        SpinePirate.animationDie = SpinePirate.skeletonData.findAnimation("die");
        SpinePirate.animationHurt = SpinePirate.skeletonData.findAnimation("hurt");
        SpinePirate.animationRun = SpinePirate.skeletonData.findAnimation("run");
        SpinePirate.skinDefault = SpinePirate.skeletonData.findSkin("default");
        SpinePodBullet.skeletonData = assetManager.get("spine/pod-bullet.json");
        SpinePodBullet.animationData = assetManager.get("spine/pod-bullet.json-animation");
        SpinePodBullet.animationAnimation = SpinePodBullet.skeletonData.findAnimation("animation");
        SpinePodBullet.skinDefault = SpinePodBullet.skeletonData.findSkin("default");
        SpinePod.skeletonData = assetManager.get("spine/pod.json");
        SpinePod.animationData = assetManager.get("spine/pod.json-animation");
        SpinePod.animationAnimation = SpinePod.skeletonData.findAnimation("animation");
        SpinePod.skinDefault = SpinePod.skeletonData.findSkin("default");
        SpinePowerup.skeletonData = assetManager.get("spine/powerup.json");
        SpinePowerup.animationData = assetManager.get("spine/powerup.json-animation");
        SpinePowerup.animationCross = SpinePowerup.skeletonData.findAnimation("cross");
        SpinePowerup.animationGrenade = SpinePowerup.skeletonData.findAnimation("grenade");
        SpinePowerup.animationShotgun = SpinePowerup.skeletonData.findAnimation("shotgun");
        SpinePowerup.animationWhip = SpinePowerup.skeletonData.findAnimation("whip");
        SpinePowerup.animationWings = SpinePowerup.skeletonData.findAnimation("wings");
        SpinePowerup.skinDefault = SpinePowerup.skeletonData.findSkin("default");
        SpineRay3k.skeletonData = assetManager.get("spine/ray3k.json");
        SpineRay3k.animationData = assetManager.get("spine/ray3k.json-animation");
        SpineRay3k.animationAnimation = SpineRay3k.skeletonData.findAnimation("animation");
        SpineRay3k.animationStand = SpineRay3k.skeletonData.findAnimation("stand");
        SpineRay3k.skinDefault = SpineRay3k.skeletonData.findSkin("default");
        SpineRobot.skeletonData = assetManager.get("spine/robot.json");
        SpineRobot.animationData = assetManager.get("spine/robot.json-animation");
        SpineRobot.animationDie = SpineRobot.skeletonData.findAnimation("die");
        SpineRobot.animationHurt = SpineRobot.skeletonData.findAnimation("hurt");
        SpineRobot.animationWalk = SpineRobot.skeletonData.findAnimation("walk");
        SpineRobot.skinDefault = SpineRobot.skeletonData.findSkin("default");
        SpineShooter.skeletonData = assetManager.get("spine/shooter.json");
        SpineShooter.animationData = assetManager.get("spine/shooter.json-animation");
        SpineShooter.animationDie = SpineShooter.skeletonData.findAnimation("die");
        SpineShooter.animationHurt = SpineShooter.skeletonData.findAnimation("hurt");
        SpineShooter.skinDefault = SpineShooter.skeletonData.findSkin("default");
        SpineShotgunBullet.skeletonData = assetManager.get("spine/shotgun-bullet.json");
        SpineShotgunBullet.animationData = assetManager.get("spine/shotgun-bullet.json-animation");
        SpineShotgunBullet.animationAnimation = SpineShotgunBullet.skeletonData.findAnimation("animation");
        SpineShotgunBullet.skinDefault = SpineShotgunBullet.skeletonData.findSkin("default");
        SpineSkeleton.skeletonData = assetManager.get("spine/skeleton.json");
        SpineSkeleton.animationData = assetManager.get("spine/skeleton.json-animation");
        SpineSkeleton.animationAttack = SpineSkeleton.skeletonData.findAnimation("attack");
        SpineSkeleton.animationDie = SpineSkeleton.skeletonData.findAnimation("die");
        SpineSkeleton.animationHurt = SpineSkeleton.skeletonData.findAnimation("hurt");
        SpineSkeleton.animationWalk = SpineSkeleton.skeletonData.findAnimation("walk");
        SpineSkeleton.skinDefault = SpineSkeleton.skeletonData.findSkin("default");
        SpineSlimeDog.skeletonData = assetManager.get("spine/slime-dog.json");
        SpineSlimeDog.animationData = assetManager.get("spine/slime-dog.json-animation");
        SpineSlimeDog.animationAttack = SpineSlimeDog.skeletonData.findAnimation("attack");
        SpineSlimeDog.animationDie = SpineSlimeDog.skeletonData.findAnimation("die");
        SpineSlimeDog.animationHurt = SpineSlimeDog.skeletonData.findAnimation("hurt");
        SpineSlimeDog.animationStand = SpineSlimeDog.skeletonData.findAnimation("stand");
        SpineSlimeDog.skinDefault = SpineSlimeDog.skeletonData.findSkin("default");
        SpineSpark.skeletonData = assetManager.get("spine/spark.json");
        SpineSpark.animationData = assetManager.get("spine/spark.json-animation");
        SpineSpark.animationAnimation = SpineSpark.skeletonData.findAnimation("animation");
        SpineSpark.skinDefault = SpineSpark.skeletonData.findSkin("default");
        SpineSpinner.skeletonData = assetManager.get("spine/spinner.json");
        SpineSpinner.animationData = assetManager.get("spine/spinner.json-animation");
        SpineSpinner.animationFall = SpineSpinner.skeletonData.findAnimation("fall");
        SpineSpinner.animationHurt = SpineSpinner.skeletonData.findAnimation("hurt");
        SpineSpinner.skinDefault = SpineSpinner.skeletonData.findSkin("default");
        SpineStahlelge.skeletonData = assetManager.get("spine/stahlelge.json");
        SpineStahlelge.animationData = assetManager.get("spine/stahlelge.json-animation");
        SpineStahlelge.animationStand = SpineStahlelge.skeletonData.findAnimation("stand");
        SpineStahlelge.skinDefault = SpineStahlelge.skeletonData.findSkin("default");
        SpineTank.skeletonData = assetManager.get("spine/tank.json");
        SpineTank.animationData = assetManager.get("spine/tank.json-animation");
        SpineTank.animationAttack = SpineTank.skeletonData.findAnimation("attack");
        SpineTank.animationHurt = SpineTank.skeletonData.findAnimation("hurt");
        SpineTank.animationKill = SpineTank.skeletonData.findAnimation("kill");
        SpineTank.animationStand = SpineTank.skeletonData.findAnimation("stand");
        SpineTank.skinDefault = SpineTank.skeletonData.findSkin("default");
        SpineToasterToast.skeletonData = assetManager.get("spine/toaster-toast.json");
        SpineToasterToast.animationData = assetManager.get("spine/toaster-toast.json-animation");
        SpineToasterToast.animationAnimation = SpineToasterToast.skeletonData.findAnimation("animation");
        SpineToasterToast.skinDefault = SpineToasterToast.skeletonData.findSkin("default");
        SpineToaster.skeletonData = assetManager.get("spine/toaster.json");
        SpineToaster.animationData = assetManager.get("spine/toaster.json-animation");
        SpineToaster.animationDie = SpineToaster.skeletonData.findAnimation("die");
        SpineToaster.animationHurt = SpineToaster.skeletonData.findAnimation("hurt");
        SpineToaster.animationShoot = SpineToaster.skeletonData.findAnimation("shoot");
        SpineToaster.skinDefault = SpineToaster.skeletonData.findSkin("default");
        SpineVacuum.skeletonData = assetManager.get("spine/vacuum.json");
        SpineVacuum.animationData = assetManager.get("spine/vacuum.json-animation");
        SpineVacuum.animationDie = SpineVacuum.skeletonData.findAnimation("die");
        SpineVacuum.animationHurt = SpineVacuum.skeletonData.findAnimation("hurt");
        SpineVacuum.skinDefault = SpineVacuum.skeletonData.findSkin("default");
        SpineWasher.skeletonData = assetManager.get("spine/washer.json");
        SpineWasher.animationData = assetManager.get("spine/washer.json-animation");
        SpineWasher.animationDie = SpineWasher.skeletonData.findAnimation("die");
        SpineWasher.animationHurt = SpineWasher.skeletonData.findAnimation("hurt");
        SpineWasher.animationStand = SpineWasher.skeletonData.findAnimation("stand");
        SpineWasher.skinDefault = SpineWasher.skeletonData.findSkin("default");
        SpineWolf.skeletonData = assetManager.get("spine/wolf.json");
        SpineWolf.animationData = assetManager.get("spine/wolf.json-animation");
        SpineWolf.animationDie = SpineWolf.skeletonData.findAnimation("die");
        SpineWolf.animationHurt = SpineWolf.skeletonData.findAnimation("hurt");
        SpineWolf.animationRun = SpineWolf.skeletonData.findAnimation("run");
        SpineWolf.skinDefault = SpineWolf.skeletonData.findSkin("default");
        SpineZebraFly.skeletonData = assetManager.get("spine/zebra-fly.json");
        SpineZebraFly.animationData = assetManager.get("spine/zebra-fly.json-animation");
        SpineZebraFly.animationAnimation = SpineZebraFly.skeletonData.findAnimation("animation");
        SpineZebraFly.skinDefault = SpineZebraFly.skeletonData.findSkin("default");
        SpineZebra.skeletonData = assetManager.get("spine/zebra.json");
        SpineZebra.animationData = assetManager.get("spine/zebra.json-animation");
        SpineZebra.animationDie = SpineZebra.skeletonData.findAnimation("die");
        SpineZebra.animationFly = SpineZebra.skeletonData.findAnimation("fly");
        SpineZebra.animationHurt = SpineZebra.skeletonData.findAnimation("hurt");
        SpineZebra.animationShoot = SpineZebra.skeletonData.findAnimation("shoot");
        SpineZebra.animationStand = SpineZebra.skeletonData.findAnimation("stand");
        SpineZebra.animationStrike = SpineZebra.skeletonData.findAnimation("strike");
        SpineZebra.skinDefault = SpineZebra.skeletonData.findSkin("default");
        skin_skin = assetManager.get("skin/skin.json");
        SkinSkinStyles.cbDefault = skin_skin.get("default", CheckBox.CheckBoxStyle.class);
        SkinSkinStyles.lLarge = skin_skin.get("large", Label.LabelStyle.class);
        SkinSkinStyles.lDefault = skin_skin.get("default", Label.LabelStyle.class);
        SkinSkinStyles.lScript = skin_skin.get("script", Label.LabelStyle.class);
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
        sfx_click = assetManager.get("sfx/click.mp3");
        sfx_crack = assetManager.get("sfx/crack.mp3");
        sfx_good = assetManager.get("sfx/good.mp3");
        sfx_grinder = assetManager.get("sfx/grinder.mp3");
        sfx_intro = assetManager.get("sfx/intro.mp3");
        sfx_poot = assetManager.get("sfx/poot.mp3");
        bgm_audioSample = assetManager.get("bgm/audio-sample.mp3");
        bgm_menu = assetManager.get("bgm/menu.mp3");
    }

    public static class SpineBat {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationFly;

        public static Animation animationHurt;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBlob {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineClock {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAttack;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationStand;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineCross {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineDangler {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationStand;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineDragonQueen {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationFlap;

        public static Animation animationHurt;

        public static Animation animationJump;

        public static Animation animationRun;

        public static Animation animationShoot;

        public static Animation animationShootDown;

        public static Animation animationShootUp;

        public static Animation animationShootJump;

        public static Animation animationStand;

        public static Animation animationThrow;

        public static Animation animationWhip;

        public static Animation animationWhipDown;

        public static Animation animationWhipJump;

        public static Animation animationWhipUp;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineExplosion {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineFlame {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineFrozen {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationStand;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineGluttonProjectile {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineGlutton {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationWalk;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineGrenadeFire {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineGrenade {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationLaunch;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineGroxar {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAttack;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationStand;

        public static Animation animationStatue;

        public static Animation animationStatueBreak;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineHit {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineJellyfish {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationMove;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineJohn {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationStand;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineJumper {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationJump;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineLibgdx {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationStand;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineLyzeBeam {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationPrep;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineLyze {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationJump;

        public static Animation animationLand;

        public static Animation animationShoot;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineMoth {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpinePandaChute {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpinePanda {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationPosition1;

        public static Animation animationPosition2;

        public static Animation animationPosition3;

        public static Animation animationPosition4;

        public static Animation animationPosition5;

        public static Animation animationPosition6;

        public static Animation animationStand;

        public static Animation animationThrow;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpinePirate {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAttack;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationRun;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpinePodBullet {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpinePod {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpinePowerup {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationCross;

        public static Animation animationGrenade;

        public static Animation animationShotgun;

        public static Animation animationWhip;

        public static Animation animationWings;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineRay3k {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationStand;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineRobot {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationWalk;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShooter {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShotgunBullet {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSkeleton {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAttack;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationWalk;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSlimeDog {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAttack;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationStand;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSpark {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSpinner {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationFall;

        public static Animation animationHurt;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStahlelge {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationStand;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineTank {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAttack;

        public static Animation animationHurt;

        public static Animation animationKill;

        public static Animation animationStand;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineToasterToast {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineToaster {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationShoot;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineVacuum {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineWasher {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationStand;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineWolf {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationHurt;

        public static Animation animationRun;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineZebraFly {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineZebra {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationDie;

        public static Animation animationFly;

        public static Animation animationHurt;

        public static Animation animationShoot;

        public static Animation animationStand;

        public static Animation animationStrike;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SkinSkinStyles {
        public static CheckBox.CheckBoxStyle cbDefault;

        public static Label.LabelStyle lLarge;

        public static Label.LabelStyle lDefault;

        public static Label.LabelStyle lScript;

        public static ScrollPane.ScrollPaneStyle spDefault;

        public static Slider.SliderStyle sDefaultHorizontal;

        public static TextButton.TextButtonStyle tbToggle;

        public static TextButton.TextButtonStyle tbDefault;

        public static TextField.TextFieldStyle tfDefault;

        public static TextTooltip.TextTooltipStyle ttDefault;

        public static Window.WindowStyle wDefault;
    }

    public static class Values {
        public static int playerMaxHealth = 100;

        public static float playerMaxSpeed = 700.0f;

        public static float playerDamageTimer = 1.0f;

        public static int playerAcceleration = 2600;

        public static int playerAccelerationWhileJumping = 1500;

        public static int playerDeceleration = 2400;

        public static int playerDecelerationWhileJumping = 800;

        public static int playerGravity = -3000;

        public static int playerJumpSpeed = 700;

        public static float playerJumpHoldTime = 0.4f;

        public static int playerMaxJumps = 3;

        public static int whipDamage = 10;

        public static int whipForce = 1000;

        public static int whipForceDirection = 60;

        public static int crossLaunchSpeed = 1500;

        public static int crossAcceleration = 2000;

        public static float crossRotationMultiplier = 0.5f;

        public static int crossDamage = 10;

        public static int crossForce = 2000;

        public static int crossForceDirection = 20;

        public static float crossHitDelay = 1.0f;

        public static float grenadeThrowAngle = 70.0f;

        public static float grenadeThrowSpeed = 1200.0f;

        public static float grenadeGravity = -4000.0f;

        public static int grenadeDamagePerSecond = 10;

        public static float shotgunProjectileSpeed = 3000.0f;

        public static float shotgunChargeAngle = 25.0f;

        public static float shotgunChargeTimescale = 0.5f;

        public static int shotgunDamage = 10;

        public static int shotgunForce = 1000;

        public static int shotgunForceDirection = 85;

        public static float explosionDelay = 0.1f;

        public static int explosionCount = 2;

        public static float explosionScaleMin = 0.2f;

        public static float explosionScaleMax = 0.7f;

        public static float whiteBackgroundFade = 0.6f;

        public static int blobGravity = -3000;

        public static int blobMoveSpeed = 400;

        public static int blobAcceleration = 500;

        public static int blobHealth = 40;

        public static int blobDamage = 10;

        public static int blobForce = 1000;

        public static int blobForceDirection = 60;

        public static boolean debugging = false;
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
