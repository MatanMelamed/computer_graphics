package Models;

public class CoordinateSystem {
    public Vector3D Position;
    public Vector3D DirX, DirY, DirZ;

    public CoordinateSystem(Vector3D newOrigin) {
        Position = newOrigin;
        DirX = new Vector3D(1, 0, 0);
        DirY = new Vector3D(0, 1, 0);
        DirZ = new Vector3D(0, 0, -1);
    }

    public void SetPosition(float x, float y, float z) {
        this.Position.x = x;
        this.Position.y = y;
        this.Position.z = z;
    }

    public void move(Vector3D deltaMove) {
        Position = Position.plus(deltaMove);
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

        a.multiplySelf(Math.cos(angle)).plusSelf(b.multiply(Math.sin(angle)));
        b.multiplySelf(Math.cos(angle)).plusSelf(a.multiply(Math.sin(angle)* -1d));
        // b = a.multiply(Math.sin(angle) * -1d).plus(b.multiply(Math.cos(angle)));
        a.normalizeSelf();
        b.normalizeSelf();
    }

    @Override
    public String toString() {
        return String.format("Pos: %s, Dir: (x: %s, y: %s, z: %s)", Position, DirX, DirY, DirZ);
    }
}
