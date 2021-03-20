// Matan Melamed 205973613
package com.matan.studies.computergraphics.Core.Collision;

import com.matan.studies.computergraphics.Models.Vector3D;

public class IntersectData {

    private boolean doesIntersect;
    private Vector3D closestPointOnOtherCollider;

    public IntersectData(boolean doesIntersect, Vector3D collisionPoint) {
        this.doesIntersect = doesIntersect;
        this.closestPointOnOtherCollider = collisionPoint;
    }

    public boolean IsIntersect() {
        return doesIntersect;
    }

    public Vector3D GetClosestPointOnOtherCollider() {
        return closestPointOnOtherCollider;
    }
}
