package GameObjects.Prefabs;

import Core.Collision.BoundingSphere;
import GameObjects.Components.ColliderComponent;
import GameObjects.GameObject;
import GameObjects.Components.TexturedGLListComponent;
import Core.Graphics.Graphics;

import java.util.function.Supplier;

public class BoxObject extends GameObject {
    private float width, height, depth;

    public BoxObject(float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;

        Supplier<Integer> glGenerator = () -> Graphics.Create3DTexturedRectangle(width, height, depth);
        TexturedGLListComponent graphics = new TexturedGLListComponent("wood_box.jpg", glGenerator);
        AddComponent(graphics);

        ColliderComponent colliderComponent = new ColliderComponent(new BoundingSphere(GetPosition(), 1));
        AddComponent(colliderComponent);
    }

    @Override
    public void InitializeAll() {
        super.InitializeAll();
    }
}
