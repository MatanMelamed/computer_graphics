// Matan Melamed 205973613
package com.matan.studies.computergraphics.GameObjects.Prefabs;

import com.matan.studies.computergraphics.Core.GameManager;
import com.matan.studies.computergraphics.Core.InputManager;
import com.matan.studies.computergraphics.GameObjects.Components.LightComponent;
import com.matan.studies.computergraphics.GameObjects.GameObject;
import com.jogamp.newt.event.KeyEvent;

import java.util.Arrays;

public class LightController extends GameObject {

    private LightComponent c;

    public LightController(LightComponent c) {
        super("light controller");
        this.c = c;

        InputManager.RegisterBinding(KeyEvent.VK_1, () -> c.spotAngle += !InputManager.isPressed(KeyEvent.VK_ALT) ? 1f : -1f);
        InputManager.RegisterBinding(KeyEvent.VK_2, () -> c.spotExponent += !InputManager.isPressed(KeyEvent.VK_ALT) ? 1f : -1f);
        InputManager.RegisterBinding(KeyEvent.VK_3, () -> c.constantAttenuation += !InputManager.isPressed(KeyEvent.VK_ALT) ? 1f : -1f);
        InputManager.RegisterBinding(KeyEvent.VK_4, () -> c.ambient[0] += !InputManager.isPressed(KeyEvent.VK_ALT) ? 0.1f : -0.1f);
        InputManager.RegisterBinding(KeyEvent.VK_5, () -> c.ambient[1] += !InputManager.isPressed(KeyEvent.VK_ALT) ? 0.1f : -0.1f);
        InputManager.RegisterBinding(KeyEvent.VK_6, () -> c.ambient[2] += !InputManager.isPressed(KeyEvent.VK_ALT) ? 0.1f : -0.1f);
        InputManager.RegisterBinding(KeyEvent.VK_7, () -> c.diffuse[0] += !InputManager.isPressed(KeyEvent.VK_ALT) ? 0.1f : -0.1f);
        InputManager.RegisterBinding(KeyEvent.VK_8, () -> c.diffuse[1] += !InputManager.isPressed(KeyEvent.VK_ALT) ? 0.1f : -0.1f);
        InputManager.RegisterBinding(KeyEvent.VK_9, () -> c.diffuse[2] += !InputManager.isPressed(KeyEvent.VK_ALT) ? 0.1f : -0.1f);
        InputManager.RegisterBinding(KeyEvent.VK_X, () -> c.direction = !InputManager.isPressed(KeyEvent.VK_ALT) ? c.direction.plus(0.1f, 0, 0) : c.direction.plus(-0.1f, 0, 0));
        InputManager.RegisterBinding(KeyEvent.VK_Z, () -> c.direction = !InputManager.isPressed(KeyEvent.VK_ALT) ? c.direction.plus(0, 0, 0.1f) : c.direction.plus(0, 0, -0.1f));
        InputManager.RegisterBinding(KeyEvent.VK_Y, () -> c.direction = !InputManager.isPressed(KeyEvent.VK_ALT) ? c.direction.plus(0, 0.1f, 0) : c.direction.plus(0, -0.1f, 0));
        InputManager.RegisterBinding(KeyEvent.VK_P, () -> {
            var p = GameManager.GetPlayer().GetPosition();
            c.position[0] = p.x;
            c.position[1] = p.y;
            c.position[2] = p.z;
        });
        InputManager.RegisterBinding(KeyEvent.VK_I, () -> {
            System.out.println(String.format("position %s, expo: %.2f, angle: %.2f,cons: %.2f, direction: %s", Arrays.toString(c.position), c.spotExponent, c.spotAngle, c.constantAttenuation, c.direction));
            System.out.println(String.format("ambient %s, diffuse: %s", Arrays.toString(c.ambient), Arrays.toString(c.diffuse)));
        });
        AddComponent(c);
    }
}
