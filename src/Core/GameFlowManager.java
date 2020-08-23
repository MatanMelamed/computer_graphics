// Matan Melamed 205973613
package Core;

import Core.Graphics.Renderer;
import Core.Sound.Sound;
import Core.Sound.SoundManager;
import GameObjects.Components.LightComponent;
import GameObjects.GameObject;
import GameObjects.Prefabs.CountdownScreen;
import GameObjects.Prefabs.LightObject;
import GameObjects.Prefabs.PlateObject;
import GameObjects.Prefabs.TimerObject;
import Models.Axis;

import java.util.ArrayList;

import static Utils.Utils.floats;

public class GameFlowManager {

    LevelManager levelManager;

    private boolean isBegun = false;
    private boolean isFinished = false;
    private boolean isVictorious = false;
    private boolean isForce = false;
    private boolean debug = false;

    private GameObject screen;
    private PlateObject victory;
    private PlateObject defeat;
    private ArrayList<PlateObject> levelLabels;


    private LightObject lightObject;

    private CountdownScreen countDown;
    private TimerObject levelEndedScreenTimer;
    private TimerObject presentLevelTimer;

    private static GameFlowManager instance = new GameFlowManager();

    public static GameFlowManager getInstance() {return instance;}

    private GameFlowManager() {
        levelManager = new LevelManager();
        screen = new GameObject("screen");

        levelManager.debug = debug;
        if (debug) {
            InputManager.TurnOnMouseRotatePlayer();
        }

        lightObject = new LightObject();
        lightObject.AddLight(new LightComponent(6, floats(0.2f, 0.2f, 0.2f, 1), floats(0.4f, 0.4f, 0.4f, 1), floats(0, 0, -1, 0)));
        screen.AddChild(lightObject);

        victory = new PlateObject("victory", "victory.png", 1, 1, 16, 9);
        victory.Rotate(Axis.X, -90);
        victory.Move(0, 2000, 0);
        victory.Disable();
        screen.AddChild(victory);

        defeat = new PlateObject("defeat", "defeat.png", 1, 1, 16, 9);
        defeat.Rotate(Axis.X, -90);
        defeat.Move(0, 2000, 0);
        defeat.Disable();
        screen.AddChild(defeat);

        levelLabels = new ArrayList<>();
        PlateObject plateObject;
        for (int i = 1; i <= levelManager.GetNumberOfLevels(); i++) {
            plateObject = new PlateObject(String.valueOf(i), "level" + i + ".png", 1, 1, 16, 9);
            plateObject.Rotate(Axis.X, -90);
            plateObject.Move(0, 2000, 0);
            plateObject.Disable();
            screen.AddChild(plateObject);
            levelLabels.add(plateObject);
        }

        countDown = new CountdownScreen(this::endCountDownForNewLevel);
        screen.AddChild(countDown);

        levelEndedScreenTimer = new TimerObject(this::startPresentLevel, 2);
        screen.AddChild(levelEndedScreenTimer);

        presentLevelTimer = new TimerObject(this::endPresentLevel, 2);
        screen.AddChild(presentLevelTimer);
    }

    private void reset() {
        if (isVictorious) {
            victory.Disable();
        } else {
            defeat.Disable();
        }
        isBegun = isVictorious = isFinished = false;
    }

    private void startPresentLevel() {
        reset();

        SoundManager.Play(Sound.Begin);
        presentLevelTimer.Enable();
        presentLevelTimer.Start();
        int currentLevelIndex = levelManager.GetCurrentLevelNumber();
        levelLabels.get(currentLevelIndex).Enable();

        levelManager.PrepareLevel();
    }

    private void endPresentLevel() {
        int currentLevel = levelManager.GetCurrentLevelNumber();
        levelLabels.get(currentLevel).Disable();

        startCountDownForNewLevel();
    }

    private void startCountDownForNewLevel() {
        countDown.Enable();
        countDown.Start();
    }

    private void endCountDownForNewLevel() {
        countDown.Disable();

        lightObject.Disable();
        isBegun = true;
        InputManager.TurnOnMouseRotatePlayer();
    }


    public void LevelEnded(boolean isVictorious) {
        lightObject.Enable();
        // set timer to 5 seconds then show current screen
        this.isFinished = true;
        this.isVictorious = isVictorious;
        if (isVictorious) {
            victory.Enable();
        } else {
            defeat.Enable();
        }
        levelEndedScreenTimer.Start();
    }


    public void ForceLevel2() {
        isForce = true;
        lightObject.Enable();
    }

    public void Initialize() {
        screen.InitializeAll();
        levelManager.Initialize();
    }

    public void Update(float deltaTime) {
        if (debug) {
            levelManager.UpdateLevel(deltaTime);
            return;
        }

        if (isForce) {
            isForce = false;
            levelManager.LevelEnded(false, false);
            levelManager.SetLevelByLevelNumber(2);
            startPresentLevel();
        }

        if (!isBegun && !presentLevelTimer.IsRunning() && !countDown.IsRunning()) {
            startPresentLevel();
        } else if (isBegun && !isFinished) {
            levelManager.UpdateLevel(deltaTime);
        } else {
            screen.UpdateAll(deltaTime);
        }
    }

    public void Render() {
        if (debug) {
            levelManager.SetLookAt();
            levelManager.RenderLevel();
            return;
        }

        if (isBegun && !isFinished) {
            levelManager.SetLookAt();
            levelManager.RenderLevel();
        } else {
            Renderer.GetGLU().gluLookAt(
                    0, 2000, -6.1,
                    0, 2000, 1,
                    0, 1, 0);
            screen.RenderAll();
        }
    }
}
