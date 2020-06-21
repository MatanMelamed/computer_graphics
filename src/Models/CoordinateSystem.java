package Models;

import Mathematics.Vector4D;

enum Axis {
    X,
    Y,
    Z
}

public class CoordinateSystem {
    Vector4D origin;
    Vector4D x, y, z;


    public CoordinateSystem(Vector4D newOrigin) {
        origin = newOrigin;
        x = new Vector4D();
        y = new Vector4D();
        z = new Vector4D();
    }

    public void move(Vector4D deltaMove){
        origin = origin.plus(deltaMove);
    }

    public void rotate(Axis axis, double angle) {
        angle = Math.toRadians(angle);
        Vector4D a, b;
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
        a = a.normalize();
        b = b.normalize();
    }


}
