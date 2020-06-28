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

        GameLoop gameLoop = GameLoop.GetInstance();
        gameLoop.Start();
    }


    public static void main(String[] args) {
        run();
    }
}
