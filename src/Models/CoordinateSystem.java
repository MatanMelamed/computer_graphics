package Models;

enum Axis {
    X,
    Y,
    Z
}

public class CoordinateSystem {
    public Vector3D position;
    public Vector3D x, y, z;

    public CoordinateSystem(Vector3D newOrigin) {
        position = newOrigin;
        x = new Vector3D();
        y = new Vector3D();
        z = new Vector3D();
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
            a = y;
            b = z;
        } else if (axis == Axis.Y) {
            a = x;
            b = z;
        } else { // axis == Axis.Z
            a = x;
            b = y;
        }

        a = a.multiply(Math.cos(angle)).plus(b.multiply(Math.sin(angle)));
        b = a.multiply(Math.sin(angle) * -1d).plus(b.multiply(Math.cos(angle)));
        a.normalizeSelf();
        b.normalizeSelf();
    }


}
