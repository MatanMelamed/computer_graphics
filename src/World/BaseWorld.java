package World;

import Core.Collision.CollisionChecker;
import Core.Collision.IntersectData;
import GameObjects.Components.ColliderComponent;
import GameObjects.GameObject;
import GameObjects.GameObjectComponent;

import java.util.ArrayList;

public abstract class BaseWorld implements World {
    protected GameObject root = new GameObject();
    protected ArrayList<ColliderComponent> colliderComponents = new ArrayList<>();

    @Override
    public void Initialize() {
        root.InitializeAll();
        collectObjectsWithCollider(root);
        System.out.println(colliderComponents.size()+" colliders");
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
}
