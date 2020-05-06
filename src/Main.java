// Matan Melamed 205973613
import Models.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Main {

    private static void Run() {

        Frame frame = new Frame("Exercise1");
        CustomCanvas canvas = new CustomCanvas();
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

    public static void main(String[] args) {
        Run();
    }
}
