package GameObjects.Prefabs;

import Core.Graphics.Graphics;
import GameObjects.Components.TexturedGLListComponent;
import GameObjects.GameObject;

import java.util.function.Supplier;

public class PlateObject extends GameObject {

    private float width;
    private float height;
    private float tHratio;
    private float tWratio;


    public PlateObject(String imageName, float textureWidthRatio, float textureHeightRatio, float width, float height) {
        this.width = width;
        this.height = height;
        this.tWratio = textureWidthRatio;
        this.tHratio = textureHeightRatio;

        Supplier<Integer> glGenerator = () -> Graphics.Create2DTexturedPlane(width,height,tWratio,tHratio);
        TexturedGLListComponent graphics = new TexturedGLListComponent(imageName, glGenerator);
        AddComponent(graphics);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
