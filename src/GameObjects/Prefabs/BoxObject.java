package GameObjects.Prefabs;

import GameObjects.Components.MaterialComponent;
import GameObjects.GameObject;
import GameObjects.Components.TexturedGLListComponent;
import Core.Graphics.Graphics;

import java.util.function.Supplier;

import static Utils.Utils.floats;

public class BoxObject extends GameObject {
    private float width, height, depth;

    public BoxObject(String name, float width, float height, float depth, String image) {
        super(name);
        this.width = width;
        this.height = height;
        this.depth = depth;

        Supplier<Integer> glGenerator = () -> Graphics.Create3DTexturedRectangle(width, height, depth, 1, 1);
        TexturedGLListComponent graphics = new TexturedGLListComponent(image, glGenerator);
        AddComponent(graphics);
//        AddComponent(new MaterialComponent(floats(0.9f, 0.9f, 0.9f, 1f)));
    }

    @Override
    public void InitializeAll() {
        super.InitializeAll();
    }
}
