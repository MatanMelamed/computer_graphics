package Core.Collision.CollisionHandlers;

import Core.Collision.Collider;
import Core.Collision.IntersectData;

public interface CollisionHandler {
    IntersectData CheckCollision(Collider first, Collider second);
}
