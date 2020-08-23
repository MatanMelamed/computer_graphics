// Matan Melamed 205973613
package Levels;

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

import static Utils.Utils.floats;

public class Level1 extends BaseLevel {

    public Level1() {
        super(1);
        float w = 0.5f;     // wall thickness
        float g = 2f;   // gap between walls
        float h = 7f;       // maze playerHeight

//        CreateAxisMarkers();
        maze = GenerateMaze(w, g, h);
        startBarrier = GetStartBarrier(w, g, h);

        AddGameObject(maze);
        AddGameObject(startBarrier);
        AddGameObject(GetDecorations(w, g, h));
    }

    private GameObject GetStartBarrier(float w, float g, float h) {
        float width = 2.5f * (w + g);
        float smallValue = 0.01f;

        GameObject barrier = new GameObject("startBarrier");

        // front wall (0,0,Z)
        GameObject plate = new PlateObject("barrier wall", "concc.jpg", width, h, width, h);
        plate.Rotate(Axis.X, -90);
        plate.Move(0, h / 2f, 0);

        GameObject sign = new PlateObject("barrier sign", "turn_around.jpg", 1, 1, 3, 2);
        sign.Rotate(Axis.X, -90);
        sign.Move(0, h / 2f, -smallValue);
        plate.AddChild(sign);

        ColliderComponent colliderComponent = new ColliderComponent(new AxisAlignedBoundingBox(
                new Vector3D(-width / 2f, 0, -smallValue),
                new Vector3D(width / 2f, h, smallValue)));
        plate.AddComponent(colliderComponent);

        plate.Move(-2.5f * (w + g) + (width / 2f), 0, -2.5f * (w + g) - 0.5f);


        barrier.AddChild(plate);
        return barrier;
    }

    GameObject GenerateMaze(float w, float g, float h) {

        float u = w + g;    // unit's length
        float fl = 5f * u; // floor length
        float srs = 2.5f * u;

        GameObject maze = new GameObject("maze");
        BoxObject boxObject;

        // Start & end room
        GameObject startRoom = CreateStartRoom(srs, srs * 5, h);
        startRoom.Move(-0.5f * srs, 0, -3.5f * srs);
        maze.AddChild(startRoom);

        enemySpawn = floats(-srs / 2f, 0, -5f * srs);

        GameObject endRoom = CreateEndRoom(srs, srs, h);
        endRoom.Move(0.5f * srs + (w / 2f), 0, 1.5f * srs);
        maze.AddChild(endRoom);

        float endingX = 2.5f * u;
        float beginningZ = 2.5f * u;
        finishRectange = floats(endingX - srs, 0, beginningZ, endingX, h, beginningZ + srs);
        spawn = floats(-2.5f * u + (srs / 2f), 0, -2.5f * u - g * 2f);

        // Maze
        PlateObject plate = new PlateObject("floor", "square_floor.jpg", fl, fl, fl, fl);
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

        // ceiling
        plate = new PlateObject("ceiling", "ceilin.jpg", fl / 2f, fl / 2f, fl, fl);
        plate.Move(0, h, 0);
        plate.Rotate(Axis.X, 180);
        maze.AddChild(plate);

        return maze;
    }

    GameObject GetDecorations(float w, float g, float h) {
        GameObject decorations = new GameObject("decorations");
        GameObject skull = new GameObject("skull");
        skull.AddComponent(new WavefrontComponent("skull", "jpg", 0.05f));
        skull.AddComponent(new MaterialComponent(floats(0.05f, 0.05f, 0.05f, 1f)));
        skull.Move(-1, 0, -2.5f * (w + g) - 1);
        skull.Rotate(Axis.Y, 220);
        decorations.AddChild(skull);

        PlateObject picture = new PlateObject("picture", "spooky.jpg", 1, 1, 3, 2);
        picture.Rotate(Axis.X, -90);
        picture.Rotate(Axis.Z, 90);
        picture.Move(5.9000f, 1.2000f, -4.0000f);
//        ControllableObject c = new ControllableObject(picture);
        decorations.AddChild(picture);


        return decorations;
    }

    private GameObject CreateStartRoom(float width, float depth, float height) {
        GameObject room = new GameObject("startRoom");

        float smallValue = 0.01f;
        AxisAlignedBoundingBox collider;
        ColliderComponent colliderComponent;

        PlateObject plate = new PlateObject("floor", "square_floor.jpg", width, depth, width, depth);
        room.AddChild(plate);

        // left wall (X,0,0)
        plate = new PlateObject("wall", "t.jpg", depth / 2f, 1, depth, height);
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
        plate = new PlateObject("wall", "t.jpg", depth / 2f, 1, depth, height);
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
        plate = new PlateObject("wall", "t.jpg", width / 2f, 1, width, height);
        plate.Rotate(Axis.X, -90);
        plate.Rotate(Axis.Z, 180);

        plate.Move(0, height / 2f, -depth / 2f);
        collider = new AxisAlignedBoundingBox(
                new Vector3D(-width / 2f, 0, -smallValue - (depth / 2f)),
                new Vector3D(width / 2f, height, smallValue - (depth / 2f)));
        colliderComponent = new ColliderComponent(collider);
        plate.AddComponent(colliderComponent);
        room.AddChild(plate);

        // ceiling
        plate = new PlateObject("ceiling", "ceilin.jpg", width / 2f, depth / 2f, width, depth);
        plate.Move(0, height, 0);
        plate.Rotate(Axis.X, 180);
        room.AddChild(plate);

        return room;
    }

    private GameObject CreateEndRoom(float width, float depth, float height) {
        GameObject room = new GameObject("startRoom");

        float smallValue = 0.01f;
        AxisAlignedBoundingBox collider;
        ColliderComponent colliderComponent;

        PlateObject plate = new PlateObject("floor", "square_floor.jpg", width, depth, width, depth);
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

        // ceiling
        plate = new PlateObject("ceiling", "ceilin.jpg", width / 2f, depth / 2f, width, depth);
        plate.Move(0, height, 0);
        plate.Rotate(Axis.X, 180);
        room.AddChild(plate);

        return room;
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
