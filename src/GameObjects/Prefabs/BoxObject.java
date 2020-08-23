// Matan Melamed 205973613
package GameObjects.Prefabs;

import Core.Collision.AxisAlignedBoundingBox;
import GameObjects.Components.ColliderComponent;
import GameObjects.GameObject;
import GameObjects.Components.TexturedGLListComponent;
import Core.Graphics.Graphics;
import Models.Vector3D;

import java.util.function.Supplier;


public class BoxObject extends GameObject {

    public BoxObject(String name, float width, float height, float depth, String image) {
        super(name);

        Supplier<Integer> glGenerator = () -> Graphics.Create3DTexturedRectangle(width, height, depth, 1, 1);
        TexturedGLListComponent graphics = new TexturedGLListComponent(image, glGenerator);
        AddComponent(graphics);

        Vector3D minPoint = new Vector3D(-width / 2f, -height / 2f, -depth / 2f);
        AxisAlignedBoundingBox collider = new AxisAlignedBoundingBox(minPoint, minPoint.multiply(-1f));
        ColliderComponent colliderComponent = new ColliderComponent(collider, true);
        AddComponent(colliderComponent);
    }

    @Override
    public void InitializeAll() {
        super.InitializeAll();
    }
}
