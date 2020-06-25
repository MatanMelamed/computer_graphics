import Core.GameLoop;
import Core.InputManager;
import Graphics.GraphicsEventListener;
import Graphics.Renderer;
import Models.Axis;
import Models.CoordinateSystem;
import Models.Vector3D;
import World.World1;
import World.WorldManager;
import com.jogamp.newt.event.KeyEvent;


public class Main {

    public static double lastAngle = 0;

    public static void PlayerResetX() {

        if (lastAngle != 0) {
            WorldManager.Player.Rotate(Axis.X, -lastAngle);
            lastAngle = 0;
        } else {
            lastAngle = WorldManager.Player.getCoordinateSystem().GetXDiv();
            var cs = WorldManager.Player.getCoordinateSystem();
            cs.DirX.Set(new Vector3D(cs.DirX.x, 0, cs.DirZ.z).normalize());
            cs.DirY.Set(CoordinateSystem.WORLD_Y);
            cs.DirZ.Set(new Vector3D(cs.DirZ.x, 0, cs.DirZ.z).normalize());
        }
        System.out.println(lastAngle);
    }

    private static void run() {
        Renderer.Initialize("MyGame", 1366, 768);
        InputManager.Initialize();

        WorldManager.SetWorld(new World1());

        GraphicsEventListener.AddDebugMessageGenerator(() -> {
            var cs = WorldManager.Player.getCoordinateSystem().AxisAnglesFromWorld();
            return String.format("x: %.4f, y: %.4f, z: %.4f", cs[0], cs[1], cs[2]);
        });

        GraphicsEventListener.AddDebugMessageGenerator(() -> {
            var cs = WorldManager.Player.getCoordinateSystem();
            return String.format("player CS :: x: %s, y: %s, z: %s", cs.DirX, cs.DirY, cs.DirZ);
        });


        InputManager.RegisterBinding(KeyEvent.VK_W, () -> WorldManager.Player.Rotate(Axis.X, -10));
        InputManager.RegisterBinding(KeyEvent.VK_S, () -> WorldManager.Player.Rotate(Axis.X, 10));
        InputManager.RegisterBinding(KeyEvent.VK_A, () -> WorldManager.Player.Rotate(Axis.Y, -10));
        InputManager.RegisterBinding(KeyEvent.VK_D, () -> WorldManager.Player.Rotate(Axis.Y, 10));


        GameLoop gameLoop = GameLoop.GetInstance();
        gameLoop.Start();
    }


    public static void main(String[] args) {
        run();
    }
}
