// Matan Melamed 205973613
package com.matan.studies.computergraphics.Core;

import com.matan.studies.computergraphics.Core.Graphics.WindowManager;

import com.matan.studies.computergraphics.Models.Axis;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class InputManager implements KeyListener, MouseListener {

    private static float sens = 2;

    // members
    private Map<Short, Runnable> bindings;
    private HashMap<Short, Boolean> isPressed = new HashMap<>();
    private volatile boolean shouldRotatePlayer = true;

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

    private void calculateAndSetHorizontalAxis(com.jogamp.newt.event.MouseEvent mouseEvent) {
        int deltaX = mouseEvent.getX() - centerX + 8;

        float r = Math.abs(deltaX) / (centerX * 2f) * sens;

        double angleAddition = Math.asin((r / Math.sqrt(Math.pow(r, 2) + 1)));

        GameManager.GetPlayer().Rotate(Axis.Y, Math.toDegrees(angleAddition * (deltaX > 0 ? 1d : -1d)) % 360);
    }

    private void calculateAndSetVerticalAxis(com.jogamp.newt.event.MouseEvent mouseEvent) {
        int deltaY = mouseEvent.getY() - centerY + 31;

        float r = Math.abs(deltaY) / (centerY * 2f) * sens;

        double angleAddition = Math.asin((r / Math.sqrt(Math.pow(r, 2) + 1)));

        GameManager.GetPlayer().Rotate(Axis.X, -Math.toDegrees(angleAddition * (deltaY > 0 ? 1d : -1d)) % 360);
    }

    private void calculateAndSetLookAtVectorValues(com.jogamp.newt.event.MouseEvent mouseEvent) {
        calculateAndSetHorizontalAxis(mouseEvent);
        calculateAndSetVerticalAxis(mouseEvent);
    }

    static void TurnOnMouseRotatePlayer() {instance.shouldRotatePlayer = true;}

    static void TurnOffMouseRotatePlayer() {instance.shouldRotatePlayer = false;}


    public static boolean isPressed(Short key) {
        return instance.isPressed.get(key) != null ? instance.isPressed.get(key) : false;
    }

    public static void Initialize() {
        instance.centerX = WindowManager.GetWindowWidth() / 2;
        instance.centerY = WindowManager.GetWindowHeight() / 2;
    }


    public static void RegisterBinding(short key, Runnable r) {
        instance.bindings.put(key, r);
    }

    private void SetDefaultBinding() {
        bindings.clear();
        bindings.put(KeyEvent.VK_ESCAPE, () -> System.exit(0));
    }


    @Override
    public void keyPressed(KeyEvent e) {
        isPressed.put(e.getKeyCode(), true);
        var r = bindings.get(e.getKeyCode());
        if (r != null) {
            r.run();
        }
    }

    @Override
    public void mouseMoved(com.jogamp.newt.event.MouseEvent mouseEvent) {
        if (shouldRotatePlayer) {
            if (mouseEvent.getX() - centerX < 100 && mouseEvent.getY() - centerY < 100) {
                calculateAndSetLookAtVectorValues(mouseEvent);
            }
            robot.mouseMove(centerX, centerY);
        }
    }

    @Override
    public void keyReleased(com.jogamp.newt.event.KeyEvent keyEvent) {
        isPressed.put(keyEvent.getKeyCode(), false);
    }

    @Override
    public void mouseClicked(com.jogamp.newt.event.MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(com.jogamp.newt.event.MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(com.jogamp.newt.event.MouseEvent mouseEvent) {}

    @Override
    public void mousePressed(com.jogamp.newt.event.MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(com.jogamp.newt.event.MouseEvent mouseEvent) {}

    @Override
    public void mouseDragged(com.jogamp.newt.event.MouseEvent mouseEvent) {}

    @Override
    public void mouseWheelMoved(com.jogamp.newt.event.MouseEvent mouseEvent) {}
}
