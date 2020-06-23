package Core;

import Graphics.Renderer;

import Models.Axis;
import World.WorldManager;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class InputManager implements KeyListener, MouseListener {


    public static float x = 0;
    public static float y = 0;
    public static float z = -1;
    private static float sens = 2;

    // members
    private Map<Short, Runnable> bindings;

    private double currentXAngle = Math.PI;
    private Robot robot;
    private int centerX, centerY;

    //region Singleton
    private static InputManager instance = new InputManager();

    public static InputManager getInstance() {return instance;}

    private InputManager() {
        bindings = new HashMap<>();
        SetDefaultBinding();
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    //endregion

    public static void Initialize() {
        instance.centerX = Renderer.GetWindowWidth() / 2;
        instance.centerY = Renderer.GetWindowHeight() / 2;
    }


    public static void RegisterBinding(short key, Runnable r) {
        instance.bindings.put(key, r);

    }

    public void SetDefaultBinding() {
        bindings.clear();
        bindings.put(KeyEvent.VK_ESCAPE, () -> System.exit(0));
        bindings.put(KeyEvent.VK_P, () -> {
            System.out.println(String.format("%f %f %f, current angle: %f", x, y, z, currentXAngle));
        });
    }


    @Override
    public void keyPressed(com.jogamp.newt.event.KeyEvent e) {
        var r = bindings.get(e.getKeyCode());
        if (r != null) {
            r.run();
        }
    }

    private void calculateAndSetHorizontalAxis(com.jogamp.newt.event.MouseEvent mouseEvent) {
        int deltaX = mouseEvent.getX() - centerX + 8;

        float r = Math.abs(deltaX) / (centerX * 2f) * sens;

        double angleAddition = Math.asin((r / Math.sqrt(Math.pow(r, 2) + 1)));
        //WorldManager.Player.Rotate(Axis.Y, Math.toDegrees(angleAddition * (deltaX < 0 ? -1d : 1d)) % 360);

        currentXAngle = currentXAngle + angleAddition * (deltaX < 0 ? -1d : 1d);

        x = (float) Math.sin(currentXAngle);
        z = (float) Math.cos(currentXAngle);

        currentXAngle = Math.toRadians(Math.toDegrees(currentXAngle) % 360);
    }

    private void calculateAndSetVerticalAxis(com.jogamp.newt.event.MouseEvent mouseEvent) {

        int deltaY = mouseEvent.getY() - centerY + 31;

        float r = Math.abs(deltaY) / (centerY * 2f) * sens;

        y += r * (deltaY > 0 ? 1f : -1f);

        y = Math.max(-0.5f, Math.min(0.5f, y));

    }

    private void calculateAndSetLookAtVectorValues(com.jogamp.newt.event.MouseEvent mouseEvent) {
        calculateAndSetHorizontalAxis(mouseEvent);
        calculateAndSetVerticalAxis(mouseEvent);
    }

    @Override
    public void mouseMoved(com.jogamp.newt.event.MouseEvent mouseEvent) {
        calculateAndSetLookAtVectorValues(mouseEvent);
        robot.mouseMove(centerX, centerY);
    }

    @Override
    public void keyReleased(com.jogamp.newt.event.KeyEvent keyEvent) {

    }


    @Override
    public void mouseClicked(com.jogamp.newt.event.MouseEvent mouseEvent) {
        System.out.println(String.format("(%d, %d)", mouseEvent.getX(), mouseEvent.getY()));
    }

    @Override
    public void mouseEntered(com.jogamp.newt.event.MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(com.jogamp.newt.event.MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(com.jogamp.newt.event.MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(com.jogamp.newt.event.MouseEvent mouseEvent) {

    }


    @Override
    public void mouseDragged(com.jogamp.newt.event.MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(com.jogamp.newt.event.MouseEvent mouseEvent) {

    }
}
