package GameObjects;

import Core.InputManager;
import Models.Axis;
import com.jogamp.newt.event.KeyEvent;

public class ControllableObject extends GameObject {

    private GameObject controlledObject;
    private float speed = 0.1f;

    public ControllableObject(GameObject controlledObject) {
        this.controlledObject = controlledObject;
        initControl();
    }

    void initControl() {
        InputManager.RegisterBinding(KeyEvent.VK_LEFT, () -> controlledObject.Move(speed, 0, 0));
        InputManager.RegisterBinding(KeyEvent.VK_RIGHT, () -> controlledObject.Move(-speed, 0, 0));
        InputManager.RegisterBinding(KeyEvent.VK_UP, () -> controlledObject.Move(0, speed, 0));
        InputManager.RegisterBinding(KeyEvent.VK_DOWN, () -> controlledObject.Move(0, -speed, 0));
        InputManager.RegisterBinding(KeyEvent.VK_SPACE, () -> controlledObject.Move(0, 0, speed));
        InputManager.RegisterBinding(KeyEvent.VK_SHIFT, () -> controlledObject.Move(0, 0, -speed));

        InputManager.RegisterBinding(KeyEvent.VK_Q, () -> controlledObject.Rotate(Axis.X, speed * 10));
        InputManager.RegisterBinding(KeyEvent.VK_W, () -> controlledObject.Rotate(Axis.X, -speed * 10));
        InputManager.RegisterBinding(KeyEvent.VK_A, () -> controlledObject.Rotate(Axis.Y, speed * 10));
        InputManager.RegisterBinding(KeyEvent.VK_S, () -> controlledObject.Rotate(Axis.Y, -speed * 10));
        InputManager.RegisterBinding(KeyEvent.VK_Z, () -> controlledObject.Rotate(Axis.Z, speed * 10));
        InputManager.RegisterBinding(KeyEvent.VK_X, () -> controlledObject.Rotate(Axis.Z, -speed * 10));

        InputManager.RegisterBinding(KeyEvent.VK_C, () -> System.out.println(controlledObject));
    }

    @Override
    public void InitGameObject() {
        controlledObject.InitGameObject();
    }

    @Override
    public void draw() {
        controlledObject.draw();
    }


}
