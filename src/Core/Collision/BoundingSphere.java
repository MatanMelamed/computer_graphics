package Core.Collision;

import Models.Vector3D;

public class BoundingSphere extends Collider {

    Vector3D center;
    float radius;

    public BoundingSphere(Vector3D center, float radius) {
        super(ColliderType.BS);
        this.center = center;
        this.radius = radius;
    }

    public Vector3D getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public void Translate(Vector3D translation) {
        center = center.plus(translation);
    }

    @Override
    public void SetPosition(Vector3D newPosition) {
        center = newPosition;
    }
}
