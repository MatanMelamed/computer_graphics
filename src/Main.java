import Core.GameManager;
import Core.Graphics.Debugger;
import Core.Graphics.Renderer;


public class Main {

    private static void run() {


//        Renderer.EnableDebugger();
//        Renderer.DisableDebugger();

        Debugger.AddDebug(() -> {
            var p = GameManager.GetPlayer();
            return String.format("x: %.4f, y: %.4f, z: %.4f", p.GetAxisAngleX(), p.GetAxisAngleY(), p.GetAxisAngleZ());
        });


        Debugger.AddDebug(() -> {
            var p = GameManager.GetPlayer();
            return String.format("pos :: %s", p.GetPosition());
        });

        GameManager.StartGame();
    }


    public static void main(String[] args) {
        run();
    }
}
