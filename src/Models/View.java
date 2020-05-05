package Models;

import Mathematics.Matrix;
import Mathematics.Vector4D;
import Mathematics.TransformMatrices;

import java.io.File;
import java.util.Scanner;

public class View {

    public Vector4D position;
    public Vector4D lookAt;
    public Vector4D up;
    public Vector4D window;
    public double viewWidth, viewHeight;

    public Matrix VM1;
    public Matrix VM2;

    public View() {
        read("resources/example3d.viw");

        Vector4D Zv = position.minus(lookAt).multiply(1d / position.minus(lookAt).magnitude());
        Vector4D Xv = up.cross(Zv).multiply(1d / up.cross(Zv).magnitude());
        Vector4D Yv = Zv.cross(Xv);

        VM1 = new Matrix(new double[][]{
                {Xv.x(), Xv.y(), Xv.z(), 0},
                {Yv.x(), Yv.y(), Yv.z(), 0},
                {Zv.x(), Zv.y(), Zv.z(), 0},
                {0, 0, 0, 1}
        });


        VM1 = VM1.mult(TransformMatrices.translate(
                -position.x(),
                -position.y(),
                -position.z()));

        VM2 = vm2();
    }

    private Matrix vm2() {
        Matrix m = new Matrix(new double[][]{
                {viewWidth / (window.y() - window.x()), 0, 0, 0},
                {0, -viewHeight / (window.w() - window.z()), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });

        Matrix t2 = TransformMatrices.translate(
                viewHeight / 2 + 20,
                viewWidth / 2 + 20,
                0);
        Matrix t1 = TransformMatrices.translate(
                window.x() + ((window.y() - window.x()) / 2),
                window.z() + ((window.w() - window.z()) / 2),
                0);
        return t2.mult(m).mult(t1);
    }


    public void read(String fileName) {
        Scanner scanner = null;
        try {
            File file = new File(fileName);
            scanner = new Scanner(file);
        } catch (Exception e) {
            System.out.println("Error\n" + e.getMessage());
            e.printStackTrace();
        }
        if (scanner == null) {return;}

        String[] positionString = scanner.nextLine().split(" ");
        position = new Vector4D(
                Double.parseDouble(positionString[1]),
                Double.parseDouble(positionString[2]),
                Double.parseDouble(positionString[3]));

        String[] lookAtString = scanner.nextLine().split(" ");
        lookAt = new Vector4D(
                Double.parseDouble(lookAtString[1]),
                Double.parseDouble(lookAtString[2]),
                Double.parseDouble(lookAtString[3]));

        String[] upString = scanner.nextLine().split(" ");
        up = new Vector4D(
                Double.parseDouble(upString[1]),
                Double.parseDouble(upString[2]),
                Double.parseDouble(upString[3]));

        String[] worldCoord = scanner.nextLine().split(" ");
        window = new Vector4D(
                Double.parseDouble(worldCoord[1]),
                Double.parseDouble(worldCoord[2]),
                Double.parseDouble(worldCoord[3]),
                Double.parseDouble(worldCoord[4])
        );

        String[] viewport = scanner.nextLine().split(" ");
        viewWidth = Integer.parseInt(viewport[1]);
        viewHeight = Integer.parseInt(viewport[2]);
    }

    boolean OutOfViewport(Vector4D vector) {
        boolean d = vector.x() > viewWidth + 20 || vector.x() < 20 || vector.y() > viewHeight + 20 || vector.y() < 20;
        return d;
    }

}
