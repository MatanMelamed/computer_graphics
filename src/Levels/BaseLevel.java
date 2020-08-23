// Matan Melamed 205973613
package Levels;

import Core.Collision.CollisionChecker;
import Core.Collision.IntersectData;
import GameObjects.Components.ColliderComponent;
import GameObjects.GameObject;
import GameObjects.GameObjectComponent;
import GameObjects.Prefabs.BoxObject;
import Models.Vector3D;

import java.util.ArrayList;

public abstract class BaseLevel implements Level {
    private GameObject root = new GameObject("root");
    private ArrayList<ColliderComponent> colliderComponents = new ArrayList<>();
    ArrayList<Vector3D> enemiesRoute = new ArrayList<>();

    GameObject maze;
    GameObject startBarrier;


    float[] finishRectange;
    float[] spawn;
    float[] enemySpawn;

    private boolean isInit;
    private int level;

    BaseLevel() {
        InitializeEnemiesRoute();
    }

    BaseLevel(int level) {
        this();
        this.level = level;
    }

    @Override
    public int GetNumber() {
        return level;
    }

    @Override
    public boolean IsInit() { return isInit; }

    protected abstract void InitializeEnemiesRoute();

    @Override
    public ArrayList<Vector3D> GetEnemyRoute() {
        return this.enemiesRoute;
    }

    @Override
    public void Initialize() {
        root.InitializeAll();
        collectObjectsWithCollider(root);
        isInit = true;
    }

    @Override
    public void Update(float deltaTime) {
        root.UpdateAll(deltaTime);
        HandleColliders();
    }

    @Override
    public void Render() { root.RenderAll(); }

    public void AddGameObject(GameObject gameObject) { root.AddChild(gameObject); }

    private void HandleColliders() {
        ColliderComponent first, second;
        IntersectData data;

        for (int i = 0; i < colliderComponents.size(); i++) {
            for (int j = i + 1; j < colliderComponents.size(); j++) {
                first = colliderComponents.get(i);
                second = colliderComponents.get(j);

                if ((!first.isEnabled || !second.isEnabled)
                        || (first.passive && second.passive)) {
                    continue;
                }

                data = CollisionChecker.CheckCollision(first.GetCollider(), second.GetCollider());
                if (data.IsIntersect()) {
                    first.CollisionHandle(data, second);
                    second.CollisionHandle(data, first);
                }
            }
        }
    }

    private ColliderComponent getColliderComponent(GameObject object) {
        for (GameObjectComponent component : object.GetComponents()) {
            if (component instanceof ColliderComponent) {
                return (ColliderComponent) component;
            }
        }
        return null;
    }

    private void collectObjectsWithCollider(GameObject current) {
        for (GameObject object : current.GetChildren()) {
            collectObjectsWithCollider(object);
        }
        ColliderComponent component = null;
        if ((component = getColliderComponent(current)) != null) {
            colliderComponents.add(component);
        }
    }

    void CreateAxisMarkers() {
        BoxObject boxObject = new BoxObject("red box", 1, 1, 1, "red.png");
        boxObject.Move(10, 15, 0);
        AddGameObject(boxObject);
        boxObject = new BoxObject("yellow box", 1, 1, 1, "yellow.png");
        boxObject.Move(0, 15, 10);
        AddGameObject(boxObject);
        boxObject = new BoxObject("green box", 1, 1, 1, "green.png");
        boxObject.Move(0, 15, 0);
        AddGameObject(boxObject);
    }

    @Override
    public float[] GetSpawnPoint() { return spawn;}

    @Override
    public GameObject GetBarrier() { return startBarrier; }

    @Override
    public float[] GetFinishRectangle() { return finishRectange; }

    @Override
    public float[] GetEnemySpawn() { return enemySpawn; }
}
