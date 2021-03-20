// Matan Melamed 205973613
package com.matan.studies.computergraphics.Core.Collision.CollisionHandlers;

import com.matan.studies.computergraphics.Core.Collision.BoundingSphere;
import com.matan.studies.computergraphics.Core.Collision.Collider;
import com.matan.studies.computergraphics.Core.Collision.IntersectData;
import com.matan.studies.computergraphics.Models.Vector3D;

public class BSwithBS implements CollisionChecker {

    @Override
    public IntersectData CheckCollision(Collider first, Collider second) {
        BoundingSphere a = (BoundingSphere) first;
        BoundingSphere b = (BoundingSphere) second;

        float distance = (a.getCenter().x - b.getCenter().x) * (a.getCenter().x - b.getCenter().x) +
                (a.getCenter().y - b.getCenter().y) * (a.getCenter().y - b.getCenter().y) +
                (a.getCenter().z - b.getCenter().z) * (a.getCenter().z - b.getCenter().z);

        float radius = (a.getRadius() + b.getRadius()) * ((a.getRadius() + b.getRadius()));

        return new IntersectData(distance < radius, new Vector3D());
    }
}
