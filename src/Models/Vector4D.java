// Matan Melamed 205973613
package Models;

public class Vector4D {

    double[] data;

    private static Vector4D perpendicular = new Vector4D(0, 0, 1);

    public Vector4D() {
        this(0, 0, 0, 1);
    }

    public Vector4D(double x, double y) {
        this(x, y, 1);
    }

    public Vector4D(double x, double y, double z) {
        this(x, y, z, 1);
    }

    public Vector4D(double x, double y, double z, double w) {
        this.data = new double[]{x, y, z, w};
    }

    public double x() { return data[0]; }

    public double y() { return data[1]; }

    public double z() { return data[2]; }

    public double w() { return data[3]; }

    public double get(int index) {
        return data[index];
    }

    public Vector4D getPerpendicular() {
        return this.cross(perpendicular);
    }

    public Vector4D plus(Vector4D other) {
        return new Vector4D(
                data[0] + other.data[0],
                data[1] + other.data[1],
                data[2] + other.data[2]);
    }

    public Vector4D minus(Vector4D other) {
        return new Vector4D(
                data[0] - other.data[0],
                data[1] - other.data[1],
                data[2] - other.data[2]);
    }

    public Vector4D multiply(double scalar) {
        return new Vector4D(
                data[0] * scalar,
                data[1] * scalar,
                data[2] * scalar);
    }

    public Vector4D cross(Vector4D other) {
        return new Vector4D(
                data[1] * other.data[2] - data[2] * other.data[1],
                data[2] * other.data[0] - data[0] * other.data[2],
                data[0] * other.data[1] - data[1] * other.data[0]);
    }

    public double angle() {
        double result = Math.toDegrees(Math.atan2(y(), x()));
        return result < 0 ? 360 + result : result;

    }

    public double dot(Vector4D other) {
        double result = 0;
        for (int i = 0; i < 3; i++) {
            result += data[i] * other.data[i];
        }

        return result;
    }

    public double magnitude() {
        return Math.sqrt(dot(this));
    }

    public Vector4D normalize() {
        double magnitude = magnitude();
        return new Vector4D(x() / magnitude, y() / magnitude, z() / magnitude);
    }

    public double angleBetween(Vector4D other) {
        return Math.acos(this.dot(other) / (this.magnitude() * other.magnitude()));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = 0; i < data.length; i++) {
            stringBuilder.append(data[i]).append(" ");
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
