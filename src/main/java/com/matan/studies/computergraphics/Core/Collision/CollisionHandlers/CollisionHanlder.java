// Matan Melamed 205973613
package com.matan.studies.computergraphics.Core.Collision.CollisionHandlers;

import com.matan.studies.computergraphics.Core.Collision.IntersectData;
import com.matan.studies.computergraphics.GameObjects.Components.ColliderComponent;

public interface CollisionHanlder {
    void HandleCollision(IntersectData data, ColliderComponent other);
}
