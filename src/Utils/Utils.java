// Matan Melamed 205973613
package Utils;

public class Utils {
    public static float[] floats(float... floats) {
        float[] result = new float[floats.length];
        System.arraycopy(floats, 0, result, 0, floats.length);
        return result;
    }

    public static boolean areEqual(float f1, float f2) {
        float epsilon = 0.00001f;
        return Math.abs(f1 - f2) < epsilon;
    }
}
