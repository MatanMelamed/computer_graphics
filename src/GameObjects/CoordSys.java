package GameObjects;

import Graphics.Graphics;
import Models.Vector3D;

public class CoordSys extends GameObject {

    public CoordSys(String imageName) {
        super(imageName);
    }

    @Override
    protected void drawInPlace() {
        //Graphics.SetColor(0.4f,0,0);
        //Graphics.DrawLine(Vector3D.ZERO,cs.DirX);

//        Graphics.SetColor(0,0.4f,0);
//        Graphics.DrawLine(Vector3D.ZERO,cs.DirY);
//
//        Graphics.SetColor(0,0,1);
//        Graphics.DrawLine(Vector3D.ZERO,cs.DirZ);
    }
}
