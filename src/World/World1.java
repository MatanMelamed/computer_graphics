package World;

import Core.InputManager;
import GameObjects.BoxObject;
import GameObjects.GameObject;
import GameObjects.PlateObject;
import GameObjects.PlayerObject;
import Graphics.GraphicsEventListener;
import Models.Axis;
import Models.Vector3D;
import com.jogamp.newt.event.KeyEvent;


public class World1 extends BaseWorld {

    public static GameObject pl = new BoxObject("wood_box.jpg", 1, 1,1);

    public static void move(int forward, int side, int height) {
        pl.cs.move(new Vector3D(side, height, forward));
    }

    public static void rot(Axis axis, double angle) {
        pl.Rotate(axis, angle);
    }

    public static String print() {
        return String.format("Plate :: %s ", pl.cs);
    }

    public World1() {
        BoxObject box;

        PlateObject plate = new PlateObject("wood_box.jpg", 16, 16);
        plate.SetPosition(0, -5, 0);
        AddGameObject(plate);

        AddGameObject(pl);
//        plate = new PlateObject("wood_box.jpg", 16, 16);
//        plate.SetPosition(8, 6, 0);
//        plate.Rotate(Axis.Z, 90);
//        AddGameObject(plate);

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


        GraphicsEventListener.AddDebugMessageGenerator(World1::print);


        box = new BoxObject("wood_box.jpg", 1, 1, 1);
        box.SetPosition(8, 6, 0);
        AddGameObject(box);
    }

}
