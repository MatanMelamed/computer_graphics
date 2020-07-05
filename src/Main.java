import Core.Collision.ColliderType;
import Core.GameManager;
import Core.Graphics.Debugger;
import Core.Graphics.Renderer;


public class Main {

    private static void run() {


        Renderer.EnableDebugger();

        Debugger.AddDebug(() -> {
            var p = GameManager.GetPlayer();
            return String.format("x: %.4f, y: %.4f, z: %.4f", p.GetAxisAngleX(), p.GetAxisAngleY(), p.GetAxisAngleZ());
        });

         GameManager.StartGame();
    }


    public static void main(String[] args) {
        run();
    }
}
