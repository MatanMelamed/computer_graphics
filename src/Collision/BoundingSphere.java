package Collision;

import Models.Vector3D;

public class BoundingSphere {

    Vector3D center;
    float radius;

    public BoundingSphere(Vector3D center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    public Vector3D getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }

    IntersectData IntersectBoundingSphere(BoundingSphere other) {
        float radiusDistance = radius + other.radius;
        double centerDistance = other.getCenter().minus(center).magnitude();

        if (centerDistance < radiusDistance) {
            return new IntersectData(true, (float) centerDistance - radiusDistance);
        } else {
            return new IntersectData(false, (float) centerDistance - radiusDistance);
        }
    }
}
