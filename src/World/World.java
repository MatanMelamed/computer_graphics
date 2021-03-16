package World;

import GameObjects.GameObject;

public interface World {
    void Initialize();
    void Update(float deltaTime);
    void Render();

    void AddGameObject(GameObject gameObject);
}
