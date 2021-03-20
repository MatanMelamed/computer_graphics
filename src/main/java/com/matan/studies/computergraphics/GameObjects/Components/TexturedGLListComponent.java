// Matan Melamed 205973613
package com.matan.studies.computergraphics.GameObjects.Components;

import com.matan.studies.computergraphics.Core.ResourceManager;
import com.matan.studies.computergraphics.Core.Graphics.Graphics;
import com.matan.studies.computergraphics.GameObjects.GameObjectComponent;
import com.matan.studies.computergraphics.Models.ImageResource;

import java.util.function.Supplier;

public class TexturedGLListComponent extends GameObjectComponent {

    private Supplier<Integer> glListGenerator;
    private int objectGLList;
    private ImageResource imageResource;

    public TexturedGLListComponent(String imageName, Supplier<Integer> glListGenerator) {
        this.imageResource = ResourceManager.GetImageResourceByName(imageName);
        this.glListGenerator = glListGenerator;
    }

    @Override
    public void Initialize() {
        objectGLList = glListGenerator.get();
    }

    @Override
    public void Update(float deltaTime) {

    }

    @Override
    public void Render() {
        Graphics.BindTexture(imageResource);
        Graphics.CallList(objectGLList);
    }
}
