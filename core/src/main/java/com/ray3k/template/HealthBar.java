package com.ray3k.template;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.spine.utils.TwoColorPolygonBatch;

import static com.ray3k.template.Core.shapeDrawer;
import static com.ray3k.template.Resources.Values.playerHealth;

public class HealthBar {
    private static boolean healthChanged;
    private static float targetHealthBarValue, initialHealthBarValue;
    private static float currentHealthBarValue = playerHealth;
    private static float healthBarChangedTime;

    public void act(float delta) {
        if (!healthChanged && playerHealth != targetHealthBarValue) {
            healthBarChangedTime = 0;
            healthChanged = true;
            initialHealthBarValue = currentHealthBarValue;
            targetHealthBarValue = playerHealth;
        }

        healthBarChangedTime += delta;

        currentHealthBarValue = Interpolation.exp10Out.apply(initialHealthBarValue, targetHealthBarValue, MathUtils.clamp(healthBarChangedTime * 2f, 0, 1));
    }

    public void draw(Viewport viewport, TwoColorPolygonBatch batch) {
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        shapeDrawer.filledRectangle(24, viewport.getWorldHeight() - 32 - 24, 200, 32, Color.DARK_GRAY);
        shapeDrawer.filledRectangle(24, viewport.getWorldHeight() - 32 - 24, currentHealthBarValue * 2f, 32, healthChanged ? Color.WHITE : currentHealthBarValue < 15 ? Color.RED : currentHealthBarValue < 50 ? Color.YELLOW : Color.GREEN);
        shapeDrawer.rectangle(24, viewport.getWorldHeight() - 32 - 24, 200, 32, Color.WHITE);
        batch.end();
        healthChanged = false;
    }
}
