// Matan Melamed 205973613
package Core.Collision.CollisionHandlers;

import Core.Collision.Collider;
import Core.Collision.IntersectData;

public interface CollisionChecker {
    IntersectData CheckCollision(Collider first, Collider second);
}
