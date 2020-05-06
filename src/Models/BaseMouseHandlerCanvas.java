// Matan Melamed 205973613
package Models;

import java.awt.*;
import java.awt.event.*;

public abstract class BaseMouseHandlerCanvas extends Canvas implements MouseListener, MouseMotionListener, KeyListener {

    int width, height;
    Point start, end;
    boolean shouldPaintMouseLine = false;

    BaseMouseHandlerCanvas() { }

    void BaseInit(int w, int h) {
        width = w;
        height = h;
        setSize(w, h);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        g.setColor(Color.blue);

        if (shouldPaintMouseLine) {
            g.drawLine(start.x, start.y, end.x, end.y);
        }
    }


    int[] pointToSquareCoordinate(Point point) {
        return new int[]{point.y / (height / 3), point.x / (width / 3)};
    }

    protected abstract void DragEvent();

    protected abstract void ReleaseEvent();

    @Override
    public void mousePressed(MouseEvent e) {
        start = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        end = e.getPoint();
        shouldPaintMouseLine = true;
        this.repaint();
        ReleaseEvent();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        end = e.getPoint();
        shouldPaintMouseLine = true;
        this.repaint();
        DragEvent();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
