package Core;

import Core.Collision.AxisAlignedBoundingBox;
import Core.Graphics.Graphics;
import GameObjects.Components.ColliderComponent;
import GameObjects.Components.LightComponent;
import GameObjects.Components.WavefrontComponent;
import GameObjects.GameObject;
import GameObjects.GameObjectComponent;
import GameObjects.Prefabs.*;
import Core.Graphics.Renderer;
import Models.Vector3D;
import World.Level;
import World.Level1;
import World.Workspace;
import com.jogamp.newt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static Utils.Utils.floats;


class LevelManager {

    private Level currentLevel;
    public PlayerObject player = new PlayerObject();
    private boolean thirdView = false;

    private GameObject endRoomLight;
    private GameObject looper;
    private GameObject finishDetector;


    private volatile boolean isPause = true;

    public LevelManager() {
        InputManager.RegisterBinding(KeyEvent.VK_P, () -> {
            if (isPause) {
                InputManager.TurnOnMouseRotatePlayer();
                isPause = false;
            } else {
                InputManager.TurnOffMouseRotatePlayer();
                isPause = true;
            }
        });
    }

    private void Pause() {
        isPause = true;
        InputManager.TurnOffMouseRotatePlayer();
    }

    private void Resume() {
        isPause = false;
        InputManager.TurnOnMouseRotatePlayer();
    }

    public void EnableThirdView() { thirdView = true; }

    public void DisableThirdView() { thirdView = false; }

    public void GameOver() {
        System.out.println("hit player");
        Pause();
    }

    public void InitializeLevel() {
        SetWorld(new Level1());

        Enemy enemy = new Enemy(this::GameOver);
        enemy.Move(-3, 0f, -25f);
        enemy.SetRoute(currentLevel.GetEnemyRoute());
        currentLevel.AddGameObject(enemy);

        InputManager.RegisterBinding(KeyEvent.VK_R, () -> System.out.println(player.GetPosition()));

//        looper = new PlayerLightLooper();
//        player.AddChild(looper);
//        SetFinishCollider();

        GameObject c = new WhiteLight(1);
        player.AddChild(c);
        InputManager.RegisterBinding(KeyEvent.VK_M, c::Disable);
        InputManager.RegisterBinding(KeyEvent.VK_N, c::Enable);

        float[] spawn = currentLevel.GetSpawnPoint();

        currentLevel.Initialize();

        player.MoveToZero();
        player.Move(spawn[0], spawn[1], spawn[2]);
        player.speed = 10f;
        Resume();
    }

    void SetFinishCollider() {
        float[] f = currentLevel.GetFinishRectangle();
        System.out.println(String.format("got %s", Arrays.toString(f)));
        var c = new ColliderComponent(
                new AxisAlignedBoundingBox(
                        new Vector3D(f[0], f[1], f[2]), new Vector3D(f[3], f[4], f[5])),
                (data, colliderComponent) -> {
                    if (colliderComponent.GetParent().name.equals("player")) {
                        AlertLevelFinished();
                    }
                });
        finishDetector = new GameObject("Finish Detector");
        finishDetector.AddComponent(c);
        currentLevel.AddGameObject(finishDetector);
    }

    void AlertLevelFinished() {
        System.out.println("finished");
        finishDetector.Disable();
        looper.Disable();

    }

    public void SetWorld(Level newLevel) {
        currentLevel = newLevel;
        currentLevel.AddGameObject(player);
    }

    public void UpdateCurrentWorld(float deltaTime) {
        if (isPlayerWon()) {
            Pause();
            // show screen ?
            // update score ?
            // switch level
            // count down
            Resume();
        }

        if (currentLevel != null && !isPause) {
            currentLevel.Update(deltaTime);
        }
    }

    public void DrawCurrentWorld() {
        if (currentLevel != null) {
            currentLevel.Render();
        }
    }

    public void SetLookAt() {
        Vector3D position = player.GetPosition();
        if (thirdView) {
            position = position.minus(player.GetDirZ());
        }
        Vector3D direction = player.GetDirection();
        Renderer.GetGLU().gluLookAt(
                position.x, position.y + PlayerObject.Height / 2f, position.z,
                direction.x, direction.y, direction.z,
                0, 1, 0);
    }

    boolean isPlayerWon() {

        return false;
    }

    void controlledLight() {
        GameObject light = new GameObject("light");
        light.AddComponent(new LightComponent(
                0,
                floats(0.1f, 0.1f, 0.1f, 1f),
                floats(0.3f, 0f, 0f, 1f),
                floats(0, 0, 0, 1f)));
        ControllableObject controllableObject = new ControllableObject(light);
        currentLevel.AddGameObject(controllableObject);
    }
}
