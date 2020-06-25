package GameObjects;

import Graphics.Graphics;

public class PlateObject extends GameObject {

    int width;
    int height;

    public PlateObject(String imageName, int width, int height) {
        super(imageName);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void drawInPlace() {
        Graphics.DrawTexturePlateOnFloorInCenter(imageResource, width, height);
    }
}
