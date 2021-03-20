// Matan Melamed 205973613
package com.matan.studies.computergraphics.Core.Collision.CollisionHandlers;

import com.matan.studies.computergraphics.Core.Collision.Collider;
import com.matan.studies.computergraphics.Core.Collision.IntersectData;

public interface CollisionChecker {
    IntersectData CheckCollision(Collider first, Collider second);
}
