// Matan Melamed 205973613
package com.matan.studies.computergraphics.GameObjects.Prefabs;

import com.matan.studies.computergraphics.Core.Collision.BoundingSphere;
import com.matan.studies.computergraphics.Core.Collision.ColliderType;
import com.matan.studies.computergraphics.Core.Collision.CollisionHandlers.CollisionHanlder;
import com.matan.studies.computergraphics.Core.InputManager;
import com.matan.studies.computergraphics.GameObjects.Components.ColliderComponent;
import com.matan.studies.computergraphics.GameObjects.GameObject;
import com.matan.studies.computergraphics.Models.Axis;
import com.matan.studies.computergraphics.Models.Transform;
import com.matan.studies.computergraphics.Models.Vector3D;
import com.jogamp.newt.event.KeyEvent;


public class PlayerObject extends GameObject {

    public static float playerHeight = 1;
    public float speed = 3f;
    private Vector3D velocity = new Vector3D(0, 0, 0);

    public PlayerObject() {
        super("player");

        BoundingSphere collider = new BoundingSphere(GetPosition().plus(0, playerHeight / 2f, 0), 0.25f);
        CollisionHanlder hanlder = (data, other) ->
        {
            if (other.GetCollider().GetType() == ColliderType.BS) {
                return;
            }

            Vector3D CollisionPoint = data.GetClosestPointOnOtherCollider();
            float length = collider.getRadius() - Math.abs(collider.getCenter().minus(CollisionPoint).magnitude());
            Vector3D direction = velocity.normalize();
            Move(direction.multiply(-length));
        };
        ColliderComponent colliderComponent = new ColliderComponent(collider, hanlder, false);
        AddComponent(colliderComponent);
    }

    @Override
    protected void Update(float deltaTime) {
        super.Update(deltaTime);

        boolean shouldMove = false;
        velocity = Vector3D.ZERO;

        deltaTime /= 1000f;

        float speed = this.speed;

        if (InputManager.isPressed(KeyEvent.VK_CONTROL)) {
            speed = 1f;
        }

        if (InputManager.isPressed(KeyEvent.VK_W)) {
            Vector3D addition = GetDirZ().multiply(deltaTime * speed);
            addition = addition.plus(new Vector3D(0, -addition.y, 0));
            velocity = velocity.plus(addition);
            shouldMove = true;
        }
        if (InputManager.isPressed(KeyEvent.VK_S)) {
            Vector3D addition = GetDirZ().multiply(deltaTime * speed * -1f);
            addition = addition.plus(new Vector3D(0, -addition.y, 0));
            velocity = velocity.plus(addition);
            shouldMove = true;
        }
        if (InputManager.isPressed(KeyEvent.VK_A)) {
            Vector3D addition = GetDirX().multiply(deltaTime * speed * -1f);
            addition = addition.plus(new Vector3D(0, -addition.y, 0));
            velocity = velocity.plus(addition);
            shouldMove = true;
        }
        if (InputManager.isPressed(KeyEvent.VK_D)) {
            Vector3D addition = GetDirX().multiply(deltaTime * speed);
            addition = addition.plus(new Vector3D(0, -addition.y, 0));
            velocity = velocity.plus(addition);

            shouldMove = true;
        }
        if (InputManager.isPressed(KeyEvent.VK_SPACE)) {
            velocity = velocity.plus(Transform.WORLD_Y.multiply(deltaTime * speed * 1f));
            shouldMove = true;
        }
        if (InputManager.isPressed(KeyEvent.VK_SHIFT)) {
            velocity = velocity.plus(Transform.WORLD_Y.multiply(deltaTime * speed * -1f));
            shouldMove = true;
        }

        if (shouldMove) {
            Move(velocity);
        }
    }

    @Override
    public void Move(float x, float y, float z) {
        super.Move(x, y, z);
    }

    @Override
    public void Rotate(Axis axis, double angle) {

        double maxLookDownAngle = 89;
        double maxLookUpAngle = -89;

        if (axis == Axis.X) {
            double currentXAngle = GetAxisAngleX();
            if (maxLookUpAngle < currentXAngle && currentXAngle < maxLookDownAngle ||
                    currentXAngle >= maxLookDownAngle && angle < 0 ||
                    currentXAngle <= maxLookUpAngle && angle > 0) {
                super.Rotate(axis, angle);
            }

        } else if (axis == Axis.Y) {
            // straight Y axis to WORLD_Y (look straight)
            var lastAngle = GetAxisAngleX();
            if (lastAngle != 0) {
                super.Rotate(Axis.X, -lastAngle);
                super.Rotate(axis, angle);
                super.Rotate(Axis.X, lastAngle);
            } else {
                super.Rotate(axis, angle);
            }
        }
    }

    public Vector3D GetDirection() {
//        return GetPosition().plus(GetDirZ()).plus(new Vector3D(0, playerHeight / 2f, 0));
        return GetPosition().plus(GetDirZ()).plus(0, playerHeight, 0);
    }
}
