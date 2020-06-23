package World;

import GameObjects.GameObject;

public interface World {
    void Update();
    void Render();

    void AddGameObject(GameObject gameObject);
}
