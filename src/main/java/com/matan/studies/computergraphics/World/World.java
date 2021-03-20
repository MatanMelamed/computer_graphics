package com.matan.studies.computergraphics.World;

import com.matan.studies.computergraphics.GameObjects.GameObject;

public interface World {
    void Initialize();
    void Update(float deltaTime);
    void Render();

    void AddGameObject(GameObject gameObject);
}
