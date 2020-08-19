package GameObjects.Components;

import Core.Graphics.Debugger;
import Core.Graphics.Graphics;
import GameObjects.GameObjectComponent;
import Models.Vector3D;


public class LightComponent extends GameObjectComponent {

    public float[] position;
    public int lightIndex;
    public float[] ambient;
    public float[] diffuse;

    public LightComponent(int lightIndex, float[] ambient, float[] diffuse, float[] position) {
        this.lightIndex = lightIndex;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.position = position;
        Debugger.AddDebug(() -> String.format("light %d at %.2f %.2f %.2f", lightIndex, position[0], position[1], position[2]));
    }

    public void SetLightColor(float[] ambient, float[] diffuse) {
        this.ambient = ambient;
        this.diffuse = diffuse;
    }

    @Override
    public void Disable() {
        super.Disable();
        Graphics.DisableLight(lightIndex);
    }

    @Override
    public void Enable() {
        super.Enable();
    }

    @Override
    public void ParentMoved(float x, float y, float z) {
        super.ParentMoved(x, y, z);
        position[0] += x;
        position[1] += y;
        position[2] += z;
    }

    @Override
    public void Initialize() {
        if (isEnabled) {
            Graphics.EnableLight(lightIndex);
            Graphics.SetLightPosition(lightIndex, position);
        }
    }

    @Override
    public void Update(float deltaTime) {

    }

    @Override
    public void Render() {
        Graphics.PushMatrix();
        Vector3D parentP = GetParent().GetPosition();
        Graphics.Rotate((float) -GetParent().GetAxisAngleZ(), 0, 0, 1);
        Graphics.Rotate((float) -GetParent().GetAxisAngleX(), 1, 0, 0);
        Graphics.Rotate((float) -GetParent().GetAxisAngleY(), 0, 1, 0);
        Graphics.Translate(position[0] - parentP.x, position[1] - parentP.y, position[2] - parentP.z);
        Graphics.DrawSphere(0.1f);
        Graphics.SetLightPosition(lightIndex, position);
        Graphics.SetLightColor(lightIndex, ambient, diffuse);
        Graphics.PopMatrix();
    }

}
