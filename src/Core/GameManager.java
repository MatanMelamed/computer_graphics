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

    private static GameManager instance = new GameManager();

    private GameManager() { }

    public static PlayerObject GetPlayer() { return levelManager.player; }

    public static void StartGame() {
//        levelManager.SetWorld(new Level1());
        levelManager.SetWorld(new Workspace());
        WindowManager.Initialize("MyGame", 1366, 768);
        InputManager.Initialize();
        Renderer.SetInitCallBack(GameManager::initializeGraphics);
        Renderer.SetDisplayCallBack(GameManager::drawFrameEvent);
        GameLoop.GetInstance().Start();
    }

    // delta time in milli second
    public static void Update(float deltaTime) { levelManager.UpdateCurrentWorld(deltaTime); }

    public static void Render() {
        // invokes Renderer's display method using GL, will call
        WindowManager.DrawNextFrame();
    }

    private static void initializeGraphics() { levelManager.InitializeLevel(); }

    private static void drawFrameEvent() {
        levelManager.SetLookAt();
        // Graphics.Mid();
        NextRenderExecutioner.BeginRendering();
        levelManager.DrawCurrentWorld();
    }
}
