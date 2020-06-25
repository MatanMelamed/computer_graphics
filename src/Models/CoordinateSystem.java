package Models;

public class CoordinateSystem {

    public static Vector3D WORLD_X = new Vector3D(1, 0, 0);
    public static Vector3D WORLD_Y = new Vector3D(0, 1, 0);
    public static Vector3D WORLD_Z = new Vector3D(0, 0, 1);


    public Vector3D Position;
    public Vector3D DirX, DirY, DirZ;

    private double xDiv, yDiv, zDiv;

    public CoordinateSystem() {
        Position = new Vector3D(0, 0, 0);
        DirX = new Vector3D(1, 0, 0);
        DirY = new Vector3D(0, 1, 0);
        DirZ = new Vector3D(0, 0, 1);
        xDiv = yDiv = zDiv = 0;
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

        angle = Math.toRadians(angle);

        newA = a.multiply(Math.cos(angle)).plus(b.multiply(Math.sin(angle)));
        newB = b.multiply(Math.cos(angle)).minus(a.multiply(Math.sin(angle)));

        a.Set(newA.normalize());
        b.Set(newB.normalize());
    }


    @Override
    public String toString() {
        return String.format("Pos: %s, Dir: (x: %s, y: %s, z: %s)", Position, DirX, DirY, DirZ);
    }

    public double GetXDiv() {
        return xDiv;
    }

    public double GetYDiv() {
        return yDiv;
    }

    public double GetZDiv() {
        return zDiv;
    }

    public double[] AxisAnglesFromWorld() {
        return new double[]{GetXDiv(), GetYDiv(), GetZDiv()};
    }

    public void check() {
        double angleY;

        Vector3D vz = DirZ.minus(new Vector3D(0, DirZ.y, 0));
        angleY = vz.angleBetween(WORLD_Z);
        System.out.println(Math.toDegrees(angleY));

        vz = DirY.minus(new Vector3D(DirY.x, 0, 0));
        System.out.println(Math.toDegrees(vz.angleBetween(WORLD_Y)));
        System.out.println(this);


    }
}
