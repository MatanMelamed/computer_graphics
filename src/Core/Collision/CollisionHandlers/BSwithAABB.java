package Core.Collision.CollisionHandlers;

import Core.Collision.AxisAlignedBoundingBox;
import Core.Collision.BoundingSphere;
import Core.Collision.Collider;
import Core.Collision.IntersectData;
import Models.Vector3D;

public class BSwithAABB implements CollisionChecker {
    @Override
    public IntersectData CheckCollision(Collider first, Collider second) {
        BoundingSphere sphere;
        AxisAlignedBoundingBox box;

        if (first instanceof BoundingSphere) {
            sphere = (BoundingSphere) first;
            box = (AxisAlignedBoundingBox) second;
        } else {
            sphere = (BoundingSphere) second;
            box = (AxisAlignedBoundingBox) first;
        }

        // get box closest point to sphere center by clamping
        var x = Math.max(box.min.x, Math.min(sphere.getCenter().x, box.max.x));
        var y = Math.max(box.min.y, Math.min(sphere.getCenter().y, box.max.y));
        var z = Math.max(box.min.z, Math.min(sphere.getCenter().z, box.max.z));

        // this is the same as isPointInsideSphere
        var distance = Math.sqrt((x - sphere.getCenter().x) * (x - sphere.getCenter().x) +
                (y - sphere.getCenter().y) * (y - sphere.getCenter().y) +
                (z - sphere.getCenter().z) * (z - sphere.getCenter().z));

        return new IntersectData(distance < sphere.getRadius(), new Vector3D(x, y, z));
    }
}
