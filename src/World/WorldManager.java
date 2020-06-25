package World;

import Core.InputManager;
import GameObjects.PlayerObject;
import Models.CoordinateSystem;
import Models.Vector3D;

import javax.media.opengl.glu.GLU;


public class WorldManager {

    private World currentWorld;
    public static PlayerObject Player;
    private static WorldManager instance = new WorldManager();
    public static boolean PlayerView;

    private WorldManager() {
        Player = new PlayerObject("white_squrare.png");
    }

    public static void SetWorld(World newWorld) {
        instance.currentWorld = newWorld;
    }

    public static void Update(float dt) {
        if (instance.currentWorld != null) {
            instance.currentWorld.Update();
        }
    }

    public static void SetLookAt(GLU glu) {

        CoordinateSystem cs = Player.getCoordinateSystem();
//        Vector3D direction = !PlayerView ? cs.Position.minus(cs.DirZ) : new Vector3D(InputManager.x, InputManager.y, InputManager.z);
        Vector3D direction = cs.Position.plus(cs.DirZ);
//        glu.gluLookAt(0, 0, 0, 0,0,-1, 0, 1, 0);
//        glu.gluLookAt(cs.Position.x, cs.Position.y, cs.Position.z, 0, 0, -1, cs.DirY.x, cs.DirY.y, cs.DirY.z);
        glu.gluLookAt(cs.Position.x, cs.Position.y, cs.Position.z, direction.x, direction.y, direction.z, cs.DirY.x, cs.DirY.y, cs.DirY.z);
    }

    public static void Render() {
        if (instance.currentWorld != null) {
            instance.currentWorld.Render();
        }
        instance.Player.draw();
    }
}
