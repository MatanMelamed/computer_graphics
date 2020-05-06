// Matan Melamed 205973613
package Models;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Mathematics.Matrix;
import Mathematics.Vector4D;


class Scene {

    private List<Vector4D> vertices;
    private List<Point> edgesByVerticesIndexes;

    private Clipper clipper;
    private View view;
    private volatile boolean shouldClip = false;

    Scene(View newView) {
        vertices = new ArrayList<>();
        edgesByVerticesIndexes = new ArrayList<>();
        view = newView;
        clipper = new Clipper(view);
    }

    private void ParseFile(String fileName) {
        Scanner scanner = null;
        try {
            File file = new File(fileName);
            scanner = new Scanner(file);
        } catch (Exception e) {
            System.out.println("Error\n" + e.getMessage());
            e.printStackTrace();
        }
        if (scanner == null) {return;}

        int numberOfVertices = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numberOfVertices; i++) {
            String[] vertex = scanner.nextLine().split(" ");
            vertices.add(new Vector4D(
                    Double.parseDouble(vertex[0]),
                    Double.parseDouble(vertex[1]),
                    Double.parseDouble(vertex[2]),
                    1
            ));
        }

        int numberOfEdges = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numberOfEdges; i++) {
            String[] edge = scanner.nextLine().split(" ");
            edgesByVerticesIndexes.add(new Point(
                    Integer.parseInt(edge[0]),
                    Integer.parseInt(edge[1])));
        }
    }

    void read(String fileName) {
        vertices.clear();
        edgesByVerticesIndexes.clear();
        ParseFile(fileName);
    }

    void ToggleClip() {
        if (shouldClip) {
            shouldClip = false;
        } else {
            shouldClip = true;
        }
    }

    void draw(Graphics g, Matrix applier) {
        Vector4D start, end;
        for (Point index : edgesByVerticesIndexes) {
            start = applier.multiply(vertices.get(index.x));
            end = applier.multiply(vertices.get(index.y));

            if (shouldClip) {
                if (!view.IsLineCrossView(start, end)) {
                    continue;
                }
                Vector4D[] newEdges = clipper.ClipEdge(start, end);
                start = newEdges[0];
                end = newEdges[1];
            }

            g.drawLine((int) start.x(), (int) start.y(), (int) end.x(), (int) end.y());
        }
    }
}
