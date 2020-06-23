// Matan Melamed 205973613
package Models;

public class Vector3D {

    public double x;
    public double y;
    public double z;

    private static Vector3D perpendicular = new Vector3D(0, 0, 1);

    public Vector3D() {
        this(0, 0, 0);
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D getPerpendicular() {
        return this.cross(perpendicular);
    }

    public Vector3D plus(Vector3D other) {
        return new Vector3D(
                x + other.x,
                y + other.y,
                z + other.z);
    }

    public Vector3D minus(Vector3D other) {
        return new Vector3D(
                x - other.x,
                y - other.y,
                z - other.z);
    }

    public Vector3D multiply(double scalar) {
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

    public Vector3D plusSelf(Vector3D other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    public Vector3D minusSelf(Vector3D other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        return this;
    }

    public Vector3D multiplySelf(double scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    public Vector3D crossSelf(Vector3D other) {
        x = x * other.z - z * other.y;
        y = z * other.x - x * other.z;
        z = x * other.y - y * other.x;
        return this;
    }

    public double dot(Vector3D other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public double magnitude() {
        return Math.sqrt(dot(this));
    }

    public double angle() {
        double result = Math.toDegrees(Math.atan2(y, x));
        return result < 0 ? 360 + result : result;
    }

    public Vector3D normalize() {
        double magnitude = magnitude();
        return new Vector3D(x / magnitude, y / magnitude, z / magnitude);
    }

    public void normalizeSelf() {
        double magnitude = magnitude();
        x /= magnitude;
        y /= magnitude;
        z /= magnitude;
    }

    public double angleBetween(Vector3D other) {
        return Math.acos(this.dot(other) / (this.magnitude() * other.magnitude()));
    }

    @Override
    public String toString() {
        return String.format("(%f, %f, %f)", x, y, z);
    }
}
