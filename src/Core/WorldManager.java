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

//        Vector3D direction = !PlayerView ? cs.Position.minus(cs.DirZ) : new Vector3D(InputManager.x, InputManager.y, InputManager.z);
        Vector3D position = Player.GetPosition();
        Vector3D direction = Player.GetDirection();
//        glu.gluLookAt(0, 0, 0, 0,0,-1, 0, 1, 0);
//        glu.gluLookAt(cs.Position.x, cs.Position.y, cs.Position.z, 0, 0, -1, cs.DirY.x, cs.DirY.y, cs.DirY.z);
        Renderer.GetGLU().gluLookAt(position.x, position.y, position.z, direction.x, direction.y, direction.z, 0, 1, 0);
    }


    public void DrawCurentWorld() {

        if (currentWorld != null) {
            currentWorld.Render();
        }

        // last object doesn't matter because at the beginning of a new frame, the vm matrix is zeroed out to id.
        Player.draw();
    }
}
