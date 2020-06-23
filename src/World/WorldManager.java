package World;

import GameObjects.PlayerObject;
import Models.CoordinateSystem;
import Models.Vector3D;

import javax.media.opengl.glu.GLU;


public class WorldManager {

    private World currentWorld;
    public static PlayerObject Player;
    private static WorldManager instance = new WorldManager();

    private WorldManager() {
        currentWorld = new World1();
        // Player = new PlayerObject("white_squrare.png");
    }

    public static void Update(float dt) {
        instance.currentWorld.Update();
    }

    public static void SetLookAt(GLU glu){
//        CoordinateSystem cs = Player.getCoordinateSystem();
//        Vector3D direction = cs.position.minus(cs.DirZ);

        // glu.gluLookAt(cs.position.x,cs.position.y,cs.position.z,direction.x,direction.y, direction.z,0,0,0);
        glu.gluLookAt(0,0,0,0,0, -5,0,0,0);
    }

    public static void Render() {
        instance.currentWorld.Render();
        // instance.Player.draw();
    }
}
