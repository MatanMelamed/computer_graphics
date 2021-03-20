package com.matan.studies.computergraphics.GameObjects.Prefabs;

import com.matan.studies.computergraphics.Core.Collision.BoundingSphere;
import com.matan.studies.computergraphics.Core.Collision.ColliderType;
import com.matan.studies.computergraphics.Core.Collision.CollisionHandlers.CollisionHanlder;
import com.matan.studies.computergraphics.GameObjects.Components.ColliderComponent;
import com.matan.studies.computergraphics.GameObjects.GameObject;
import com.matan.studies.computergraphics.Models.Vector3D;

public class BallObject extends GameObject {

    public BallObject(float radius) {
        BoundingSphere collider = new BoundingSphere(GetPosition(), radius);
        CollisionHanlder hanlder = (data, other) ->
        {
            if (other.GetCollider().GetType() == ColliderType.BS) {
                return;
            }
            Vector3D op = data.GetClosestPointOnOtherCollider();
            Vector3D dt = collider.getCenter().minus(op);

            float dx = dt.x != 0 ? (dt.x > 0 ? radius - dt.x : dt.x - radius) : 0f;
            float dy = dt.y != 0 ? (dt.y > 0 ? radius - dt.y : dt.y - radius) : 0f;
            float dz = dt.z != 0 ? (dt.z > 0 ? radius - dt.z : dt.z - radius) : 0f;

            Move(new Vector3D(dx, dy, dz));
        };
        ColliderComponent colliderComponent = new ColliderComponent(collider, hanlder);
        AddComponent(colliderComponent);
    }


}
