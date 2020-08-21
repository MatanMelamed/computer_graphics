package World;

import Core.Collision.AxisAlignedBoundingBox;
import GameObjects.Components.ColliderComponent;
import GameObjects.Components.LightComponent;
import GameObjects.GameObject;
import GameObjects.Prefabs.LightObject;
import GameObjects.Prefabs.PlateObject;
import Models.Axis;
import Models.Vector3D;

import java.util.ArrayList;

import static Utils.Utils.floats;

public class Workspace extends BaseLevel {
    @Override
    public float[] GetSpawnPoint() {
        return new float[]{0, 0, 0};
    }

    @Override
    public float[] GetFinishRectangle() {
        return new float[]{5, 0, 5, 10, 0, 10};
    }

    @Override
    public float[] GetEnemySpawn() {
        return new float[0];
    }

    @Override
    public GameObject GetBarrier() {
        return null;
    }

    @Override
    protected void InitializeEnemiesRoute() {
        enemiesRoute = new ArrayList<>();
    }

    public Workspace() {
        GameObject room = CreateStartRoom(50, 50, 10);
        AddGameObject(room);
        CreateAxisMarkers();

        LightObject o = new LightObject();
        o.AddLight(new LightComponent(
                0,
                floats(0.5f, 0.5f, 0.5f, 1f),
                floats(0.5f, 0.5f, 0.5f, 1f),
                floats(0f, -1f, 0f, 0f)));
        AddGameObject(o);
    }

    private GameObject CreateStartRoom(float width, float depth, float height) {
        GameObject room = new GameObject("startRoom");

        float smallValue = 0.01f;
        AxisAlignedBoundingBox collider;
        ColliderComponent colliderComponent;

        PlateObject plate = new PlateObject("floor", "square_floor.png", width, depth, width, depth);
        room.AddChild(plate);

        // left wall (X,0,0)
        plate = new PlateObject("wall", "concc.jpg", depth / 4, 1, depth, height);
        plate.Rotate(Axis.X, -90);
        plate.Rotate(Axis.Z, 90);

        plate.Move(width / 2f, height / 2f, 0);

        collider = new AxisAlignedBoundingBox(
                new Vector3D(-smallValue + (width / 2f), 0, -depth / 2f),
                new Vector3D(smallValue + (width / 2f), height, depth / 2f));
        colliderComponent = new ColliderComponent(collider);
        plate.AddComponent(colliderComponent);
        room.AddChild(plate);

        // right wall
        plate = new PlateObject("wall", "concc.jpg", depth / 4, 1, depth, height);
        plate.Rotate(Axis.X, -90);
        plate.Rotate(Axis.Z, -90);

        plate.Move(-width / 2f, height / 2f, 0);
        collider = new AxisAlignedBoundingBox(
                new Vector3D(-smallValue - (width / 2f), 0, -depth / 2f),
                new Vector3D(smallValue - (width / 2f), height, depth / 2f));
        colliderComponent = new ColliderComponent(collider);
        plate.AddComponent(colliderComponent);

        room.AddChild(plate);

        // back wall
        plate = new PlateObject("wall", "concc.jpg", depth / 4, 1, width, height);
        plate.Rotate(Axis.X, -90);
        plate.Rotate(Axis.Z, 180);

        plate.Move(0, height / 2f, -depth / 2f);
        collider = new AxisAlignedBoundingBox(
                new Vector3D(-width / 2f, 0, -smallValue - (depth / 2f)),
                new Vector3D(width / 2f, height, smallValue - (depth / 2f)));
        colliderComponent = new ColliderComponent(collider);
        plate.AddComponent(colliderComponent);
        room.AddChild(plate);

        // front
        plate = new PlateObject("wall", "concc.jpg", depth / 4, 1, width, height);
        plate.Rotate(Axis.X, -90);

        plate.Move(0, height / 2f, depth / 2f);
        collider = new AxisAlignedBoundingBox(
                new Vector3D(-width / 2f, 0, -smallValue - (depth / 2f)),
                new Vector3D(width / 2f, height, smallValue - (depth / 2f)));
        colliderComponent = new ColliderComponent(collider);
        plate.AddComponent(colliderComponent);
        room.AddChild(plate);

        return room;
    }


}
