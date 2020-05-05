package Models;

import Mathematics.Matrix;
import Mathematics.ThreeDVector;

import java.awt.*;

public class ViewConverter {

    Matrix WorldToView(Point vCenter, ThreeDVector vUp) {
        ThreeDVector u = vUp.cross(new ThreeDVector(0, 0, 1));
        return new Matrix(new double[][]{
                {u.x(), u.y(), 0},
                {vUp.x(), vUp.y(), 0},
                {0, 0, 1},
        }).mult(new Matrix(new double[][]{
                {1, 0, -vCenter.x},
                {0, 1, -vCenter.y},
                {0, 0, 1},
        }));
    }
}
