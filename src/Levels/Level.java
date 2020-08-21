package World;

import GameObjects.GameObject;
import Models.Vector3D;

import java.util.ArrayList;

public interface Level {
    void Initialize();

    void Update(float deltaTime);

    void Render();

    void AddGameObject(GameObject gameObject);

    float[] GetSpawnPoint();

    float[] GetFinishRectangle();

    float[] GetEnemySpawn();

    GameObject GetBarrier();

    ArrayList<Vector3D> GetEnemyRoute();
}
