package Tests;

import Core.InputManager;
import GameObjects.BoxObject;
import GameObjects.GameObject;
import Graphics.Debugger;
import Models.Axis;
import World.World1;
import com.jogamp.newt.event.KeyEvent;

public class ObjectController {


    public static GameObject pl = new BoxObject("wood_box.jpg", 1, 1,1);

    public static void move(int forward, int side, int height) {
        pl.Move(side, height, forward);
    }

    public static void rot(Axis axis, double angle) {
        pl.Rotate(axis, angle);
    }

    public static String print() {
        return String.format("Plate :: %s ", pl);
    }


    public static void init(){
        InputManager.RegisterBinding(KeyEvent.VK_UP, () -> move(1, 0, 0));
        InputManager.RegisterBinding(KeyEvent.VK_DOWN, () -> move(-1, 0, 0));
        InputManager.RegisterBinding(KeyEvent.VK_LEFT, () -> move(0, 1, 0));
        InputManager.RegisterBinding(KeyEvent.VK_RIGHT, () -> move(0, -1, 0));
        InputManager.RegisterBinding(KeyEvent.VK_SPACE, () -> move(0, 0, 1));
        InputManager.RegisterBinding(KeyEvent.VK_SHIFT, () -> move(0, 0, -1));

        InputManager.RegisterBinding(KeyEvent.VK_Q, () -> rot(Axis.X, 10));
        InputManager.RegisterBinding(KeyEvent.VK_W, () -> rot(Axis.X, -10));
        InputManager.RegisterBinding(KeyEvent.VK_A, () -> rot(Axis.Y, 10));
        InputManager.RegisterBinding(KeyEvent.VK_S, () -> rot(Axis.Y, -10));
        InputManager.RegisterBinding(KeyEvent.VK_Z, () -> rot(Axis.Z, 10));
        InputManager.RegisterBinding(KeyEvent.VK_X, () -> rot(Axis.Z, -10));


        Debugger.AddDebug(ObjectController::print);
    }

}
