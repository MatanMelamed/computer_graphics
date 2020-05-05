public class ThreeDVector {

    public double x;
    public double y;
    public double z;

    public ThreeDVector() {
        this(0, 0, 0);
    }

    public ThreeDVector(double x, double y) {
        this(x, y, 1);
    }

    public ThreeDVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double get(int index) {
        if (index == 0) {
            return x;
        } else if (index == 1) {
            return y;
        } else if (index == 2) {
            return z;
        }
        System.out.println("WTF");
        return -1;
    }


    public ThreeDVector minus(ThreeDVector other) {
        return new ThreeDVector(
                x - other.x,
                y - other.y,
                z - other.z);
    }

    public ThreeDVector cross(ThreeDVector other) {
        return new ThreeDVector(
                y * other.z - z * other.y,
                z * other.x - x * other.z,
                x * other.y - y * other.x);
    }

    public double dot(ThreeDVector other) {
        return x * other.x + y * other.y + x * other.z;
    }

    public double magnitude() {
        return Mathematics.sqrt(dot(this));
    }

    public ThreeDVector normalize() {
        double magnitude = magnitude();
        return new ThreeDVector(x / magnitude, y / magnitude, z / magnitude);
    }

    public double angleBetween(ThreeDVector other) {
        return Mathematics.acos(this.dot(other) / (this.magnitude() * other.magnitude()));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
