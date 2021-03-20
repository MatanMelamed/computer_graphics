// Matan Melamed 205973613
package com.matan.studies.computergraphics.Models;

import static com.matan.studies.computergraphics.Utils.Utils.areEqual;

public class Vector3D implements Comparable<Vector3D> {

    public float x;
    public float y;
    public float z;

    private static Vector3D perpendicular = new Vector3D(0, 0, 1);

    public static final Vector3D ZERO = new Vector3D(0, 0, 0);

    public Vector3D() {
        this(0, 0, 0);
    }

    public Vector3D(Vector3D other) {
        this(other.x, other.y, other.z);
    }

    public Vector3D duplicate() { return new Vector3D(x, y, z); }


    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D getPerpendicular() {
        return this.cross(perpendicular);
    }

    public boolean DifferentThan(Vector3D other) {
        return x != other.x || y != other.y || z != other.z;
    }

    public void Set(Vector3D other) {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    public Vector3D plus(Vector3D other) {
        return new Vector3D(
                x + other.x,
                y + other.y,
                z + other.z);
    }

    public Vector3D plus(float x, float y, float z) {
        return new Vector3D(
                this.x + x,
                this.y + y,
                this.z + z);
    }

    public Vector3D minus(float d) {
        return new Vector3D(
                this.x - d,
                this.y - d,
                this.z = d);
    }

    public Vector3D minus(Vector3D other) {
        return new Vector3D(
                x - other.x,
                y - other.y,
                z - other.z);
    }

    public Vector3D multiply(double scalar) {
        return multiply((float) scalar);
    }

    public Vector3D multiply(float scalar) {
        return new Vector3D(
                x * scalar,
                y * scalar,
                z * scalar);
    }

    public Vector3D cross(Vector3D other) {
        return new Vector3D(
                x * other.z - z * other.y,
                z * other.x - x * other.z,
                x * other.y - y * other.x);
    }

    public float dot(Vector3D other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public float magnitude() {
        return (float) Math.sqrt(dot(this));
    }

    public float angle() {
        float result = (float) Math.toDegrees(Math.atan2(y, x));
        return result < 0 ? 360 + result : result;
    }

    public Vector3D normalize() {
        float magnitude = magnitude();
        if (magnitude == 0) {
            return new Vector3D(0, 0, 0);
        }
        return new Vector3D(x / magnitude, y / magnitude, z / magnitude);
    }

    public float angleBetween(Vector3D other) {
        if (this.compareTo(other) == 0) { return 0; }

        float dot = this.dot(other);
        float m1 = this.magnitude();
        float m2 = other.magnitude();
        float mags = m1 * m2;
        float res = dot / mags;
        res = (float) Math.acos(res);

        Vector3D cross = cross(other);
        return cross.dot(Transform.WORLD_Y) < 0 ? res : -res;
    }

    @Override
    public String toString() {
        return String.format("(%.4f, %.4f, %.4f)", x, y, z);
    }

    @Override
    public int compareTo(Vector3D o) {
        boolean a = areEqual(this.x, o.x);
        boolean b = areEqual(this.y, o.y);
        boolean c = areEqual(this.z, o.z);
        return areEqual(this.x, o.x) && areEqual(this.y, o.y) && areEqual(this.z, o.z) ? 0 : 1;
    }
}
