// Matan Melamed 205973613
package com.matan.studies.computergraphics.GameObjects.Prefabs;

import com.matan.studies.computergraphics.Core.InputManager;
import com.matan.studies.computergraphics.GameObjects.GameObject;
import com.jogamp.newt.event.KeyEvent;

public class ControllableObject extends GameObject {

    private GameObject controlledObject;
    private float speed = 0.1f;

    public ControllableObject(GameObject controlledObject) {
        this.controlledObject = controlledObject;
        AddChild(controlledObject);
        initControl();
    }

    private void initControl() {
        InputManager.RegisterBinding(KeyEvent.VK_LEFT, () -> controlledObject.Move(speed, 0, 0));
        InputManager.RegisterBinding(KeyEvent.VK_RIGHT, () -> controlledObject.Move(-speed, 0, 0));
        InputManager.RegisterBinding(KeyEvent.VK_UP, () -> controlledObject.Move(0, 0, speed));
        InputManager.RegisterBinding(KeyEvent.VK_DOWN, () -> controlledObject.Move(0, 0, -speed));
        InputManager.RegisterBinding(KeyEvent.VK_M, () -> controlledObject.Move(0, speed, 0));
        InputManager.RegisterBinding(KeyEvent.VK_N, () -> controlledObject.Move(0, -speed, 0));

        InputManager.RegisterBinding(KeyEvent.VK_C, () -> {
            System.out.println(String.format("%s", controlledObject.GetPosition()));
        });
    }

    @Override
    public void InitializeAll() {
        controlledObject.InitializeAll();
    }

    @Override
    public void RenderAll() {
        controlledObject.RenderAll();
    }
}
