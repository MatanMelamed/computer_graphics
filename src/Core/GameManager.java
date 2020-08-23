// Matan Melamed 205973613
package Core;

import Core.Graphics.Renderer;
import GameObjects.Components.LightComponent;
import GameObjects.Prefabs.*;
import Models.Axis;
import Models.DelayedButtonPress;
import com.jogamp.newt.event.KeyEvent;

import static Utils.Utils.floats;


public class GameManager {

    private boolean isPaused = true;
    private GameFlowManager gameFlowManager;
    private PlateObject pauseMenu;

    private DelayedButtonPress F1;
    private DelayedButtonPress F2;

    //region Singleton
    private static GameManager instance = new GameManager();

    public static GameManager getInstance() {return instance;}

    private GameManager() {
        F1 = new DelayedButtonPress(0.5f, KeyEvent.VK_F1, this::PausePressed);
        F2 = new DelayedButtonPress(0.5f, KeyEvent.VK_F2, this::ForceLevel2);

        pauseMenu = new PlateObject("rules", "rules.jpg", 1, 1, 16, 9);
        pauseMenu.Rotate(Axis.X, -90);
        pauseMenu.Move(0, 2000, 0);
        pauseMenu.AddComponent(new LightComponent(6, floats(0.9f, 0.9f, 0.9f, 1), floats(0.9f, 0.9f, 0.9f, 1), floats(0, 0, -1, 0)));
    }
    //endregion


    private void PausePressed() {
        if (isPaused) { // resume
            pauseMenu.Disable();
            InputManager.TurnOnMouseRotatePlayer();
        } else {        // pause
            pauseMenu.Enable();
            InputManager.TurnOffMouseRotatePlayer();
        }
        isPaused = !isPaused;
    }

    private void ForceLevel2() {
        if (gameFlowManager.levelManager.GetCurrentLevelNumber() != 2) {
            gameFlowManager.ForceLevel2();
        }
    }


    public static PlayerObject GetPlayer() {
        return instance.gameFlowManager.levelManager.player;
    }

    public void Initialize() {
        gameFlowManager = GameFlowManager.getInstance();
        gameFlowManager.Initialize();
        pauseMenu.InitializeAll();
    }

    public void Update(float deltaTime) {
        F1.Update(deltaTime);
        F2.Update(deltaTime);

        if (!isPaused) {
            gameFlowManager.Update(deltaTime);
        }
    }

    public void Render() {
        if (!isPaused) {
            gameFlowManager.Render();
        } else {
            Renderer.GetGLU().gluLookAt(
                    0, 2000, -6.1,
                    0, 2000, 1,
                    0, 1, 0);
            pauseMenu.RenderAll();
        }
    }
}
