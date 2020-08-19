package GameObjects.Prefabs;

import Core.Collision.BoundingSphere;
import GameObjects.Components.ColliderComponent;
import GameObjects.GameObject;
import Models.Vector3D;

import java.util.ArrayList;

public class Enemy extends GameObject {

    private float speed = 5f;
    private ArrayList<Vector3D> route;
    private int currentPoint = 0;
    private Runnable onHitCallback;

    public Enemy(Runnable onHitCallback) {
        super("enemy");
        this.onHitCallback = onHitCallback;
        ColliderComponent c = new ColliderComponent(
                new BoundingSphere(new Vector3D(0, 0.5f, 0), 0.5f),
                (data, colliderComponent) -> {
                    if (colliderComponent.GetParent().name.equals("player")) {
                        if (this.onHitCallback != null) {
                            this.onHitCallback.run();
                        }
                    }
                }
                , true);
        c.debugOn = true;
        AddComponent(c);
    }

    public void SetRoute(ArrayList<Vector3D> newRoute) {
        this.route = newRoute;
    }

    @Override
    protected void Update(float deltaTime) {
        Vector3D currentPosition = GetPosition();
        if (route != null) {
            if (currentPoint == route.size()) {
                System.out.println("enemy reached end of route");
                route = null;
                return;
            }

            Vector3D direction = route.get(currentPoint).minus(currentPosition);
            direction = direction.normalize();
            deltaTime /= 1000f;
            Move(direction.x * speed * deltaTime, direction.y * speed * deltaTime, direction.z * speed * deltaTime);

            if (route.get(currentPoint).minus(currentPosition).magnitude() <= 0.05f) {
                currentPoint += 1;
            }
        }


        super.Update(deltaTime);

    }
}
