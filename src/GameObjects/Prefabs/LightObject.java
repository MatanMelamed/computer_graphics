package GameObjects.Prefabs;

import GameObjects.Components.LightComponent;
import GameObjects.GameObject;

public class LightObject extends GameObject {

    public LightObject(int lightIndex, float[] ambient, float[] diffuse, float[] pos) {
        super(String.format("light %d", lightIndex));

        LightComponent component = new LightComponent(lightIndex, ambient, diffuse, pos);
        AddComponent(component);
    }

}
