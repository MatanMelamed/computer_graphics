package Core.Collision.CollisionHandlers;

import Core.Collision.BoundingSphere;
import Core.Collision.Collider;
import Core.Collision.IntersectData;

public class BSwithBS implements CollisionHandler {

    @Override
    public IntersectData CheckCollision(Collider first, Collider second) {
        return ((BoundingSphere) first).IntersectBoundingSphere((BoundingSphere) second);
    }
}
