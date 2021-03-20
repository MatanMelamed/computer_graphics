// Matan Melamed 205973613
package com.matan.studies.computergraphics.GameObjects.Prefabs;

import com.matan.studies.computergraphics.Core.Graphics.Graphics;
import com.matan.studies.computergraphics.GameObjects.Components.TexturedGLListComponent;
import com.matan.studies.computergraphics.GameObjects.GameObject;

import java.util.function.Supplier;

public class PlateObject extends GameObject {

    private float width;
    private float height;
    private float tHratio;
    private float tWratio;
    private String imageName;


    public PlateObject(String name, String imageName, float textureWidthRatio, float textureHeightRatio, float width, float height) {
        super(name);
        this.width = width;
        this.height = height;
        this.tWratio = textureWidthRatio;
        this.tHratio = textureHeightRatio;
        this.imageName = imageName;
    }

    @Override
    protected void Initialize() {
        Supplier<Integer> glGenerator = () -> Graphics.Create2DTexturedPlane(width, height, tWratio, tHratio);
        TexturedGLListComponent graphics = new TexturedGLListComponent(imageName, glGenerator);
        AddComponent(graphics);
        super.Initialize();
    }
}
