package World;

import Core.Collision.AxisAlignedBoundingBox;
import GameObjects.Components.ColliderComponent;
import GameObjects.Components.MaterialComponent;
import GameObjects.Components.WavefrontComponent;
import GameObjects.GameObject;
import GameObjects.Prefabs.BoxObject;
import GameObjects.Prefabs.PlateObject;
import Models.Axis;
import Models.Vector3D;

import java.util.ArrayList;
import java.util.Arrays;


public class Level1 extends BaseLevel {

    @Override
    public float[] GetSpawnPoint() { return new float[]{-3f, 0.5f, -9f};}

    @Override
    public float[] GetFinishRectangle() { return new float[]{-0.5f, 0f, 7f, 6f, 4f, 12f}; }

    public Level1() {
        createAxisMarkers();

        float w = 0.5f;     // wall thickness
        float g = 2f;   // gap between walls
        float u = w + g;    // unit's length
        float h = 5f;       // maze height

        float fl = 5f * u; // floor length
        float srs = 2.5f * u;

        GameObject maze = new GameObject("maze");
        BoxObject boxObject;

        GameObject startRoom = CreateStartRoom(srs, srs * 5, h);
        startRoom.Move(-2.5f * u + (srs / 2f), 0, -2.5f * u - (srs * 5 / 2f));
        maze.AddChild(startRoom);

        GameObject endRoom = CreateEndRoom(srs, srs, h);
        endRoom.Move(2.5f * u - (srs / 2f) + (w / 2f), 0, 2.5f * u + (srs / 2f));
        maze.AddChild(endRoom);

        PlateObject plate = new PlateObject("floor", "square_floor.png", fl, fl, fl, fl);
        maze.AddChild(plate);

        // surrounding walls
        // (-x,0,0)
        boxObject = new BoxObject("inside wall", w, h, 5f * u + w, "concc.jpg");
        boxObject.Move(-2.5f * u, h / 2f, 0);
        maze.AddChild(boxObject);

        boxObject = new BoxObject("inside wall", w, h, 5f * u + w, "concc.jpg");
        boxObject.Move(2.5f * u, h / 2f, 0);
        maze.AddChild(boxObject);

        boxObject = new BoxObject("inside wall", 4 * u, h, w, "concc.jpg");
        boxObject.Move(0.5f * g, h / 2f, -2.5f * u);
        maze.AddChild(boxObject);

        boxObject = new BoxObject("inside wall", 4 * u, h, w, "concc.jpg");
        boxObject.Move(-0.5f * g, h / 2f, 2.5f * u);
        maze.AddChild(boxObject);

        // parallel to z axis walls
        boxObject = new BoxObject("inside wall", w, h, 2f * u, "concc.jpg");
        boxObject.Move(0.5f * u, h / 2f, -(u + 0.5f * g));
        maze.AddChild(boxObject);

        boxObject = new BoxObject("inside wall", w, h, 2f * u, "concc.jpg");
        boxObject.Move(1.5f * u, h / 2f, -0.5f * g - w);
        maze.AddChild(boxObject);

        boxObject = new BoxObject("inside wall", w, h, 1f * u, "concc.jpg");
        boxObject.Move(-0.5f * u, h / 2f, 0.5f * g + 0.5f * u);
        maze.AddChild(boxObject);

        boxObject = new BoxObject("inside wall", w, h, 1f * u, "concc.jpg");
        boxObject.Move(0.5f * u, h / 2f, 0.5f * g + 1.5f * u);
        maze.AddChild(boxObject);

        boxObject = new BoxObject("inside wall", w, h, 1f * u, "concc.jpg");
        boxObject.Move(-1.5f * u, h / 2f, 0.5f * g + 0.5f * u + w);
        maze.AddChild(boxObject);

        // parallel to x axis walls

        boxObject = new BoxObject("inside wall", 2f * u, h, w, "concc.jpg");
        boxObject.Move(-0.5f * g - w, h / 2f, -0.5f * u);
        maze.AddChild(boxObject);

        boxObject = new BoxObject("inside wall", 2f * u, h, w, "concc.jpg");
        boxObject.Move(-(u + 0.5f * g), h / 2f, -1.5f * u);
        maze.AddChild(boxObject);

        boxObject = new BoxObject("inside wall", 2f * u, h, w, "concc.jpg");
        boxObject.Move(0.5f * g + w, h / 2f, 0.5f * u);
        maze.AddChild(boxObject);

        boxObject = new BoxObject("inside wall", 1f * u, h, w, "concc.jpg");
        boxObject.Move(-0.5f * w, h / 2f, 1.5f * u);
        maze.AddChild(boxObject);

        boxObject = new BoxObject("inside wall", 1f * u, h, w, "concc.jpg");
        boxObject.Move(-0.5f * w + 2f * u, h / 2f, 1.5f * u);
        maze.AddChild(boxObject);

        boxObject = new BoxObject("inside wall", 1f * u, h, w, "concc.jpg");
        boxObject.Move(-2f * u + 0.5f * w, h / 2f, 0.5f * u);
        maze.AddChild(boxObject);


        float skySize = 5f * u + 2f * srs;
        // sky
        plate = new PlateObject("ceiling", "ceilin.jpg", skySize / 2f, skySize / 2f, skySize, skySize);
        plate.Move(0, h, 0);
        plate.Rotate(Axis.X, 180);
        maze.AddChild(plate);

        addDecorations();

        AddGameObject(maze);
    }

    void addDecorations() {
        GameObject skull = new GameObject("skull");
        skull.AddComponent(new WavefrontComponent("skull", "jpg", 0.05f));
        skull.AddComponent(new MaterialComponent(floats(0.05f, 0.05f, 0.05f, 1f)));
        skull.Move(-0.35f, 0, -6.85f);
        skull.Rotate(Axis.Y, 220);
        AddGameObject(skull);
    }

    private GameObject CreateStartRoom(float width, float depth, float height) {
        GameObject room = new GameObject("startRoom");

        float smallValue = 0.01f;
        AxisAlignedBoundingBox collider;
        ColliderComponent colliderComponent;

        PlateObject plate = new PlateObject("floor", "square_floor.png", width, depth, width, depth);
        room.AddChild(plate);

        // left wall (X,0,0)
        plate = new PlateObject("wall", "prison_bars.jpg", depth / 4, 1, depth, height);
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
        plate = new PlateObject("wall", "prison_bars.jpg", depth / 4, 1, depth, height);
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
        plate = new PlateObject("wall", "prison_bars.jpg", depth / 4, 1, width, height);
        plate.Rotate(Axis.X, -90);
        plate.Rotate(Axis.Z, 180);

        plate.Move(0, height / 2f, -depth / 2f);
        collider = new AxisAlignedBoundingBox(
                new Vector3D(-width / 2f, 0, -smallValue - (depth / 2f)),
                new Vector3D(width / 2f, height, smallValue - (depth / 2f)));
        colliderComponent = new ColliderComponent(collider);
        plate.AddComponent(colliderComponent);
        room.AddChild(plate);

        return room;
    }

    private GameObject CreateEndRoom(float width, float depth, float height) {
        GameObject room = new GameObject("startRoom");

        float smallValue = 0.01f;
        AxisAlignedBoundingBox collider;
        ColliderComponent colliderComponent;

        PlateObject plate = new PlateObject("floor", "square_floor.png", width, depth, width, depth);
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
        room.AddChild(plate);

        // right wall (-X,0,0)
        plate = new PlateObject("wall", "wall.jpg", depth / 4, 1, depth, height);
        plate.Rotate(Axis.X, -90);
        plate.Rotate(Axis.Z, -90);

        plate.Move(-width / 2f, height / 2f, 0);
        collider = new AxisAlignedBoundingBox(
                new Vector3D(-smallValue - (width / 2f), 0, -depth / 2f),
                new Vector3D(smallValue - (width / 2f), height, depth / 2f));
        colliderComponent = new ColliderComponent(collider);
        plate.AddComponent(colliderComponent);

        room.AddChild(plate);

        // front wall (0,0,Z)
        plate = new PlateObject("wall", "wall.jpg", depth / 4, 1, width, height);
        plate.Rotate(Axis.X, -90);
        plate.Move(0, height / 2f, depth / 2f);
        collider = new AxisAlignedBoundingBox(
                new Vector3D(-width / 2f, 0, -smallValue + (depth / 2f)),
                new Vector3D(width / 2f, height, smallValue + (depth / 2f)));
        colliderComponent = new ColliderComponent(collider);
        plate.AddComponent(colliderComponent);
        room.AddChild(plate);

        return room;
    }

    private float[] floats(float x, float y, float z, float w) {
        return new float[]{x, y, z, w};
    }

    private float[] floats(float x, float y, float z) {
        return new float[]{x, y, z};
    }

    @Override
    protected void InitializeEnemiesRoute() {
        enemiesRoute = new ArrayList<>(Arrays.asList(
                new Vector3D(-5, 0, -6.3f),
                new Vector3D(-5f, 0, -5f),
                new Vector3D(0f, 0, -5f),
                new Vector3D(0f, 0, -2.5f),
                new Vector3D(-5f, 0, -2.5f),
                new Vector3D(-5f, 0, 0f),
                new Vector3D(2.5f, 0, 0f),
                new Vector3D(2.5f, 0, -5f),
                new Vector3D(5f, 0, -5f),
                new Vector3D(5f, 0, 2.5f),
                new Vector3D(2.5f, 0, 2.5f),
                new Vector3D(2.5f, 0, 5f),
                new Vector3D(5f, 0, 5f),
                new Vector3D(5f, 0, 6.3f)
        ));
    }
}
