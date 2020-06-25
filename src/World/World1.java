package World;

import GameObjects.BoxObject;


public class World1 extends BaseWorld {

    public World1() {
        BoxObject box;

        box = new BoxObject("wood_box.jpg", 16, 0, 16);
        box.SetPosition(0, -2, 0);
        AddGameObject(box);

        box = new BoxObject("wood_box.jpg", 1, 16, 16);
        box.SetPosition(8, 6, 0);
        AddGameObject(box);

        box = new BoxObject("wood_box.jpg", 16, 16, 1);
        box.SetPosition(0, 6, 8);
        AddGameObject(box);

    }
}
