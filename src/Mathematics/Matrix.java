package Mathematics;

public class Matrix {

    public static Matrix I = new Matrix(new double[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});

    public int rows, cols;
    public double[][] data;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    public Matrix(double[][] data) {
        if (data != null) {
            rows = data.length;
            cols = data[0].length;
            this.data = data;
        }
    }

    public Matrix mult(Matrix other) {
        double[][] newData = new double[this.rows][other.cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                newData[i][j] = 0;
                for (int k = 0; k < this.cols; k++) {
                    newData[i][j] += this.data[i][k] * other.data[k][j];
                }
            }
        }

        return new Matrix(newData);
    }

    public ThreeDVector multiply(ThreeDVector vector) {
        double[] newData = new double[rows];

        for (int i = 0; i < rows; i++) {
            double s = 0;
            for (int j = 0; j < cols; j++) {
                s += data[i][j] * vector.get(j);
            }
            newData[i] = s;
        }

        return new ThreeDVector(newData[0], newData[1], newData[2]);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                stringBuilder.append(data[i][j]).append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

}
