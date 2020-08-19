// Matan Melamed 205973613
package Models;

public class Vector3D {

    public float x;
    public float y;
    public float z;

    private static Vector3D perpendicular = new Vector3D(0, 0, 1);

    public static final Vector3D ZERO = new Vector3D(0, 0, 0);

    public Vector3D() {
        this(0, 0, 0);
    }

    public Vector3D(Vector3D other) {
        this(other.x, other.y, other.z);
    }

    public Vector3D duplicate() { return new Vector3D(x, y, z); }


    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D getPerpendicular() {
        return this.cross(perpendicular);
    }

    public boolean DifferentThan(Vector3D other) {
        return x != other.x || y != other.y || z != other.z;
    }

    public void Set(Vector3D other) {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    public Vector3D plus(Vector3D other) {
        return new Vector3D(
                x + other.x,
                y + other.y,
                z + other.z);
    }

    public Vector3D plus(float x, float y, float z) {
        return new Vector3D(
                this.x + x,
                this.y + y,
                this.z + z);
    }

    public Vector3D minus(float d) {
        return new Vector3D(
                this.x - d,
                this.y - d,
                this.z = d);
    }

    public Vector3D minus(Vector3D other) {
        return new Vector3D(
                x - other.x,
                y - other.y,
                z - other.z);
    }

    public Vector3D multiply(double scalar) {
        return multiply((float) scalar);
    }

    public Vector3D multiply(float scalar) {
        return new Vector3D(
                x * scalar,
                y * scalar,
                z * scalar);
    }

    public Vector3D cross(Vector3D other) {
        return new Vector3D(
                x * other.z - z * other.y,
                z * other.x - x * other.z,
                x * other.y - y * other.x);
    }

    public double dot(Vector3D other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public float magnitude() {
        return (float) Math.sqrt(dot(this));
    }

    public double angle() {
        double result = Math.toDegrees(Math.atan2(y, x));
        return result < 0 ? 360 + result : result;
    }

    public Vector3D normalize() {
        float magnitude = magnitude();
        if (magnitude == 0) {
            return new Vector3D(0, 0, 0);
        }
        return new Vector3D(x / magnitude, y / magnitude, z / magnitude);
    }

    public double angleBetween(Vector3D other) {
        return Math.acos(this.dot(other) / (this.magnitude() * other.magnitude()));
    }

    @Override
    public String toString() {
        return String.format("(%.4f, %.4f, %.4f)", x, y, z);
    }
}
