package Graphics;

import Models.ImageResource;
import Models.Vector3D;
import com.jogamp.opengl.util.texture.Texture;

import javax.media.opengl.GL2;

public class Graphics {

    // members
    private GL2 gl;

    //region Singleton
    private static Graphics instance = new Graphics();

    public static Graphics getInstance() { return instance;}

    private Graphics() {
        gl = null;
    }
    //endregion

    public static void UpdateGL(GL2 newGl) {
        instance.gl = newGl;
    }

    public static void DrawTexturePlateOnFloor(ImageResource imgResource, float width, float height) {
        GL2 gl = instance.gl;

        Texture tex = imgResource.getTexture();

        if (tex != null) {
            tex.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS);

        width /= 2;
        height /= 2;

        gl.glNormal3f(0, 1, 0);
        gl.glTexCoord2f(1f, 1f);
        gl.glVertex3f(-width, 0f, -height);
        gl.glTexCoord2f(0f, 1f);
        gl.glVertex3f(width, 0, -height);
        gl.glTexCoord2f(0f, 0f);
        gl.glVertex3f(width, 0f, height);
        gl.glTexCoord2f(1f, 0f);
        gl.glVertex3f(-width, 0f, height);

        gl.glEnd();
    }

    public static void drawTexturedRectangle(ImageResource imgResource, float width, float height, float depth) {
        GL2 gl = instance.gl;

        Texture tex = imgResource.getTexture();

        if (tex != null) {
            tex.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS);

        width /= 2;
        height /= 2;
        depth /= 2;

        // Front Face
        gl.glNormal3f(0, 0, 1);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-width, -height, depth);
        gl.glTexCoord2f(1f, 0.0f);
        gl.glVertex3f(width, -height, depth);
        gl.glTexCoord2f(1f, 1.0f);
        gl.glVertex3f(width, height, depth);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-width, height, depth);
        // Back Face
        gl.glNormal3f(0, 0, -1);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-width, -height, -depth);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-width, height, -depth);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(width, height, -depth);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(width, -height, -depth);
        // Top Face
        gl.glNormal3f(0, 1, 0);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-width, height, -depth);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-width, height, depth);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(width, height, depth);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(width, height, -depth);
        // Bottom Face
        gl.glNormal3f(0, -1, 0);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-width, -height, -depth);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(width, -height, -depth);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(width, -height, depth);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-width, -height, depth);
        // Right face
        gl.glNormal3f(1, 0, 0);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(width, -height, -depth);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(width, height, -depth);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(width, height, depth);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(width, -height, depth);
        // Left Face
        gl.glNormal3f(-1, 0, 0);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-width, -height, -depth);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-width, -height, depth);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-width, height, depth);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-width, height, -depth);
        gl.glEnd();
    }
    public static void DrawTexturedRectangle(ImageResource imgResource, float width, float height, float depth) {
        GL2 gl = instance.gl;

        Texture tex = imgResource.getTexture();

        if (tex != null) {
            tex.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS);

        width /= 2;
        height /= 2;
        depth /= 2;

        // Front Face
        gl.glBegin(GL2.GL_QUADS);
        // Front Face
        gl.glNormal3f(0,0,1);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(2f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(2f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        // Back Face
        gl.glNormal3f(0,0,-1);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        // Top Face
        gl.glNormal3f(0,1,0);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        // Bottom Face
        gl.glNormal3f(0,-1,0);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        // Right face
        gl.glNormal3f(1,0,0);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        // Left Face
        gl.glNormal3f(-1,0,0);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
    }


    public static void Rotate(float x, float y, float z, float w) {
        instance.gl.glRotatef(x, y, z, w);
    }

    public static void Translate(float x, float y, float z) {
        instance.gl.glTranslatef(x, y, z);
    }
}
