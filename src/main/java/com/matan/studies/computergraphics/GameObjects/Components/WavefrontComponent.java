// Matan Melamed 205973613
package com.matan.studies.computergraphics.GameObjects.Components;

import com.matan.studies.computergraphics.Core.Graphics.Graphics;
import com.matan.studies.computergraphics.Core.ResourceManager;
import com.matan.studies.computergraphics.GameObjects.GameObjectComponent;
import com.matan.studies.computergraphics.Models.ImageResource;

public class WavefrontComponent extends GameObjectComponent {

    private String resourcePath;
    private ImageResource imageResource = null;

    private int list;
    private float scaleScalar;


    public WavefrontComponent(String resourceName, String imageType, float scale) {
        this.resourcePath = "/wavefront_objs/" + resourceName + ".obj";
        if (!imageType.isEmpty()) {
            this.imageResource = ResourceManager.GetImageResourceByName(resourceName + "." + imageType);
        }
        this.scaleScalar = scale;
    }

    @Override
    public void Initialize() {
        this.list = Graphics.LoadWavefrontObject(this.resourcePath);
    }

    @Override
    public void Update(float deltaTime) {}

    @Override
    public void Render() {
        Graphics.PushMatrix();
        Graphics.Scale(scaleScalar);
        if (imageResource != null) {
            Graphics.BindTexture(imageResource);
        }
        Graphics.CallList(list);
        Graphics.PopMatrix();
    }
}
