package Core;

import GameObjects.PlayerObject;
import Graphics.Renderer;
import Models.CoordinateSystem;
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
        Vector3D currentPos = player.GetPosition();
        player.Move(-currentPos.x + 2, -currentPos.y, -currentPos.z + 2);

    }

    public void SetWorld(World newWorld) {
        currentWorld = newWorld;
    }

    public World GetCurrentWorld() {
        return currentWorld;
    }

    public void UpdateCurrentWorld(float dt) {
        if (currentWorld != null) {
            currentWorld.Update();
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
        Player.draw();
    }
}
