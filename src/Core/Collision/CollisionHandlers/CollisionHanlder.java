package Core.Collision.CollisionHandlers;

import Core.Collision.IntersectData;
import GameObjects.Components.ColliderComponent;

public interface CollisionHanlder {
    void HandleCollision(IntersectData data, ColliderComponent other);
}
