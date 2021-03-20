// Matan Melamed 205973613
package com.matan.studies.computergraphics.Core.Collision;

import com.matan.studies.computergraphics.GameObjects.GameObjectComponent;
import com.matan.studies.computergraphics.Models.Vector3D;

public abstract class Collider {
    private ColliderType type;
    private GameObjectComponent parentComponent;

    public Collider(ColliderType type) {
        this.type = type;
    }

    public ColliderType GetType() {return type;}

    public abstract void Translate(Vector3D translation);

    public abstract void SetPosition(Vector3D newPosition);

    public void SetParent(GameObjectComponent newParent) {this.parentComponent = newParent;}

    public GameObjectComponent GetParent() {return parentComponent;}
}
