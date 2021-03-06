// Matan Melamed 205973613
package com.matan.studies.computergraphics.Core.Collision.CollisionHandlers;

import com.matan.studies.computergraphics.Core.Collision.AxisAlignedBoundingBox;
import com.matan.studies.computergraphics.Core.Collision.Collider;
import com.matan.studies.computergraphics.Core.Collision.IntersectData;
import com.matan.studies.computergraphics.Models.Vector3D;

public class AABBwithAABB implements CollisionChecker {
    @Override
    public IntersectData CheckCollision(Collider first, Collider second) {
        AxisAlignedBoundingBox a = (AxisAlignedBoundingBox) first;
        AxisAlignedBoundingBox b = (AxisAlignedBoundingBox) second;

        return new IntersectData((a.min.x <= b.max.x && a.max.x >= b.min.x) &&
                (a.min.y <= b.max.y && a.max.y >= b.min.y) &&
                (a.min.z <= b.max.z && a.max.z >= b.min.z), new Vector3D(0, 0, 0));
    }
}
