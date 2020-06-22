package Graphics;

import Models.CoordinateSystem;

import javax.media.opengl.GL2;

public interface Drawable {
    CoordinateSystem getCoordinateSystem();
    void draw();
}
