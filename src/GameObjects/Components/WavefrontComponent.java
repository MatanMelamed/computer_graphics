// Matan Melamed 205973613
package GameObjects.Components;

import Core.Graphics.Graphics;
import Core.ResourceManager;
import GameObjects.GameObjectComponent;
import Models.ImageResource;

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
