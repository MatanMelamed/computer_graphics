// Matan Melamed 205973613
package Core.Collision.CollisionHandlers;

import Core.Collision.IntersectData;
import GameObjects.Components.ColliderComponent;

public interface CollisionHanlder {
    void HandleCollision(IntersectData data, ColliderComponent other);
}
