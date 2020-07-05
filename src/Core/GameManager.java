package Core;

import GameObjects.Prefabs.PlayerObject;
import Core.Graphics.Renderer;
import Core.Graphics.WindowManager;
import World.World1;

/*
    initializeGraphics, Start, and handle main game calls of update and draw
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
        Renderer.SetInitCallBack(GameManager::initializeGraphics);
        Renderer.SetDisplayCallBack(GameManager::drawFrameEvent);
        GameLoop.GetInstance().Start();
    }

    public static void Update(float deltaTime) {
        instance.worldManager.UpdateCurrentWorld(deltaTime);
    }

    public static void Render() {
        // invokes Renderer's display method using GL, will call
        WindowManager.DrawNextFrame();
    }

    private static void initializeGraphics() {
        instance.worldManager.GetCurrentWorld().Initialize();
    }

    private static void drawFrameEvent() {
        instance.worldManager.SetLookAt();
        instance.worldManager.DrawCurrentWorld();
    }
}
