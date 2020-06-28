package GameObjects;

import Core.ResourceManager;
import Graphics.Graphics;
import Models.ImageResource;

public abstract class SingleTexturedGameObject extends GameObject {

    protected int objectGLList;
    protected ImageResource imageResource;

    public SingleTexturedGameObject(String imageName) {
        imageResource = ResourceManager.GetImageResourceByName(imageName);
    }

    @Override
    public void InitGameObject() {
        CreateAndSetObjectGLLIst();
    }

    @Override
    protected void drawInPlace() {
        Graphics.BindTexture(imageResource);
        Graphics.CallList(objectGLList);
    }

    protected abstract void CreateAndSetObjectGLLIst();
}
