import Core.GameLoop;
import Core.InputManager;
import Graphics.Renderer;

public class Main {

    public static void run() {
        Renderer.Initialize("MyGame", 1366, 768);
        InputManager.Initialize();

        GameLoop gameLoop = GameLoop.GetInstance();
        gameLoop.Start();
    }

    public static void main(String[] args) {
        run();
    }
}
