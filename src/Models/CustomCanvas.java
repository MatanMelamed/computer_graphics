// Matan Melamed 205973613
package Models;

import Mathematics.Matrix;
import Mathematics.Vector4D;
import Mathematics.TransformMatrices;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class CustomCanvas extends BaseMouseHandlerCanvas {

    private Scene scene;
    private View view;
    private Vector4D screenCenter;

    private Matrix totalTransformation;
    private Matrix currentTransformation;

    private TransformMatrices.Axis currentAxis;

    private Map<Character, Runnable> keyPressHandlers;

    public CustomCanvas() {
        view = new View();
        view.read("ex1.viw");

        scene = new Scene(view);
        scene.read("ex1.scn");

        currentAxis = TransformMatrices.Axis.X;
        screenCenter = new Vector4D(view.viewWidth / 2, view.viewHeight / 2, 0);
        totalTransformation = Matrix.I;
        currentTransformation = Matrix.I;

        BaseInit((int) view.viewWidth + 40, (int) view.viewHeight + 40);
        InitKeyPressHandlers();
    }

    private void InitKeyPressHandlers() {
        keyPressHandlers = new HashMap<>();
        keyPressHandlers.put('x', () -> currentAxis = TransformMatrices.Axis.X);
        keyPressHandlers.put('y', () -> currentAxis = TransformMatrices.Axis.Y);
        keyPressHandlers.put('z', () -> currentAxis = TransformMatrices.Axis.Z);
        keyPressHandlers.put('c', () -> {
            scene.ToggleClip();
            repaint();
        });
        keyPressHandlers.put('r', () -> {
            currentTransformation = Matrix.I;
            totalTransformation = Matrix.I;
            repaint();
        });
        keyPressHandlers.put('l', () -> {
            view.read("ex1.viw");
            scene.read("ex1.scn");
            repaint();
        });
        keyPressHandlers.put('q', () -> System.exit(0));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Matrix finalTransformation = view.VM2
                .mult(Matrix.I)
                .mult(currentTransformation)
                .mult(totalTransformation)
                .mult(view.VM1);
        scene.draw(g, finalTransformation);
        g.drawRect(20, 20, (int) view.viewWidth, (int) view.viewHeight);
    }

    void applyMouseController(int xIndex, int yIndex) {

        Vector4D startVector = new Vector4D(start.x, start.y).minus(screenCenter);
        Vector4D endVector = new Vector4D(end.x, end.y).minus(screenCenter);

        Matrix T = TransformMatrices.translate(0, 0, -view.position.minus(view.lookAt).z());
        Matrix Tnot = TransformMatrices.translate(0, 0, view.position.minus(view.lookAt).z());

        if (xIndex == 1 && yIndex == 1) { // translate
            double windowWidth = view.window.y() - view.window.x();
            double windowHeight = view.window.w() - view.window.z();
            double dx = (end.x - start.x) * windowWidth / view.viewWidth;
            double dy = -1d * (end.y - start.y) * (windowHeight / view.viewWidth);

            currentTransformation = TransformMatrices.translate(dx, dy, 1);
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
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char chosen = e.getKeyChar();
        if (keyPressHandlers.containsKey(chosen)) {
            keyPressHandlers.get(chosen).run();
        }
    }
}
