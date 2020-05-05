package Utils;

import java.io.File;
import java.util.Scanner;

public class ScnParser {

    private int numberOfVertices;
    private int[][] vertices;
    private int numberOfEdges;
    private int[][] edges;

    private Scanner OpenScanner(String fileName) {
        Scanner scanner = null;
        try {
            File file = new File(fileName);
            scanner = new Scanner(file);
        } catch (Exception e) {
            System.out.println("Error\n" + e.getMessage());
            e.printStackTrace();
        }

        return scanner;
    }

    public void parse(String fileName) {
        Scanner scanner = OpenScanner(fileName);
        if (scanner == null) {return;}

        numberOfVertices = Integer.parseInt(scanner.nextLine());
        vertices = new int[numberOfVertices][2];

        for (int i = 0; i < numberOfVertices; i++) {
            String[] vertex = scanner.nextLine().split(" ");
            vertices[i][0] = Integer.parseInt(vertex[0]);
            vertices[i][1] = Integer.parseInt(vertex[1]);
        }

        numberOfEdges = Integer.parseInt(scanner.nextLine());
        edges = new int[numberOfEdges][2];

        for (int i = 0; i < numberOfEdges; i++) {
            String[] vertex = scanner.nextLine().split(" ");
            edges[i][0] = Integer.parseInt(vertex[0]);
            edges[i][1] = Integer.parseInt(vertex[1]);
        }
    }

    public void print() {
        System.out.printf("%d Vertices\n", numberOfVertices);
        for (int i = 0; i < numberOfVertices; i++) {
            System.out.printf("X: %d, Y: %d\n", vertices[i][0], vertices[i][1]);
        }
        System.out.printf("%d Edges\n", numberOfEdges);
        for (int i = 0; i < numberOfEdges; i++) {
            System.out.printf("from: %d, to: %d\n", edges[i][0], edges[i][1]);
        }
    }


}
