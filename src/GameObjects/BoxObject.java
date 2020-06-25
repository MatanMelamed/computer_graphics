package GameObjects;

import Graphics.Graphics;

public class BoxObject extends GameObject {

    private float width, height, depth;

    public BoxObject(String imageName, float width, float height, float depth) {
        super(imageName);
        this.width = width;
        this.height = height;
        this.depth = depth;
        cs.SetPosition(0, 1, 0);
    }

    @Override
    protected void drawInPlace() {
        //Graphics.Rotate(45, 1.0f, 0, 0);
        Graphics.DrawTexturedRectangleInCenter(imageResource, width,  height, depth);
        // Graphics.oldDrawTexturedRectangle(imageResource, width, height, depth);
        //Graphics.Rotate(0, -5, 0, 0);
    }
}