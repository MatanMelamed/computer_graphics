package Core;

import GameObjects.Prefabs.PlayerObject;
import Core.Graphics.Renderer;
import Models.Vector3D;
import World.World;


class WorldManager {

    private World currentWorld;
    private PlayerObject Player;

    public WorldManager() { }

    public PlayerObject getPlayer() {
        return Player;
    }

    public void SetPlayer(PlayerObject player) {
        Player = player;
        Player.Move(2, 0, 2);

    }

    public void SetWorld(World newWorld) {
        currentWorld = newWorld;
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
        Vector3D direction = Player.GetDirection();
        Renderer.GetGLU().gluLookAt(position.x, position.y + PlayerObject.Height,
                position.z, direction.x, direction.y, direction.z,
                0, 1, 0);
    }


    public void DrawCurrentWorld() {
        if (currentWorld != null) {
            currentWorld.Render();
        }
        Player.RenderAll();
    }
}
