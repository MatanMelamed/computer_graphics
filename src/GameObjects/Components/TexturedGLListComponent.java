package GameObjects.Components;

import Core.ResourceManager;
import Core.Graphics.Graphics;
import GameObjects.GameObjectComponent;
import Models.ImageResource;

import java.util.function.Supplier;

public class TexturedGLListComponent extends GameObjectComponent {

    private Supplier<Integer> glListGenerator;
    protected int objectGLList;
    protected ImageResource imageResource;

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
