// Matan Melamed 205973613
package com.matan.studies.computergraphics.GameObjects.Prefabs;

import com.matan.studies.computergraphics.GameObjects.Components.LightComponent;
import com.matan.studies.computergraphics.GameObjects.GameObject;

import java.util.concurrent.LinkedBlockingDeque;

public class LightObject extends GameObject {

    private LinkedBlockingDeque<LightComponent> lights;

    public LightObject(String name) {
        super(name);
        lights = new LinkedBlockingDeque<>();
    }

    public LightObject() {
        this("lights");
    }

    public void AddLight(LightComponent newLightComponent) {
        lights.add(newLightComponent);
        AddComponent(newLightComponent);
    }

    public void SetAllPower(float powerPrecent) {
        for (LightComponent component : lights) {
            component.SetPower(powerPrecent);
        }
    }

    public void SetAllLight(float[] ambient, float[] diffuse) {
        for (LightComponent component : lights) {
            component.SetLightColor(ambient, diffuse);
        }
    }
}
