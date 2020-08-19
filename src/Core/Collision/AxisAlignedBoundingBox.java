package Core.Collision;

import Models.Vector3D;

public class AxisAlignedBoundingBox extends Collider {

    public Vector3D min;
    public Vector3D max;

    public AxisAlignedBoundingBox(Vector3D min, Vector3D max) {
        super(ColliderType.AABB);
        this.min = min;
        this.max = max;
    }

    @Override
    public void Translate(Vector3D translation) {
        min = min.plus(translation);
        max = max.plus(translation);
    }

    @Override
    public void SetPosition(Vector3D newPosition) {

    }

    public String toString() {
        return String.format("AABB :: Min: %s, Max: %s.\n",min,max);
    }
}
