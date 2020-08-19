package GameObjects.Prefabs;

import GameObjects.Components.LightComponent;
import GameObjects.GameObject;

import static Utils.Utils.floats;

public class WhiteLight extends GameObject {


    public WhiteLight() {
        this(7);
    }

    public WhiteLight(int index) {
        this(index, floats(0, 0, 0, 1f));
    }

    public WhiteLight(float[] position) {
        this(7, position);
    }

    public WhiteLight(int index, float[] position) {
        AddComponent(new LightComponent(
                index,
                floats(0.3f, 0.3f, 0.3f, 1f),
                floats(1f, 0f, 0f, 1f),
                position));
    }
}
