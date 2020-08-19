package GameObjects.Components;

import Core.Graphics.Graphics;
import GameObjects.GameObjectComponent;


public class MaterialComponent extends GameObjectComponent {
    float material[];

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
