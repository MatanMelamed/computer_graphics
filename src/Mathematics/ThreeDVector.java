package Mathematics;

public class ThreeDVector {

    double[] data;

    public ThreeDVector() {
        this(0, 0, 0);
    }

    public ThreeDVector(double x, double y) {
        this(x, y, 0);
    }

    public ThreeDVector(double x, double y, double z) {
        this.data = new double[]{x, y, z};
    }

    public double x() { return data[0]; }

    public double y() { return data[1]; }

    public double z() { return data[2]; }

    public double get(int index) {
        return data[index];
    }

    public ThreeDVector minus(ThreeDVector other) {
        return new ThreeDVector(
                data[0] - other.data[0],
                data[1] - other.data[1],
                data[2] - other.data[2]);
    }

    public ThreeDVector multiply(int scalar) {
        return new ThreeDVector(
                data[0] * scalar,
                data[1] * scalar,
                data[2] * scalar);
    }

    public ThreeDVector cross(ThreeDVector other) {
        return new ThreeDVector(
                data[1] * other.data[2] - data[2] * other.data[1],
                data[2] * other.data[0] - data[0] * other.data[2],
                data[0] * other.data[1] - data[1] * other.data[0]);
    }

    public double angle() {
        double result = Math.toDegrees(Math.atan2(y(),x()));
        return result < 0 ? 360 + result : result;

    }

    public double dot(ThreeDVector other) {
        double result = 0;
        for (int i = 0; i < 3; i++) {
            result += data[i] * other.data[i];
        }

        return result;
    }

    public double magnitude() {
        return Math.sqrt(dot(this));
    }

    public ThreeDVector normalize() {
        double magnitude = magnitude();
        return new ThreeDVector(x() / magnitude, y() / magnitude, z() / magnitude);
    }

    public double angleBetween(ThreeDVector other) {
        return Math.acos(this.dot(other) / (this.magnitude() * other.magnitude()));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = 0; i < 3; i++) {
            stringBuilder.append(data[i]).append(" ");
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
