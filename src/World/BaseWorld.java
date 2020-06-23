package World;

import GameObjects.GameObject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class BaseWorld implements World{
    private BlockingQueue<GameObject> gameObjects;

    public BaseWorld(){
        gameObjects = new LinkedBlockingDeque<>();

    }

    public void AddGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    @Override
    public void Update() {

    }

    @Override
    public void Render() {
        for (GameObject object : gameObjects) {
            object.draw();
        }
    }
}
