package Mathematics;

public class TransformMatrices {


    public static Matrix translate(double dx, double dy) {
        return new Matrix(new double[][]{
                {1, 0, dx},
                {0, 1, dy},
                {0, 0, 1}
        });
    }

    public static Matrix translate(double dx, double dy, double dz) {
        return new Matrix(new double[][]{
                {1, 0, dx},
                {0, 1, dy},
                {0, 0, 1}
        });
    }

    public static Matrix scale(double a, double b) {
        return new Matrix(new double[][]{
                {a, 0, 0},
                {0, b, 0},
                {0, 0, 1}
        });
    }

    public static Matrix rotate(double eta) {
        return new Matrix(new double[][]{
                {Math.cos(eta), Math.sin(eta), 0},
                {-Math.sin(eta), Math.cos(eta), 0},
                {0, 0, 1}
        });
    }
}