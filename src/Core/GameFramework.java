package Core;

import Core.Graphics.NextRenderExecutioner;
import GameObjects.Prefabs.PlayerObject;
import Core.Graphics.Renderer;
import Core.Graphics.WindowManager;
import World.Workspace;

/*
    initializeGraphics, Start, and handle main game calls of update and draw
 */
public class GameManager {

    private static LevelManager levelManager = new LevelManager();

    private GameManager() {}

    private static void initializeGraphics() { levelManager.InitializeLevel(); }

    private static void drawFrameEvent() {
        levelManager.SetLookAt();
        NextRenderExecutioner.BeginRendering();
        levelManager.DrawCurrentWorld();
    }

    public static PlayerObject GetPlayer() { return levelManager.player; }

    public static void StartGame() {
//        levelManager.SetWorld(new Level1());
        WindowManager.Initialize("MyGame", 1366, 768);
        InputManager.Initialize();
        Renderer.SetInitCallBack(GameManager::initializeGraphics);
        Renderer.SetDisplayCallBack(GameManager::drawFrameEvent);
        GameLoop.GetInstance().Start();
    }

    // delta time in milli second
    public static void Update(float deltaTime) { levelManager.UpdateCurrentWorld(deltaTime); }

    // invokes Renderer's display method using GL, will call
    public static void Render() { WindowManager.DrawNextFrame(); }
}
