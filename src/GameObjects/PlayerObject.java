package GameObjects;

import Models.Axis;

public class PlayerObject extends GameObject {


    public PlayerObject(String imageName) {
        super(imageName);
    }

    @Override
    public void Rotate(Axis axis, double angle) {

//        if (axis == Axis.X){
//            if(cs.Dir)
//        }

        super.Rotate(axis, angle);
    }

    @Override
    protected void drawInPlace() {

    }
}
