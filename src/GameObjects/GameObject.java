package GameObjects;

import Core.ResourceManager;
import Graphics.Drawable;
import Graphics.Graphics;
import Models.Axis;
import Models.CoordinateSystem;
import Models.ImageResource;
import Models.Vector3D;

public abstract class GameObject implements Drawable {

    protected CoordinateSystem cs;
    protected ImageResource imageResource;

    public GameObject(String imageName) {
        cs = new CoordinateSystem(new Vector3D(0, 0, 0));
        imageResource = ResourceManager.GetImageResourceByName(imageName);
    }

    public void Rotate(Axis axis, double angle) {
        cs.rotate(axis, angle);
    }

    public void SetPosition(float x, float y, float z) {
        cs.SetPosition(x, y, z);
    }

    @Override
    public CoordinateSystem getCoordinateSystem() {
        return cs;
    }

    @Override
    public final void draw() {
        Graphics.Translate((float) -cs.position.x, (float) cs.position.y, (float) -cs.position.z);
        drawInPlace();
        Graphics.Translate((float) cs.position.x, (float) -cs.position.y, (float) cs.position.z);
    }

    protected abstract void drawInPlace();
}
