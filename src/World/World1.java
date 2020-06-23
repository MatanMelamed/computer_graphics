package World;

import GameObjects.BoxObject;

public class World1 extends BaseWorld {

    public World1() {
        BoxObject box;

        box = new BoxObject("wood_box.jpg");
        box.SetPosition(10, 0, 0);
        AddGameObject(box);

        box = new BoxObject("wood_box.jpg");
        box.SetPosition(-10, 0, 0);
        AddGameObject(box);

        box = new BoxObject("wood_box.jpg");
        box.SetPosition(0, 0, 10);
        AddGameObject(box);

        box = new BoxObject("wood_box.jpg");
        box.SetPosition(0, 0, -10);
        AddGameObject(box);
    }
}
