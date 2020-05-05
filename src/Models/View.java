package Models;

import Mathematics.Matrix;
import Mathematics.ThreeDVector;
import Mathematics.TransformMatrices;

import java.io.File;
import java.util.Scanner;

public class View {

    public double windowWidth, windowHeight;
    public double viewWidth, viewHeight;
    public double originX, originY;
    public double angle;

    public Matrix viewModel;

    public View() {
        read("resources/example.viw");

        Matrix T = TransformMatrices.translate(-originX, -originY);
        Matrix R = TransformMatrices.rotate(angle);
        Matrix S = TransformMatrices.scale(
                viewWidth / windowWidth,
                -viewHeight / windowHeight);
        Matrix Tr = TransformMatrices.translate(
                viewWidth / 2 + 20,
                viewHeight / 2 + 20);
        viewModel = Tr.mult(S).mult(R).mult(T);
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

        String[] originStrings = scanner.nextLine().split(" ");
        originX = Integer.parseInt(originStrings[1]);
        originY = Integer.parseInt(originStrings[2]);

        String[] direction = scanner.nextLine().split(" ");
        angle = Double.parseDouble(direction[1]);

        String[] size = scanner.nextLine().split(" ");
        viewWidth = Integer.parseInt(size[1]);
        viewHeight = Integer.parseInt(size[2]);

        String[] res = scanner.nextLine().split(" ");
        windowWidth = Integer.parseInt(res[1]);
        windowHeight = Integer.parseInt(res[2]);
    }

    boolean OutOfViewport(ThreeDVector vector) {
        boolean d = vector.x() > viewWidth + 20 || vector.x() < 20 || vector.y() > viewHeight + 20 || vector.y() < 20;
        return d;
    }

}
