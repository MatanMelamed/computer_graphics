package GameObjects.Components;

import Core.Graphics.Graphics;
import Core.ResourceManager;
import GameObjects.GameObjectComponent;
import Models.ImageResource;
import Models.WavefrontObjectLoader_DisplayList;

public class WavefrontComponent extends GameObjectComponent {

    private String resoursePath;
    private ImageResource imageResource = null;

    private int list;
    private WavefrontObjectLoader_DisplayList object;
    private float scaleScalar;


    public WavefrontComponent(String resourceName, String imageType, float scale) {
        this.resoursePath = "/wavefront_objs/" + resourceName + ".obj";
        if (!imageType.isEmpty()) {
            this.imageResource = ResourceManager.GetImageResourceByName(resourceName + "." + imageType);
        }
        this.list = Graphics.LoadWavefrontObject(this.resoursePath);
        this.scaleScalar = scale;
    }

    @Override
    public void Initialize() {
        object = new WavefrontObjectLoader_DisplayList(resoursePath);
    }

    @Override
    public void Update(float deltaTime) {

    }

    @Override
    public void Render() {
        Graphics.PushMatrix();
        Graphics.Scale(scaleScalar);
        if (imageResource != null) {
            Graphics.BindTexture(imageResource);
        }
        Graphics.CallList(list);
        //Graphics.DrawWavefrontObj(object);
        Graphics.PopMatrix();
    }
}
