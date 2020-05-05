import Mathematics.ThreeDVector;
import Models.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Main {

    private static void Run() {


        Frame frame = new Frame("Exercise1");
        CustomCanvas canvas = new CustomCanvas(new View());
        frame.add(canvas);

        WindowAdapter myWindowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        frame.addWindowListener(myWindowAdapter);
        frame.pack();
        frame.setVisible(true);
    }

    public static CanvasForTries Try() {

        Frame frame = new Frame("Exercise1");
        CanvasForTries canvas = new CanvasForTries(800, 800);
        frame.add(canvas);

        WindowAdapter myWindowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        frame.addWindowListener(myWindowAdapter);
        frame.pack();
        frame.setVisible(true);

        return canvas;
    }

    public static void main(String[] args) {

        Run();

    }


}
