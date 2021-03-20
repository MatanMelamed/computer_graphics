// Matan Melamed 205973613
package com.matan.studies.computergraphics.Models;

public class Transform {

    public static Vector3D WORLD_X = new Vector3D(1, 0, 0);
    public static Vector3D WORLD_Y = new Vector3D(0, 1, 0);
    public static Vector3D WORLD_Z = new Vector3D(0, 0, 1);

    public Vector3D Position;
    public Vector3D DirX, DirY, DirZ;

    public double xDiv, yDiv, zDiv;

    public Transform() {
        Position = new Vector3D(0, 0, 0);
        DirX = new Vector3D(1, 0, 0);
        DirY = new Vector3D(0, 1, 0);
        DirZ = new Vector3D(0, 0, -1);
        xDiv = yDiv = zDiv = 0;
    }

    public void SetPosition(float x, float y, float z) {
        this.Position.x = x;
        this.Position.y = y;
        this.Position.z = z;
    }

    public void Move(Vector3D delta) {
        Position = Position.plus(delta);
    }

    public void Rotate(Axis axis, double angle) {
        Vector3D a, b, newA, newB;
        if (axis == Axis.X) {
            a = DirY;
            b = DirZ;
            xDiv = (xDiv + angle) % 360;
        } else if (axis == Axis.Y) {
            a = DirX;
            b = DirZ;
            yDiv = (yDiv + angle) % 360;
        } else { // axis == Axis.Z
            a = DirX;
            b = DirY;
            zDiv = (zDiv + angle) % 360;
        }

        angle = Math.toRadians(-angle);

        newA = a.multiply(Math.cos(angle)).plus(b.multiply(Math.sin(angle)));
        newB = a.multiply(-Math.sin(angle)).plus(b.multiply(Math.cos(angle)));
//        newB = b.multiply(Math.cos(angle)).minus(a.multiply(Math.sin(angle)));

        a.Set(newA.normalize());
        b.Set(newB.normalize());
    }

    @Override
    public String toString() {
        return String.format("Pos: %s, Dir: (x: %s, y: %s, z: %s), Angles: (x: %.4f, y: %.4f, z: %.4f)", Position, DirX, DirY, DirZ, xDiv, yDiv, zDiv);
    }
}
