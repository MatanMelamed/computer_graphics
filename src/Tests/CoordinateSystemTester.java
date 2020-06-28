package Tests;

import Core.GameLoop;
import Core.InputManager;
import Graphics.WindowManager;
import Models.Axis;
import Models.CoordinateSystem;
import com.jogamp.newt.event.KeyEvent;

public class CoordinateSystemTester {

    public static void run() {
        WindowManager.Initialize("MyGame", 1366, 768);
        InputManager.Initialize();

        GameLoop gameLoop = GameLoop.GetInstance();
        gameLoop.Start();
    }

    public static void upa(Integer a, int val) { a += val;}

    public static void main(String[] args) {

        run();

        CoordinateSystem cs = new CoordinateSystem();

        Integer x = 0;
        Integer y = 0;
        Integer z = 0;
        InputManager.RegisterBinding(KeyEvent.VK_P, () -> System.out.println(cs));
        InputManager.RegisterBinding(KeyEvent.VK_Z, () -> {cs.rotate(Axis.Z, 10); upa(z, 10);});
        InputManager.RegisterBinding(KeyEvent.VK_A, () -> {cs.rotate(Axis.Z, -10); upa(z, -10);});
        InputManager.RegisterBinding(KeyEvent.VK_X, () -> {cs.rotate(Axis.X, 10); upa(x, 10);});
        InputManager.RegisterBinding(KeyEvent.VK_S, () -> {cs.rotate(Axis.X, -10); upa(x, -10);});
        InputManager.RegisterBinding(KeyEvent.VK_Y, () -> {cs.rotate(Axis.Y, 10); upa(y, 10);});
        InputManager.RegisterBinding(KeyEvent.VK_U, () -> {cs.rotate(Axis.Y, -10); upa(y, -10);});
        InputManager.RegisterBinding(KeyEvent.VK_I, () -> System.out.println(String.format("rotation:: x:%d, y:%d, z:%d", x, y, z)));
        InputManager.RegisterBinding(KeyEvent.VK_C, () -> {
            double[] res = cs.AxisAnglesFromWorld();
            System.out.println(String.format("res:: x:%.4f, y:%.4f, z:%.4f", res[0], res[1], res[2]));
        });

    }
}
