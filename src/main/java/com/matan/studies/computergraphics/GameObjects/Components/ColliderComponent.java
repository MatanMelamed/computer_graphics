// Matan Melamed 205973613
package com.matan.studies.computergraphics.GameObjects.Components;

import com.matan.studies.computergraphics.Core.Collision.*;
import com.matan.studies.computergraphics.Core.Collision.CollisionHandlers.CollisionHanlder;
import com.matan.studies.computergraphics.Core.Graphics.Graphics;
import com.matan.studies.computergraphics.GameObjects.GameObject;
import com.matan.studies.computergraphics.GameObjects.GameObjectComponent;
import com.matan.studies.computergraphics.Models.Vector3D;

public class ColliderComponent extends GameObjectComponent {

    private Collider collider;
    private CollisionHanlder hanlder;
    public boolean passive;

    public ColliderComponent(Collider collider) {
        this(collider, null, true);
    }

    public ColliderComponent(Collider collider, boolean passive) {
        this(collider, null, passive);
    }

    public ColliderComponent(Collider collider, CollisionHanlder handler) {
        this(collider, handler, true);
    }

    public ColliderComponent(Collider collider, CollisionHanlder handler, boolean passive) {
        this.collider = collider;
        this.hanlder = handler;
        this.passive = passive;
    }

    public Collider GetCollider() {
        return collider;
    }

    @Override
    public void ParentMoved(float x, float y, float z) {
        collider.Translate(new Vector3D(x, y, z));
    }

    @Override
    public void Initialize() {
    }

    @Override
    public void Update(float deltaTime) {

    }

    @Override
    public void Render() {
        if (!isDebug) return;

        Vector3D parentPos = GetParent().GetPosition();

        if (collider.GetType() == ColliderType.AABB) {
            GameObject p = GetParent();

            Graphics.Rotate((float) -p.GetAxisAngleZ(), 0, 0, 1);
            Graphics.Rotate((float) -p.GetAxisAngleX(), 1, 0, 0);
            Graphics.Rotate((float) p.GetAxisAngleY(), 0, 1, 0);

            AxisAlignedBoundingBox aabb = (AxisAlignedBoundingBox) collider;
            Graphics.SetColor(0, 1, 0);
            Graphics.DrawAABB(aabb.min, aabb.max);
            Graphics.SetColor(1, 1, 1);

            Graphics.Rotate((float) -p.GetAxisAngleY(), 0, 1, 0);
            Graphics.Rotate((float) p.GetAxisAngleX(), 1, 0, 0);
            Graphics.Rotate((float) p.GetAxisAngleZ(), 0, 0, 1);

        } else if (collider.GetType() == ColliderType.BS) {
            BoundingSphere sphere = (BoundingSphere) collider;
            Vector3D pos = sphere.getCenter();
            Graphics.Translate(-parentPos.x, -parentPos.y, -parentPos.z);
            Graphics.Translate(pos.x, pos.y, pos.z);
            Graphics.DrawSphere(sphere.getRadius());
            Graphics.Translate(-pos.x, -pos.y, -pos.z);
            Graphics.Translate(parentPos.x, parentPos.y, parentPos.z);
        }
    }

    public void CollisionHandle(IntersectData data, ColliderComponent other) {
        if (hanlder != null) {
            hanlder.HandleCollision(data, other);
        }
    }
}
