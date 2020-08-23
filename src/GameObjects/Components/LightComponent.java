// Matan Melamed 205973613
package GameObjects.Components;

import Core.Graphics.Graphics;
import Core.Graphics.NextRenderExecutioner;
import GameObjects.GameObjectComponent;
import Models.Vector3D;


public class LightComponent extends GameObjectComponent {

    private int lightIndex;
    private float power;

    public float[] position;
    public float[] ambient;
    public float[] diffuse;
    public float spotExponent;
    public float constantAttenuation;
    public float spotAngle;
    public Vector3D direction;

    public LightComponent(int lightIndex, float[] ambient, float[] diffuse, float[] position) {
        this(lightIndex, ambient, diffuse, position, -1f, -1f, -1f, new Vector3D(0, -1, 0));
    }

    public LightComponent(int lightIndex, float[] ambient, float[] diffuse, float[] position,
                          float spotExponent, float constantAttenuation, float spotAngle, Vector3D direction) {
        this.lightIndex = lightIndex;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.position = position;
        this.power = 1f;
        this.spotExponent = spotExponent;
        this.constantAttenuation = constantAttenuation;
        this.spotAngle = spotAngle;
        this.direction = direction;
    }

    public void SetPower(float newPower) {
        this.power = newPower;
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
        NextRenderExecutioner.AddRequestToNextRender(() -> Graphics.EnableLight(lightIndex));
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
        Graphics.SetLightPosition(lightIndex, position);
        Graphics.SetLightColor(
                lightIndex,
                ambient[0] * power, ambient[1] * power, ambient[2] * power,
                diffuse[0] * power, diffuse[1] * power, diffuse[2] * power);

        if (spotAngle != -1f) {
            direction = direction.normalize();
            Graphics.SetLightDirection(lightIndex, direction, spotAngle, spotExponent, constantAttenuation);
        }

        if (isDebug) {
            Graphics.PushMatrix();
            Vector3D parentP = GetParent().GetPosition();
            Graphics.Rotate((float) -GetParent().GetAxisAngleZ(), 0, 0, 1);
            Graphics.Rotate((float) -GetParent().GetAxisAngleX(), 1, 0, 0);
            Graphics.Rotate((float) -GetParent().GetAxisAngleY(), 0, 1, 0);
            Graphics.Translate(position[0] - parentP.x, position[1] - parentP.y, position[2] - parentP.z);
            Graphics.DrawSphere(0.1f);

            if (spotAngle != -1f) {
                Graphics.Translate(direction.x, direction.y, direction.z);
                Graphics.DrawSphere(0.05f);
            }
            Graphics.PopMatrix();
        }
    }
}
