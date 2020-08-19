package Core.Collision;

import Models.Vector3D;

public class IntersectData {

    boolean doesIntersect;
    Vector3D closestPointOnOtherCollider;

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
