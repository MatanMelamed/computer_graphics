package GameObjects;

import Graphics.Drawable;
import Graphics.Graphics;
import Models.Axis;
import Models.CoordinateSystem;
import Models.Vector3D;

public abstract class GameObject implements Drawable {

    protected CoordinateSystem cs = new CoordinateSystem();

    public GameObject() {}

    public double GetAngleX() {
        return cs.GetXDiv();
    }

    public double GetAngleY() {
        return cs.GetYDiv();
    }

    public double GetAngleZ() {
        return cs.GetZDiv();
    }

    public Vector3D GetPosition() {
        return cs.Position;
    }

    public double[] AxisAnglesFromWorld() {
        return new double[]{GetAngleX(), GetAngleY(), GetAngleZ()};
    }

    public void Move(double x, double y, double z) {
        cs.move(new Vector3D(x, y, z));
    }

    public void Rotate(Axis axis, double angle) {
        cs.rotate(axis, angle);
    }

    public void InitGameObject() {}

    @Override
    public void draw() {
        double[] angles = cs.AxisAnglesFromWorld();

        Graphics.PushMatrix();
        Graphics.Translate((float) cs.Position.x, (float) cs.Position.y, (float) cs.Position.z);
        Graphics.Rotate((float) angles[0], 1, 0, 0);
        Graphics.Rotate((float) angles[1], 0, 1, 0);
        Graphics.Rotate((float) angles[2], 0, 0, 1);
        drawInPlace();
        Graphics.PopMatrix();
    }

    protected void drawInPlace() {}

    @Override
    public String toString() {
        return String.format("GameObject :: %s", cs);
    }
}
