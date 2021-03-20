// Matan Melamed 205973613
package com.matan.studies.computergraphics.GameObjects.Components;

import com.matan.studies.computergraphics.Core.Graphics.Graphics;
import com.matan.studies.computergraphics.GameObjects.GameObjectComponent;


public class MaterialComponent extends GameObjectComponent {
    private float material[];

    public MaterialComponent(float[] material) {
        this.material = material;
        this.priority = 3;
    }

    @Override
    public void Initialize() {

    }

    @Override
    public void Update(float deltaTime) {

    }

    @Override
    public void Render() {
        Graphics.SetMaterial(material);
    }
}
