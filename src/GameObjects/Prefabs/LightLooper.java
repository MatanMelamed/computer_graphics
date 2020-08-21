package GameObjects.Prefabs;

import GameObjects.Components.LightComponent;
import GameObjects.Components.TimeLooperComponent;
import GameObjects.GameObject;

import static Utils.Utils.floats;

public class PlayerLightLooper extends GameObject {

    private TimeLooperComponent timer;
    private LightComponent lightComponent;

    float[] ambientFull = {0.3f, 0.3f, 0.3f, 1f};
    float[] diffuseFull = {0.9f, 0.1f, 0.1f, 1f};

    public PlayerLightLooper() {
        timer = new TimeLooperComponent(3f, 1f, 1f, 1f);
        lightComponent = new LightComponent(
                0,
                floats(0.2f, 0.2f, 0.2f, 1f),
                floats(0.6f, 0.1f, .1f, 1f),
                floats(0, 0, 0, 1f));
        AddComponent(timer);
        AddComponent(lightComponent);
    }

    @Override
    public void Update(float deltaTime) {
        super.Update(deltaTime);
        float a = timer.relativeValue;
        lightComponent.ambient[0] = ambientFull[0] * a;
        lightComponent.ambient[1] = ambientFull[1] * a;
        lightComponent.ambient[2] = ambientFull[2] * a;
        lightComponent.diffuse[0] = diffuseFull[0] * a;
        lightComponent.diffuse[1] = diffuseFull[1] * a;
        lightComponent.diffuse[2] = diffuseFull[2] * a;
    }
}
