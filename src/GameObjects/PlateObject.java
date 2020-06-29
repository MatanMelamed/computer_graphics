package GameObjects;

import Graphics.Graphics;

public class PlateObject extends SingleTexturedGameObject {

    private float width;
    private float height;
    private float tHratio;
    private float tWratio;


    public PlateObject(String imageName, float textureWidthRatio, float textureHeightRatio, float width, float height) {
        super(imageName);
        this.width = width;
        this.height = height;
        this.tWratio = textureWidthRatio;
        this.tHratio = textureHeightRatio;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    @Override
    protected void CreateAndSetObjectGLLIst() {

        objectGLList = Graphics.Create2DTexturedPlane(width, height, tWratio, tHratio);
    }
}
