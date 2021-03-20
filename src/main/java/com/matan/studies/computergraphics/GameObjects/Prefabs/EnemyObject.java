// Matan Melamed 205973613
package com.matan.studies.computergraphics.GameObjects.Prefabs;

import com.matan.studies.computergraphics.Core.Collision.BoundingSphere;
import com.matan.studies.computergraphics.Core.GameManager;
import com.matan.studies.computergraphics.Core.Graphics.Graphics;
import com.matan.studies.computergraphics.GameObjects.Components.ColliderComponent;
import com.matan.studies.computergraphics.GameObjects.Components.WavefrontComponent;
import com.matan.studies.computergraphics.GameObjects.GameObject;
import com.matan.studies.computergraphics.Models.Axis;
import com.matan.studies.computergraphics.Models.Vector3D;

import java.util.ArrayList;

public class EnemyObject extends GameObject {

    private float speed = 4.1f;
    private ArrayList<Vector3D> route;
    private int currentPoint = 0;
    private Runnable onHitCallback;
    private boolean isStarted;

    private Vector3D directionToPlayer = new Vector3D();
    private Vector3D enemyDirection = new Vector3D();

    public EnemyObject(Runnable onHitCallback) {
        super("enemy");
        this.isStarted = false;
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
        AddComponent(c);

        WavefrontComponent o = new WavefrontComponent("gos", "jpg", 1f);
        AddComponent(o);

        // model is built that way
        SetDirZ(new Vector3D(0, 0, 1));
    }

    public void Start() {
        isStarted = true;
    }

    public void Reset() {
        currentPoint = 0;
        isStarted = false;
    }

    public void SetRoute(ArrayList<Vector3D> newRoute) {
        this.route = newRoute;
        currentPoint = 0;
    }

    private void RotateToPlayer() {
        Vector3D playerPosition = GameManager.GetPlayer().GetPosition();
        Vector3D enemyPosition = GetPosition();

        if (playerPosition.compareTo(enemyPosition) == 0) return;

        directionToPlayer = playerPosition.minus(enemyPosition).normalize();
        enemyDirection = GetDirZ();

        float angleToRotate = (float) Math.toDegrees(directionToPlayer.angleBetween(enemyDirection));

        if (Math.abs(angleToRotate) > 1 && !Float.isNaN(angleToRotate)) {
            Rotate(Axis.Y, angleToRotate);
        }
    }

    @Override
    protected void Update(float deltaTime) {
        RotateToPlayer();
        if (!isStarted) return;

        Vector3D currentPosition = GetPosition();
        if (route != null) {
            if (currentPoint == route.size()) {
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

    @Override
    protected void Render() {
        super.Render();
        if (isDebug) {
            Graphics.PushMatrix();
            Graphics.SetColor(1, 0, 0);

            Vector3D edp = GetPosition().plus(enemyDirection);
            Graphics.Translate(edp.x, edp.y, edp.z);
            Graphics.DrawSphere(0.2f);
            Graphics.Translate(-edp.x, -edp.y, -edp.z);
            edp = GetPosition().plus(directionToPlayer.multiply(4));
            Graphics.Translate(edp.x, edp.y, edp.z);
            Graphics.DrawSphere(0.2f);
            Graphics.SetColor(0, 0, 0);
            Graphics.PopMatrix();
        }
    }
}
