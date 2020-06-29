package World;

import GameObjects.PlateObject;
import Models.Axis;


public class World1 extends BaseWorld {

    public World1() {
        CreateRoom(20,30,3);
    }

    private void CreateRoom(int width, int depth, int height) {
        // floor
        PlateObject plate = new PlateObject("square_floor.png", 10, 5, width, depth);
        plate.Move(plate.getWidth() / 2, 0, plate.getHeight() / 2);
        AddGameObject(plate);

        // left wall
        plate = new PlateObject("wall.jpg", depth / 4, 1, depth, height);
        plate.Move(width, height/2f, depth/2f);
        plate.Rotate(Axis.X, -90);
        plate.Rotate(Axis.Z, 90);
        AddGameObject(plate);

        // right wall
        plate = new PlateObject("wall.jpg", depth / 4, 1, depth, height);
        plate.Move(0, height/2f, depth/2f);
        plate.Rotate(Axis.X, -90);
        plate.Rotate(Axis.Z, -90);
        AddGameObject(plate);

        // front wall
        plate = new PlateObject("wall.jpg", depth / 4, 1, width, height);
        plate.Move(width/2f, height/2f, depth);
        plate.Rotate(Axis.X, 90);
        plate.Rotate(Axis.Y, 180);
        AddGameObject(plate);

        // back wall
        plate = new PlateObject("wall.jpg", depth / 4, 1, width, height);
        plate.Move(width/2f, height/2f, 0);
        plate.Rotate(Axis.X, -90);
        AddGameObject(plate);
    }

}
