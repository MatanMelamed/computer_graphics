package Models;

import Mathematics.ThreeDVector;

public class Clipper {

    private ThreeDVector[] screenPoints;
    private ThreeDVector[] normals;


    public Clipper(View view) {
        double xMin = 20, yMin = 20;
        double xMax = (int) view.viewWidth + xMin;
        double yMax = (int) view.viewHeight + yMin;
        screenPoints = new ThreeDVector[]{
                new ThreeDVector(xMin, yMin, 0),
                new ThreeDVector(xMax, yMin, 0),
                new ThreeDVector(xMax, yMax, 0),
                new ThreeDVector(xMin, yMax, 0),

        };

        int n = screenPoints.length;
        normals = new ThreeDVector[4];
        for (int i = 0; i < n; i++) {
            normals[i] = new ThreeDVector(
                    screenPoints[i].y() - screenPoints[(i + 1) % n].y(),
                    screenPoints[(i + 1) % n].x() - screenPoints[i].x());
        }

    }

    public ThreeDVector[] ClipEdge(ThreeDVector p0, ThreeDVector p1) {
        ThreeDVector[] result = new ThreeDVector[2];

        ThreeDVector P1_P0 = p1.minus(p0);
        ThreeDVector[] P0_PEi = new ThreeDVector[4];
        for (int i = 0; i < screenPoints.length; i++) {
            P0_PEi[i] = screenPoints[i].minus(p0);
        }

        double maxTe = 0;
        double minTl = 1;

        double numerator, denominator, t;

        for (int i = 0; i < screenPoints.length; i++) {
            numerator = normals[i].dot(P0_PEi[i]);
            denominator = normals[i].dot(P1_P0);
            t = numerator / denominator;
            if (t < 0 || t > 1) {
                continue;
            }
            if (denominator > 0) {
                if (t > maxTe) {
                    maxTe = t;
                }
            } else {
                if (t < minTl) {
                    minTl = t;
                }
            }
        }

        if (maxTe > minTl) {
            result[0] = new ThreeDVector(-1, -1);
            result[1] = new ThreeDVector(-1, -1);
        } else {
            result[0] = new ThreeDVector(
                    p0.x() + P1_P0.x() * maxTe,
                    p0.y() + P1_P0.y() * maxTe
            );
            result[1] = new ThreeDVector(
                    p0.x() + P1_P0.x() * minTl,
                    p0.y() + P1_P0.y() * minTl
            );
        }

        return result;
    }
}
