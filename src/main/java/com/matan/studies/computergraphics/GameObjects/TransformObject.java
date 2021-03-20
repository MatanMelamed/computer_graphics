// Matan Melamed 205973613
package com.matan.studies.computergraphics.GameObjects;

import com.matan.studies.computergraphics.Models.Axis;
import com.matan.studies.computergraphics.Models.Transform;
import com.matan.studies.computergraphics.Models.Vector3D;

public abstract class TransformObject {
    private Transform transform;

    TransformObject() {
        transform = new Transform();
    }

    protected void SetDirZ(Vector3D newDirZ) {this.transform.DirZ = newDirZ;}

    public void Move(float x, float y, float z) { transform.Move(new Vector3D(x, y, z)); }

    public void Rotate(Axis axis, double angle) { transform.Rotate(axis, angle);}

    public Vector3D GetPosition() {return transform.Position;}

    public Vector3D GetDirX() {return transform.DirX;}

    public Vector3D GetDirY() {return transform.DirY;}

    public Vector3D GetDirZ() {return transform.DirZ;}

    public double GetAxisAngleX() {return transform.xDiv;}

    public double GetAxisAngleY() {return transform.yDiv;}

    public double GetAxisAngleZ() {return transform.zDiv;}
}
