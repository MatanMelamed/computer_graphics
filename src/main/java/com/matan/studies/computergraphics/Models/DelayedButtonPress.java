// Matan Melamed 205973613
package com.matan.studies.computergraphics.Models;

import com.matan.studies.computergraphics.Core.InputManager;

public class DelayedButtonPress {

    private float buttonCooldown;
    private float currentCooldown;

    private short key;
    private Runnable onPressCallback;

    public DelayedButtonPress(float buttonCooldown, short key, Runnable onPressCallback) {
        this.buttonCooldown = buttonCooldown;
        this.key = key;
        this.onPressCallback = onPressCallback;
        InputManager.RegisterBinding(key, this::ButtonPressed);
    }

    private void ButtonPressed() {
        if (currentCooldown > 0) return;

        currentCooldown = buttonCooldown;
        onPressCallback.run();
    }

    public void Update(float deltaTime) {
        if (buttonCooldown > 0) {
            buttonCooldown -= (deltaTime / 1000f);
        }
    }
}
