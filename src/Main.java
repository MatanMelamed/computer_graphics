import Core.GameManager;
import Graphics.Debugger;
import Graphics.Renderer;


public class Main {

    private static void run() {


        Renderer.EnableDebugger();

        Debugger.AddDebug(() -> {
            var cs = GameManager.GetPlayer().AxisAnglesFromWorld();
            return String.format("x: %.4f, y: %.4f, z: %.4f", cs[0], cs[1], cs[2]);
        });

        GameManager.StartGame();
    }


    public static void main(String[] args) {
        run();
    }
}
