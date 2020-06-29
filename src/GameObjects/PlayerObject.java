package GameObjects;

import Models.Axis;
import Models.CoordinateSystem;
import Models.Vector3D;

public class PlayerObject extends GameObject {

    public static int Height = 1;

    public PlayerObject() { }

    @Override
    public void Rotate(Axis axis, double angle) {

        double maxLookDownAngle = 50;
        double maxLookUpAngle = -50;

        if (axis == Axis.X) {
            double currentXAngle = cs.GetXDiv();
            if (maxLookUpAngle < currentXAngle && currentXAngle < maxLookDownAngle ||
                    currentXAngle >= maxLookDownAngle && angle < 0 ||
                    currentXAngle <= maxLookUpAngle && angle > 0) {
                super.Rotate(axis, angle);
            }

        } else if (axis == Axis.Y) {
            // straight Y axis to WORLD_Y (look straight)
            var lastAngle = cs.GetXDiv();
            if (lastAngle != 0) {
                super.Rotate(Axis.X, -lastAngle);
                super.Rotate(axis, angle);
                super.Rotate(Axis.X, lastAngle);
            } else {
                super.Rotate(axis, angle);
            }
        }
    }

    public Vector3D GetDirection() {
        return cs.Position.plus(cs.DirZ).plus(new Vector3D(0, Height, 0));
    }

    @Override
    protected void drawInPlace() {

    }
}
