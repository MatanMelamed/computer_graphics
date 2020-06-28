package GameObjects;

import Graphics.Graphics;

public class BoxObject extends SingleTexturedGameObject {

    private float width, height, depth;

    public BoxObject(String imageName, float width, float height, float depth) {
        super(imageName);
        this.width = width;
        this.height = height;
        this.depth = depth;
        cs.SetPosition(0, 1, 0);
    }


    @Override
    protected void CreateAndSetObjectGLLIst() {
        objectGLList = Graphics.Create3DTexturedRectangle(width,  height, depth);
    }

}