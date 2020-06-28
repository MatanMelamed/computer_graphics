package GameObjects;

import Graphics.Graphics;

public class PlateObject extends SingleTexturedGameObject {

    private int width;
    private int height;

    public PlateObject(String imageName, int width, int height) {
        super(imageName);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void CreateAndSetObjectGLLIst() {
        objectGLList = Graphics.Create2DTexturedPlane(width, height);
    }
}
