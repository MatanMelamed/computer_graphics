package Models;

public class CoordinateSystem {
    public Vector3D position;
    public Vector3D DirX, DirY, DirZ;

    public CoordinateSystem(Vector3D newOrigin) {
        position = newOrigin;
        DirX = new Vector3D();
        DirY = new Vector3D();
        DirZ = new Vector3D();
    }

    public void SetPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void move(Vector3D deltaMove) {
        position = position.plus(deltaMove);
    }

    public void rotate(Axis axis, double angle) {
        angle = Math.toRadians(angle);
        Vector3D a, b;
        if (axis == Axis.X) {
            a = DirY;
            b = DirZ;
        } else if (axis == Axis.Y) {
            a = DirX;
            b = DirZ;
        } else { // axis == Axis.Z
            a = DirX;
            b = DirY;
        }

        a = a.multiply(Math.cos(angle)).plus(b.multiply(Math.sin(angle)));
        b = a.multiply(Math.sin(angle) * -1d).plus(b.multiply(Math.cos(angle)));
        a.normalizeSelf();
        b.normalizeSelf();
    }


}
