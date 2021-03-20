package com.matan.studies.computergraphics.Core;

import com.matan.studies.computergraphics.GameObjects.Prefabs.PlayerObject;
import com.matan.studies.computergraphics.Core.Graphics.Renderer;
import com.matan.studies.computergraphics.Models.Vector3D;
import com.matan.studies.computergraphics.World.World;


class WorldManager {

    private World currentWorld;
    private PlayerObject Player;
    private boolean thirdView = false;

    public WorldManager() {
        Player = new PlayerObject();
    }

    public void EnableThirdView() { thirdView = true; }

    public void DisableThirdView() { thirdView = false; }


    public PlayerObject getPlayer() {
        return Player;
    }

    public void SetWorld(World newWorld) {
        currentWorld = newWorld;
        currentWorld.AddGameObject(Player);
    }

    public World GetCurrentWorld() {
        return currentWorld;
    }

    public void UpdateCurrentWorld(float deltaTime) {
        if (currentWorld != null) {
            currentWorld.Update(deltaTime);
        }
    }

    public void SetLookAt() {
        Vector3D position = Player.GetPosition();
        if (thirdView) {
            position = position.minus(Player.GetDirZ());
        }
        Vector3D direction = Player.GetDirection();
        Renderer.GetGLU().gluLookAt(position.x, position.y + PlayerObject.playerHeight / 2f,
                position.z, direction.x, direction.y, direction.z,
                0, 1, 0);
    }


    public void DrawCurrentWorld() {
        if (currentWorld != null) {
            currentWorld.Render();
        }
    }
}
