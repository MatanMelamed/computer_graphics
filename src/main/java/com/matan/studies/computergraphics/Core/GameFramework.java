// Matan Melamed 205973613
package com.matan.studies.computergraphics.Core;

import com.matan.studies.computergraphics.Core.Graphics.NextRenderExecutioner;
import com.matan.studies.computergraphics.Core.Graphics.Renderer;
import com.matan.studies.computergraphics.Core.Graphics.WindowManager;

public class GameFramework {

    private static GameManager gameManager;

    private static void initialize() { gameManager.Initialize(); }

    private static void update(float deltaTime) { gameManager.Update(deltaTime); }

    private static void render() {
        NextRenderExecutioner.BeginRendering();
        gameManager.Render();
    }

    public static void StartGame() {
        gameManager = GameManager.getInstance();
        WindowManager.Initialize("MyGame", 1366, 768);
        InputManager.Initialize();
        Renderer.SetInitCallBack(GameFramework::initialize);
        Renderer.SetDisplayCallBack(GameFramework::render);
        GameLoop.GetInstance().Start();
    }

    // For Game Loop methods

    // Update logic with time from last update
    static void Update(float deltaTime) { update(deltaTime); }

    // Invokes Renderer's display method using GL, will call render
    static void Render() { WindowManager.DrawNextFrame(); }
}
