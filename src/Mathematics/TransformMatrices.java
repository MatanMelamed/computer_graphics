package Mathematics;

public class TransformMatrices {

    public enum Axis {
        X,
        Y,
        Z
    }

    public static Matrix translate(double dx, double dy) {
        return new Matrix(new double[][]{
                {1, 0, dx},
                {0, 1, dy},
                {0, 0, 1}
        });
    }

    public static Matrix translate(double dx, double dy, double dz) {
        return new Matrix(new double[][]{
                {1, 0, 0, dx},
                {0, 1, 0, dy},
                {0, 0, 1, dz},
                {0, 0, 0, 1}
        });
    }

    public static Matrix scale(double a, double b) {
        return new Matrix(new double[][]{
                {a, 0, 0},
                {0, b, 0},
                {0, 0, 1}
        });
    }

    public static Matrix scale(double a, double b, double c) {
        return new Matrix(new double[][]{
                {a, 0, 0, 0},
                {0, b, 0, 0},
                {0, 0, c, 0},
                {0, 0, 0, 1}
        });
    }

    public static Matrix rotate(double eta) {
        return new Matrix(new double[][]{
                {Math.cos(eta), Math.sin(eta), 0},
                {-Math.sin(eta), Math.cos(eta), 0},
                {0, 0, 1}
        });
    }

    public static Matrix rotate(double eta, Axis axis) {
        Matrix result = null;
        if (axis == Axis.X) {
            result = new Matrix(new double[][]{
                    {1, 0, 0, 0},
                    {0, Math.cos(eta), -Math.sin(eta), 0},
                    {0, Math.sin(eta), Math.cos(eta), 0},
                    {0, 0, 0, 1}
            });
        } else if (axis == Axis.Z) {
            result = new Matrix(new double[][]{
                    {Math.cos(eta), -Math.sin(eta), 0, 0},
                    {Math.sin(eta), Math.cos(eta), 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1}
            });
        } else if (axis == Axis.Y) {
            result = new Matrix(new double[][]{
                    {Math.cos(eta), 0, -Math.sin(eta), 0},
                    {0, 1, 0, 0},
                    {Math.sin(eta), 0, Math.cos(eta), 0},
                    {0, 0, 0, 1}
            });
        }
        return result;
    }
}