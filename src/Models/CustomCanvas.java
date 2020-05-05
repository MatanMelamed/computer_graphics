package Models;

import Mathematics.Matrix;
import Mathematics.Vector4D;
import Mathematics.TransformMatrices;

import java.awt.*;

public class CustomCanvas extends BaseMouseHandlerCanvas {

    private Scene scene;
    private View view;
    private Vector4D screenCenter;

    private Matrix totalTransformation;
    private Matrix currentTransformation;


    public CustomCanvas(View newView) {
        super((int) newView.viewWidth + 40, (int) newView.viewHeight + 40);
        view = newView;

        screenCenter = new Vector4D(view.viewWidth / 2, view.viewHeight / 2, 0);
        System.out.println(screenCenter);

        scene = new Scene(view);
        scene.read("resources/example.scn");
        scene.ToggleClip();

        totalTransformation = Matrix.I;
        currentTransformation = Matrix.I;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Matrix finalTransformation = currentTransformation.mult(totalTransformation).mult(view.viewModel);
        scene.draw(g, finalTransformation);
        g.drawRect(20, 20, (int) view.viewWidth, (int) view.viewHeight);
    }

    void drawGrid(Graphics g) {
        for (int i = 0; i < 2; i++) {
            g.drawLine((i + 1) * width / 3, 0, (i + 1) * width / 3, height);
            g.drawLine(0, (i + 1) * height / 3, width, (i + 1) * height / 3);
        }
    }

    void applyMouseController(int xIndex, int yIndex) {

        Vector4D startVector = new Vector4D(start.x, start.y).minus(screenCenter);
        Vector4D endVector = new Vector4D(end.x, end.y).minus(screenCenter);

        if (xIndex == 1 && yIndex == 1) { // translate
            double dx = end.x - start.x;
            double dy = end.y - start.y;
            currentTransformation = TransformMatrices.translate(screenCenter.x(), screenCenter.y())
                    .mult(TransformMatrices.translate(dx, dy))
                    .mult(TransformMatrices.translate(-screenCenter.x(), -screenCenter.y()));
        } else if (Math.abs(xIndex - yIndex) == 1) { // scale

            double sf = endVector.magnitude() / startVector.magnitude();
            currentTransformation = TransformMatrices.translate(screenCenter.x(), screenCenter.y())
                    .mult(TransformMatrices.scale(sf, sf))
                    .mult(TransformMatrices.translate(-screenCenter.x(), -screenCenter.y()));
        } else { // rotate
            double angle = endVector.angle() - startVector.angle();
            currentTransformation = TransformMatrices.translate(screenCenter.x(), screenCenter.y())
                    .mult(TransformMatrices.rotate(Math.toRadians(angle)))
                    .mult(TransformMatrices.translate(-screenCenter.x(), -screenCenter.y()));

        }
    }

    @Override
    protected void DragEvent() {
        int[] co = pointToSquareCoordinate(start);
        applyMouseController(co[0], co[1]);
    }

    @Override
    protected void ReleaseEvent() {
        totalTransformation = currentTransformation.mult(totalTransformation);
        currentTransformation = Matrix.I;
    }
}
