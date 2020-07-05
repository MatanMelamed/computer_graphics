package Core.Collision;

import Core.Collision.CollisionHandlers.AABBwithAABB;
import Core.Collision.CollisionHandlers.BSwithAABB;
import Core.Collision.CollisionHandlers.BSwithBS;
import Core.Collision.CollisionHandlers.CollisionHandler;

import java.util.HashMap;

public class CollisionChecker {

    private static HashMap<String, CollisionHandler> collisionHandlers = new HashMap<>();
    private static CollisionChecker instance = new CollisionChecker();

    public static IntersectData CheckCollision(Collider first, Collider second) {
        return collisionHandlers.get(first.GetType().toString() + second.GetType().toString())
                .CheckCollision(first, second);
    }

    private CollisionChecker() {
        Initialize();
    }

    private static void Initialize() {
        collisionHandlers.put(ColliderType.BS.toString() + ColliderType.BS.toString(), new BSwithBS());
        collisionHandlers.put(ColliderType.BS.toString() + ColliderType.AABB.toString(), new BSwithAABB());
        collisionHandlers.put(ColliderType.AABB.toString() + ColliderType.BS.toString(), new BSwithAABB());
        collisionHandlers.put(ColliderType.AABB.toString() + ColliderType.AABB.toString(), new AABBwithAABB());
    }
}
