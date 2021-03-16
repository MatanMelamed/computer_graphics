package World;

import Core.Collision.AxisAlignedBoundingBox;
import Core.Graphics.Debugger;
import GameObjects.Components.ColliderComponent;
import GameObjects.Components.MaterialComponent;
import GameObjects.GameObject;
import GameObjects.Prefabs.BallObject;
import GameObjects.Prefabs.BoxObject;
import GameObjects.Prefabs.ControllableObject;
import GameObjects.Prefabs.PlateObject;
import Models.Axis;
import Models.Vector3D;


public class World1 extends BaseWorld {

    public World1() {
        createAxisMarkers();
        CreateRoom(100, 100, 5);
    }

    private void createAxisMarkers() {
        BoxObject boxObject = new BoxObject("red box", 1, 1, 1, "red.png");
        boxObject.Move(10, 15, 0);
        AddGameObject(boxObject);
        boxObject = new BoxObject("yellow box", 1, 1, 1, "yellow.png");
        boxObject.Move(0, 15, 10);
        AddGameObject(boxObject);
        boxObject = new BoxObject("green box", 1, 1, 1, "green.png");
        boxObject.Move(0, 15, 0);
        AddGameObject(boxObject);
    }

    private void CreateRoom(int width, int depth, int height) {
        GameObject room = new GameObject();
        float smallValue = 0.01f;
        AxisAlignedBoundingBox collider;
        ColliderComponent colliderComponent;
        // floor
        PlateObject plate = new PlateObject("floor", "square_floor.png", width, depth, width, depth);
//        plate.AddComponent(new MaterialComponent(new float[]{0.2f, 0.2f, 0.2f, 1.0f}));

        room.AddChild(plate);

        // left wall (X,0,0)
        plate = new PlateObject("wall", "wall.jpg", depth / 4, 1, depth, height);
        plate.Rotate(Axis.X, -90);
        plate.Rotate(Axis.Z, 90);

        plate.Move(width / 2f, height / 2f, 0);
        collider = new AxisAlignedBoundingBox(
                new Vector3D(-smallValue + (width / 2f), 0, -depth / 2f),
                new Vector3D(smallValue + (width / 2f), height, depth / 2f));
        colliderComponent = new ColliderComponent(collider);
        plate.AddComponent(colliderComponent);
//        plate.AddComponent(new MaterialComponent(new float[]{0.8f, 0.8f, 0.8f, 1.0f}));
        room.AddChild(plate);

        // right wall
        plate = new PlateObject("wall", "wall.jpg", depth / 4, 1, depth, height);
        plate.Rotate(Axis.X, -90);
        plate.Rotate(Axis.Z, -90);

        plate.Move(-width / 2f, height / 2f, 0);
        collider = new AxisAlignedBoundingBox(
                new Vector3D(-smallValue - (width / 2f), 0, -depth / 2f),
                new Vector3D(smallValue - (width / 2f), height, depth / 2f));
        colliderComponent = new ColliderComponent(collider);
        plate.AddComponent(colliderComponent);
//        plate.AddComponent(new MaterialComponent(new float[]{0.8f, 0.8f, 0.8f, 1.0f}));

        room.AddChild(plate);

        // back wall
        plate = new PlateObject("wall", "wall.jpg", depth / 4, 1, width, height);
        plate.Rotate(Axis.X, -90);
        plate.Rotate(Axis.Z, 180);

        plate.Move(0, height / 2f, -depth / 2f);
        collider = new AxisAlignedBoundingBox(
                new Vector3D(-width / 2f, 0, -smallValue - (depth / 2f)),
                new Vector3D(width / 2f, height, smallValue - (depth / 2f)));
        colliderComponent = new ColliderComponent(collider);
        plate.AddComponent(colliderComponent);
        room.AddChild(plate);

        // front wall
        plate = new PlateObject("wall", "wall.jpg", depth / 4, 1, width, height);
        plate.Rotate(Axis.X, -90);
        plate.Move(0, height / 2f, depth / 2f);
        collider = new AxisAlignedBoundingBox(
                new Vector3D(-width / 2f, 0, -smallValue + (depth / 2f)),
                new Vector3D(width / 2f, height, smallValue + (depth / 2f)));
        colliderComponent = new ColliderComponent(collider);
        plate.AddComponent(colliderComponent);
        room.AddChild(plate);

        // sky
        plate = new PlateObject("sky", "sky.jpg", 1, 1, 500, 500);
        plate.Move(0, 50, 0);
        plate.Rotate(Axis.X, 180);
        room.AddChild(plate);

        AddGameObject(room);

        room.MoveToZero();
    }

}
