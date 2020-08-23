// Matan Melamed 205973613
package Core.Collision.CollisionHandlers;

import Core.Collision.BoundingSphere;
import Core.Collision.Collider;
import Core.Collision.IntersectData;
import Models.Vector3D;

public class BSwithBS implements CollisionChecker {

    @Override
    public IntersectData CheckCollision(Collider first, Collider second) {
        BoundingSphere a = (BoundingSphere) first;
        BoundingSphere b = (BoundingSphere) second;

        float distance = (a.getCenter().x - b.getCenter().x) * (a.getCenter().x - b.getCenter().x) +
                (a.getCenter().y - b.getCenter().y) * (a.getCenter().y - b.getCenter().y) +
                (a.getCenter().z - b.getCenter().z) * (a.getCenter().z - b.getCenter().z);

        float radius = (a.getRadius() + b.getRadius()) * ((a.getRadius() + b.getRadius()));

        return new IntersectData(distance < radius, new Vector3D());
    }
}
