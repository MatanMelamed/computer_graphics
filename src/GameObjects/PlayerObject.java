package GameObjects;

import Models.Axis;
import Models.CoordinateSystem;
import Models.Vector3D;
import World.WorldManager;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;

public class PlayerObject extends GameObject {


    public PlayerObject(String imageName) {
        super(imageName);
    }

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

//            double currentZDegree = cs.GetXDiv();
//
//            if (currentZDegree < maxLookUpAngle && currentZDegree > -40||
//                    currentZDegree == maxLookUpAngle && angle < 0 ||
//                    currentZDegree == minLookDownAngle && angle > 0) {
//                super.Rotate(axis, angle);
//            }
//        }else if (axis == Axis.Y){
//            double div = cs.GetXDiv();
//
//            Vector3D dirX = new Vector3D(cs.DirX.x,0,cs.DirX.z);
//            Vector3D dirY = CoordinateSystem.WORLD_Y;
//            Vector3D dirZ = new Vector3D(cs.DirZ.x,0,cs.DirZ.z);
//
//            super.Rotate(Axis.X,-div);
//            super.Rotate(axis, angle);
//            super.Rotate(Axis.X,div);
//        }
    }

    @Override
    protected void drawInPlace() {

    }
}
