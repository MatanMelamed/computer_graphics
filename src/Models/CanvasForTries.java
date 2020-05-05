package Models;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CanvasForTries extends BaseMouseHandlerCanvas {


    List<Line> lines = new ArrayList<>();

    public CanvasForTries(int w, int h) {
        super(w, h);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Line l : lines) {
            g.drawLine(l.x, l.y, l.w, l.h);
        }
    }

    public void addLine(Line l) {
        lines.add(l);
    }


    @Override
    protected void DragEvent() {

    }

    @Override
    protected void ReleaseEvent() {

    }
}
