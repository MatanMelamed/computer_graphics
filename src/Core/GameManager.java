package Core;

import GameObjects.PlayerObject;
import Graphics.Renderer;
import Graphics.WindowManager;
import World.World1;

/*
    Initialize, Start, and handle main game calls of update and draw
 */
public class GameManager {

    private WorldManager worldManager = new WorldManager();

    private static GameManager instance = new GameManager();

    private GameManager() {
        worldManager.SetWorld(new World1());
        worldManager.SetPlayer(new PlayerObject());
    }

    public static PlayerObject GetPlayer() {
        return instance.worldManager.getPlayer();
    }

    public static void StartGame() {
        WindowManager.Initialize("MyGame", 1366, 768);
        InputManager.Initialize();
        Renderer.SetInitCallBack(GameManager::initGameGraphics);
        Renderer.SetDisplayCallBack(GameManager::drawFrameEvent);
        GameLoop.GetInstance().Start();
    }

    public static void UpdateLogic(float dt) {
        instance.worldManager.UpdateCurrentWorld(dt);
    }

    public static void UpdateGraphics() {
        // invokes Renderer's display method using GL, will call
        WindowManager.DrawNextFrame();
    }

    private static void initGameGraphics(){
        instance.worldManager.GetCurrentWorld().PrepareToRender();
    }

    private static void drawFrameEvent() {
        instance.worldManager.SetLookAt();
        instance.worldManager.DrawCurentWorld();
    }
}
