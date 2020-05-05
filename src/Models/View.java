package Models;

import Mathematics.Matrix;
import Mathematics.Vector4D;
import Mathematics.TransformMatrices;

import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class View {

    // 2d params
    public double windowWidth, windowHeight;
    public double viewWidth, viewHeight;

    // 3d params
    public Vector4D position;
    public Vector4D lookAt;
    public Vector4D up;


    public Matrix viewModel;

    public View() {
        read("resources/example3d.viw");

        Vector4D Zv = position.minus(lookAt).multiply(1 / position.minus(lookAt).magnitude());
        Vector4D Xv = up.cross(Zv).multiply(1 / up.cross(Zv).magnitude());
        Vector4D Yv = Zv.cross(Xv);

        Matrix R = new Matrix(new double[][]{
                {Xv.x(), Xv.y(), Xv.z(), 0},
                {Yv.x(), Yv.y(), Yv.z(), 0},
                {Zv.x(), Zv.y(), Zv.z(), 0},
                {0, 0, 0, 0}
        });

        Matrix T = TransformMatrices.translate(
                -position.x(),
                -position.y(),
                -position.z());

        viewModel = R.mult(T);
//        Matrix T = TransformMatrices.translate(-originX, -originY);
//        Matrix R = TransformMatrices.rotate(angle);
//        Matrix S = TransformMatrices.scale(
//                viewWidth / windowWidth,
//                -viewHeight / windowHeight);
//        Matrix Tr = TransformMatrices.translate(
//                viewWidth / 2 + 20,
//                viewHeight / 2 + 20);
//        viewModel = Tr.mult(S).mult(R).mult(T);
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
        windowWidth = Integer.parseInt(worldCoord[1]);
        windowHeight = Integer.parseInt(worldCoord[2]);

        String[] viewport = scanner.nextLine().split(" ");
        viewWidth = Integer.parseInt(worldCoord[1]);
        viewHeight = Integer.parseInt(worldCoord[2]);
    }

    boolean OutOfViewport(Vector4D vector) {
        boolean d = vector.x() > viewWidth + 20 || vector.x() < 20 || vector.y() > viewHeight + 20 || vector.y() < 20;
        return d;
    }

}
