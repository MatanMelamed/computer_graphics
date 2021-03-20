package com.matan.studies.computergraphics.World;

import com.matan.studies.computergraphics.Core.Collision.CollisionChecker;
import com.matan.studies.computergraphics.Core.Collision.IntersectData;
import com.matan.studies.computergraphics.GameObjects.Components.ColliderComponent;
import com.matan.studies.computergraphics.GameObjects.GameObject;
import com.matan.studies.computergraphics.GameObjects.GameObjectComponent;

import java.util.ArrayList;

public abstract class BaseWorld implements World {
    protected GameObject root = new GameObject("root");
    protected ArrayList<ColliderComponent> colliderComponents = new ArrayList<>();

    @Override
    public void Initialize() {
        root.InitializeAll();
        collectObjectsWithCollider(root);
        System.out.println(colliderComponents.size()+" colliders");
        root.PrintAll(0);
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
