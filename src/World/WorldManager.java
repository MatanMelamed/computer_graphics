package World;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class WorldManager {

    private BlockingQueue<GameObject> gameObjects;

    private static WorldManager instance = new WorldManager();

    public static WorldManager getInstance() { return instance;}

    private WorldManager() {
        gameObjects = new LinkedBlockingDeque<>();
    }

    public static void AddGameObject(GameObject gameObject) {
        instance.gameObjects.add(gameObject);
    }


    public static void Update(float dt) {

    }

    public static void Render() {
        for (GameObject object : instance.gameObjects) {
            object.draw();
        }
    }
}
