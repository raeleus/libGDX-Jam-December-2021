package com.ray3k.template;

import java.util.HashMap;

public class MobileControls {
    private final HashMap<Core.Binding, Boolean> input = new HashMap<>();
    private final HashMap<Core.Binding, Boolean> inputJustPressed = new HashMap<>();

    public boolean isPressed(Core.Binding binding) {
        return input.containsKey(binding) ? input.get(binding) : Boolean.valueOf(false);
    }

    public boolean isJustPressed(Core.Binding binding) {
        if (inputJustPressed.containsKey(binding)) {
            if (inputJustPressed.get(binding)) {
                inputJustPressed.put(binding, Boolean.FALSE);
                return true;
            }
        }

        return false;
    }

    public void setPressed(Core.Binding binding, boolean pressed) {
        input.put(binding, pressed);
        setJustPressedOnly(binding, pressed);
    }

    public void setJustPressedOnly(Core.Binding binding, boolean pressed) {
        inputJustPressed.put(binding, pressed);
    }
}
