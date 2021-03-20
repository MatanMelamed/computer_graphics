// Matan Melamed 205973613
package com.matan.studies.computergraphics.Core;

import com.matan.studies.computergraphics.Core.Collision.AxisAlignedBoundingBox;
import com.matan.studies.computergraphics.Core.Sound.Sound;
import com.matan.studies.computergraphics.Core.Sound.SoundManager;
import com.matan.studies.computergraphics.GameObjects.Components.*;
import com.matan.studies.computergraphics.GameObjects.GameObject;
import com.matan.studies.computergraphics.GameObjects.Prefabs.*;
import com.matan.studies.computergraphics.Core.Graphics.Renderer;
import com.matan.studies.computergraphics.Levels.Level1;
import com.matan.studies.computergraphics.Levels.Level2;
import com.matan.studies.computergraphics.Models.Axis;
import com.matan.studies.computergraphics.Models.Vector3D;
import com.matan.studies.computergraphics.Levels.Level;

import java.util.ArrayList;

import static com.matan.studies.computergraphics.Utils.Utils.floats;


class LevelManager {

    // main objects to manager
    PlayerObject player;
    private boolean thirdView;


    // general game objects for game logic
    private LightObject startRoomLight;
    private LightObject endRoomLight;
    private LightLooper looper;
    private EnemyObject enemy;
    private RuleCheckerObject startGameChecker;

    // general variables for game logic
    private boolean isVictorious;
    private boolean levelStarted;
    private boolean levelFinished;
    boolean debug = false;

    private ArrayList<Level> levels = new ArrayList<>();
    private ArrayList<GameObject> finishDetectors = new ArrayList<>();
    private volatile int currentLevelIndex = 0;
    private Level currentLevel;
    private TimerObject gameEndingTimer;

    LevelManager() {
        player = new PlayerObject();
        thirdView = false;
        levels.add(new Level1());
        levels.add(new Level2());
        gameEndingTimer = new TimerObject(() -> GameFlowManager.getInstance().LevelEnded(isVictorious), 5);
        enemy = new EnemyObject(() -> this.LevelEnded(false, true));
        startRoomLight = new LightObject("start room lights");
        startRoomLight.AddLight(new LightComponent(
                5,
                floats(0.3f, 0.3f, 0.3f, 1f),
                floats(0.5f, 0.5f, 0.5f, 1f),
                floats(-3.12f, 3.67f, -11.39f, 1),
                1, 1, 20, new Vector3D(0.29f, -0.63f, 0.71f)));
        endRoomLight = new LightObject("end room lights");
        endRoomLight.AddLight(new LightComponent(
                5,
                floats(0.8f, 0.8f, 0.8f, 1f),
                floats(0.2f, 0.9f, 0.2f, 1f),
                floats(3.38f, 16.53f, 9.12f, 1),
                1, 1, 32, new Vector3D(0f, -1f, 0f)));
        CreateLightLooper();
        startGameChecker = new RuleCheckerObject(
                false,
                () -> {
                    double playerYangle = player.GetAxisAngleY();
                    return playerYangle < 40 || playerYangle > 320;
                },
                this::LevelStarted
        );
    }

    private void SetEnemy() {
        enemy.Reset();
        float[] enemySpawn = currentLevel.GetEnemySpawn();
        enemy.MoveToZero();
        enemy.Move(enemySpawn[0], enemySpawn[1], enemySpawn[2]);
        enemy.SetRoute(currentLevel.GetEnemyRoute());
    }

    private void CreateFinishDetector() {
        float[] f = currentLevel.GetFinishRectangle();
        var c = new ColliderComponent(
                new AxisAlignedBoundingBox(
                        new Vector3D(f[0], f[1], f[2]), new Vector3D(f[3], f[4], f[5])),
                (data, colliderComponent) -> {
                    if (colliderComponent.GetParent().name.equals("player")) {
                        if (!levelFinished) {
                            LevelEnded(true, true);
                        }
                    }
                });
        GameObject finishDetector = new GameObject("Finish Detector");
        finishDetector.AddComponent(c);
        currentLevel.AddGameObject(finishDetector);
        finishDetectors.add(finishDetector);
    }

    private void LevelStarted() {
        if (levelStarted) return;
        levelStarted = true;

        startRoomLight.Disable();
        looper.Enable();
        currentLevel.GetBarrier().Disable();
        enemy.Start();
        SoundManager.Play(Sound.Background);
    }

    int GetNumberOfLevels() {return levels.size();}

    void LevelEnded(boolean isVictorious, boolean peacefully) {
        if (levelFinished) return;
        levelFinished = true;
        this.isVictorious = isVictorious;
        SoundManager.Stop();
        if (!peacefully) { return; }  // only need the sound to stop

        finishDetectors.get(currentLevelIndex).Disable();
        looper.Disable();

        if (isVictorious) {
            SoundManager.Play(Sound.Victory);
        } else {
            SoundManager.Play(Sound.Defeat);
            endRoomLight.SetAllLight(floats(0.5f, 0.1f, 0.1f, 1f), floats(0.6f, 0.1f, 0.1f));
        }

        endRoomLight.Enable();
        gameEndingTimer.Start();
        if (isVictorious) {
            int newVal = (currentLevelIndex + 1) % levels.size();
            currentLevelIndex = newVal;
        }
    }

    private void CreateLightLooper() {
        LightObject lights = new LightObject("red lights");
        lights.AddLight(new LightComponent(
                0,
                floats(0.1f, 0, 0, 1),
                floats(0.7f, 0, 0, 1),
                floats(1, 0, 0, 0)));
        lights.AddLight(new LightComponent(
                1,
                floats(0.1f, 0, 0, 1),
                floats(0.7f, 0, 0, 1),
                floats(-1, 0, 0, 0)));
        lights.AddLight(new LightComponent(
                2,
                floats(0.1f, 0, 0, 1),
                floats(0.7f, 0, 0, 1),
                floats(0, 1, 0, 0)));
        lights.AddLight(new LightComponent(
                3,
                floats(0.1f, 0, 0, 1),
                floats(0.7f, 0, 0, 1),
                floats(0, 0, 1, 0)));
        lights.AddLight(new LightComponent(
                4,
                floats(0.1f, 0, 0, 1),
                floats(0.7f, 0, 0, 1),
                floats(0, 0, -1, 0)));
        looper = new LightLooper(new TimeLooperComponent(2f, 0f, 0f, 1f), lights);
    }

    private void InitializeLights(int d) {
        currentLevel.AddGameObject(startRoomLight);
        currentLevel.AddGameObject(endRoomLight);
        currentLevel.AddGameObject(looper);

        if (d == 1) {
            startRoomLight.Disable();
            LightObject all = new LightObject("StartRoomLights");
            all.AddLight(new LightComponent(
                    5,
                    floats(0.8f, 0.8f, 0.8f, 1f),
                    floats(0.9f, 0.9f, 0.9f, 1f),
                    floats(0f, 1f, 0f, 0)));
            currentLevel.AddGameObject(all);
        }
    }

    private void AddStartGameChecker() {
        currentLevel.AddGameObject(startGameChecker);
    }

    private void SetPlayer() {
        float[] spawn = currentLevel.GetSpawnPoint();
        player.MoveToZero();
        player.Move(spawn[0], spawn[1], spawn[2]);
        player.speed = 5f;
        player.ResetRotation();
        player.Rotate(Axis.Y, 180);
    }

    private void Debug() {
        SetLevel(new Level2());
        LightObject lightObject = new LightObject();
        lightObject.AddLight(new LightComponent(6, floats(0.9f, 0.9f, 0.9f, 1), floats(0.9f, 0.9f, 0.9f, 1), floats(0, -1, 0, 0)));
        currentLevel.AddGameObject(lightObject);
        player.Move(0, 15, 0);
        currentLevel.AddGameObject(player);
        currentLevel.Initialize();
        player.speed = 10f;
    }

    private void SetLevel(Level newLevel) {
        currentLevel = newLevel;
    }

    private void InitializCurrenteLevel() {
        if (debug) {
            Debug();
            return;
        }
        currentLevel.AddGameObject(enemy);

        CreateFinishDetector();
        AddStartGameChecker();
        InitializeLights(0);

        currentLevel.AddGameObject(player);
        currentLevel.AddGameObject(gameEndingTimer);
        currentLevel.Initialize();
    }

    void PrepareLevel() {
        if (debug) {
            Debug();
            return;
        }

        SetLevel(levels.get(currentLevelIndex));

        if (!currentLevel.IsInit()) {
            InitializCurrenteLevel();
        }
        SetEnemy();
        finishDetectors.get(currentLevelIndex).Enable();
        startGameChecker.Reset();
        SetPlayer();

        levelStarted = false;
        levelFinished = false;
        startRoomLight.Enable();
        endRoomLight.Disable();
        looper.Disable();
        looper.Reset();

        currentLevel.GetBarrier().Enable();
    }

    void Initialize() {
        for (Level level : levels) {
            SetLevel(level);
            InitializCurrenteLevel();
        }
        SetLevelByLevelNumber(1);
    }

    void SetLevelByLevelNumber(int levelNumber) {
        currentLevelIndex = levelNumber - 1;
        SetLevel(levels.get(currentLevelIndex));
    }


    void UpdateLevel(float deltaTime) {
        if (currentLevel != null) {
            currentLevel.Update(deltaTime);
        }
    }

    void RenderLevel() {
        if (currentLevel != null) {
            currentLevel.Render();
        }
    }

    int GetCurrentLevelNumber() { return currentLevelIndex; }

    void SetLookAt() {
        Vector3D position = (
                thirdView
                        ? player.GetPosition().plus(0, PlayerObject.playerHeight, 0).minus(player.GetDirZ())
                        : player.GetPosition().plus(0, PlayerObject.playerHeight, 0));

        Vector3D direction = player.GetDirection();

        Renderer.GetGLU().gluLookAt(
                position.x, position.y, position.z,
                direction.x, direction.y, direction.z,
                0, 1, 0);
    }
}
