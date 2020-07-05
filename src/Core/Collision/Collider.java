package Core.Collision;

import Models.Vector3D;

public abstract class Collider {
    private ColliderType type;

    public Collider(ColliderType type) {
        this.type = type;
    }

    public ColliderType GetType() {return type;}

    public abstract void Translate(Vector3D translation);
    public abstract void SetPosition(Vector3D newPosition);
}
