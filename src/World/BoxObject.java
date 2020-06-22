package World;

import Graphics.Graphics;

public class BoxObject extends GameObject {

    public BoxObject(String imageName) {
        super(imageName);
        cs.SetPosition(0, 1, 0);
    }

    @Override
    protected void drawInPlace() {
        //Graphics.Rotate(45, 1.0f, 0, 0);
        Graphics.drawTexturedRectangle(imageResource, 2f, 1f, 3f);
        //Graphics.Rotate(0, -5, 0, 0);
    }
}