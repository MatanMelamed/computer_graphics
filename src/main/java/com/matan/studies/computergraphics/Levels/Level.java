// Matan Melamed 205973613
package com.matan.studies.computergraphics.Levels;

import com.matan.studies.computergraphics.GameObjects.GameObject;
import com.matan.studies.computergraphics.Models.Vector3D;

import java.util.ArrayList;

public interface Level {
    void Initialize();

    boolean IsInit();

    void Update(float deltaTime);

    void Render();

    void AddGameObject(GameObject gameObject);

    float[] GetSpawnPoint();

    float[] GetFinishRectangle();

    float[] GetEnemySpawn();

    GameObject GetBarrier();

    ArrayList<Vector3D> GetEnemyRoute();

    int GetNumber();
}
