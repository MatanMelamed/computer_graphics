package World;

import GameObjects.GameObject;

public interface World {
    void PrepareToRender();
    void Update();
    void Render();

    void AddGameObject(GameObject gameObject);
}
