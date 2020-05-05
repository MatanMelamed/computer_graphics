package Models;

import Mathematics.Matrix;
import Mathematics.Vector4D;
import Mathematics.TransformMatrices;

import java.awt.*;
import java.awt.event.KeyEvent;

public class CustomCanvas extends BaseMouseHandlerCanvas {

    private Scene scene;
    private View view;
    private Vector4D screenCenter;

    private Matrix totalTransformation;
    private Matrix currentTransformation;
    private Matrix m;

    public TransformMatrices.Axis currentAxis;

    public CustomCanvas(View newView) {
        super((int) newView.viewWidth + 40, (int) newView.viewHeight + 40);
        view = newView;

        m = new Matrix(4, 4);

        currentAxis = TransformMatrices.Axis.X;

        screenCenter = new Vector4D(view.viewWidth / 2, view.viewHeight / 2, 0);

        scene = new Scene(view);
        scene.read("resources/example3d.scn");

        totalTransformation = Matrix.I;
        currentTransformation = Matrix.I;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Matrix finalTransformation = currentTransformation.mult(totalTransformation).mult(view.viewModel);
        Matrix finalTransformation = view.VM2.mult(Matrix.I).mult(currentTransformation)
                .mult(totalTransformation).mult(view.VM1);
        scene.draw(g, finalTransformation);
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

        Matrix T = TransformMatrices.translate(0, 0, -view.position.minus(view.lookAt).z());
        Matrix Tnot = TransformMatrices.translate(0, 0, view.position.minus(view.lookAt).z());

        if (xIndex == 1 && yIndex == 1) { // translate
            double dx = end.x - start.x;
            double dy = end.y - start.y;

            Matrix M = TransformMatrices.translate(dx, dy, 0);
            currentTransformation = M;
        } else if (Math.abs(xIndex - yIndex) == 1) { // scale
            double sf = endVector.magnitude() / startVector.magnitude();
            Matrix S = TransformMatrices.scale(sf, sf, sf);
            currentTransformation = T.mult(S).mult(Tnot);
        } else { // rotate
            double angle = endVector.angle() - startVector.angle();
            Matrix R = TransformMatrices.rotate(Math.toRadians(angle), currentAxis);
            currentTransformation = T.mult(R).mult(Tnot);
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
        requestFocus();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char chosen = e.getKeyChar();
        System.out.println(e.getKeyChar());
        if (chosen == 'x') {
            currentAxis = TransformMatrices.Axis.X;
        } else if (chosen == 'y') {
            currentAxis = TransformMatrices.Axis.Y;
        } else if (chosen == 'z') {
            currentAxis = TransformMatrices.Axis.Z;
        } else if (chosen == 'c') {
            scene.ToggleClip();
            repaint();
        }
    }
}
