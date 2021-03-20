// Matan Melamed 205973613
package com.matan.studies.computergraphics.GameObjects.Prefabs;

import com.matan.studies.computergraphics.GameObjects.Components.TimeLooperComponent;
import com.matan.studies.computergraphics.GameObjects.GameObject;


public class LightLooper extends GameObject {

    private TimeLooperComponent timer;
    private LightObject lights;

    public LightLooper(TimeLooperComponent timer, LightObject lights) {
        this.timer = timer;
        this.lights = lights;
        AddComponent(timer);
        AddChild(lights);
    }

    @Override
    public void Update(float deltaTime) {
        super.Update(deltaTime);
        lights.SetAllPower(timer.relativeValue);
    }

    public void Reset() {
        timer.Reset();
    }
}
