package Models;

import Mathematics.Matrix;
import Mathematics.Vector4D;

import java.awt.*;

public class ViewConverter {

    Matrix WorldToView(Point vCenter, Vector4D vUp) {
        Vector4D u = vUp.cross(new Vector4D(0, 0, 1));
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
