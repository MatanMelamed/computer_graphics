// Matan Melamed 205973613
package com.matan.studies.computergraphics.Core.Collision;

import com.matan.studies.computergraphics.Models.Vector3D;

public class BoundingSphere extends Collider {

    private Vector3D center;
    private float radius;

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
