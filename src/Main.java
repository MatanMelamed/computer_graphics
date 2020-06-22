import Core.GameLoop;
import Core.InputManager;
import Graphics.Renderer;
import World.BoxObject;
import World.WorldManager;


public class Main {

    public static void run() {
        Renderer.Initialize("MyGame", 1366, 768);
        InputManager.Initialize();

        GameLoop gameLoop = GameLoop.GetInstance();
        gameLoop.Start();


        BoxObject box;

        box = new BoxObject("wood_box.jpg");
        box.SetPosition(10, 0, 0);
        WorldManager.AddGameObject(box);

        box = new BoxObject("wood_box.jpg");
        box.SetPosition(-10, 0, 0);
        WorldManager.AddGameObject(box);

        box = new BoxObject("wood_box.jpg");
        box.SetPosition(0, 0, 10);
        WorldManager.AddGameObject(box);

        box = new BoxObject("wood_box.jpg");
        box.SetPosition(0, 0, -10);
        WorldManager.AddGameObject(box);
    }

    public static void main(String[] args) {
        run();

    }
}
