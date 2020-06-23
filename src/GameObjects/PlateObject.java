package GameObjects;

import Graphics.Graphics;

public class PlateObject extends GameObject {

    public PlateObject(String imageName) {
        super(imageName);
    }

    @Override
    protected void drawInPlace() {
        Graphics.Rotate(0, 1.0f, 0, 0);
        Graphics.DrawTexturePlateOnFloor(imageResource, 5f, 5f);
    }
}
