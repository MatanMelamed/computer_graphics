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
         Player = new PlayerObject("white_squrare.png");
    }

    public static void Update(float dt) {
        instance.currentWorld.Update();
    }

    public static void SetLookAt(GLU glu){
        CoordinateSystem cs = Player.getCoordinateSystem();
        Vector3D direction = cs.Position.minus(cs.DirZ);
        glu.gluLookAt(cs.Position.x,cs.Position.y,cs.Position.z,direction.x,direction.y, direction.z,0,1,0);
    }

    public static void Render() {
        instance.currentWorld.Render();
        // instance.Player.draw();
    }
}
