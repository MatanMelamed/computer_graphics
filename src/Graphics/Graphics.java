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

    private Graphics() {
        gl = null;
    }
    //endregion

    public static void UpdateGL(GL2 newGl) {
        instance.gl = newGl;
    }

    public static void PushMatrix() {
        instance.gl.glPushMatrix();
    }

    public static void PopMatrix() {
        instance.gl.glPopMatrix();
    }

    public static void SetColor(float r, float g, float b) {
        instance.gl.glColor3f(r, g, b);
    }

    public static void BindTexture(ImageResource imageResource) {
        Texture tex = imageResource.getTexture();

        if (tex != null) {
            tex.bind(instance.gl);
        }
    }

    public static void CallList(int list) {
        instance.gl.glCallList(list);
    }

    public static int Create2DTexturedPlane(float width, float height) {
        GL2 gl = instance.gl;
        int list = gl.glGenLists(1);
        gl.glNewList(list, GL2.GL_COMPILE);
        gl.glBegin(GL2.GL_QUADS);

        width /= 2;
        height /= 2;

        gl.glNormal3f(0, 1, 0);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-width, 0, -height);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-width, 0, height);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(width, 0, height);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(width, 0, -height);

        gl.glEnd();
        gl.glEndList();
        return list;
    }

    public static int Create3DTexturedRectangle(float width, float height, float depth) {
        GL2 gl = instance.gl;
        int list = gl.glGenLists(1);
        gl.glNewList(list, GL2.GL_COMPILE);
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
        gl.glEndList();
        return list;
    }

    public static void Rotate(float angle, float xAxis, float yAxis, float zAxis) {
        instance.gl.glRotatef(angle, xAxis, yAxis, zAxis);
    }

    public static void Translate(float x, float y, float z) {
        instance.gl.glTranslatef(x, y, z);
    }


    public static void DrawLine(Vector3D start, Vector3D end) {
        GL2 gl = instance.gl;
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3f((float) start.x, (float) start.y, (float) start.z);
        gl.glVertex3f((float) end.x, (float) end.y, (float) end.z);
        gl.glEnd();
    }

    public static void DrawTexturePlateOnFloorInCenter(ImageResource imgResource, float width, float height) {
        GL2 gl = instance.gl;

        Texture tex = imgResource.getTexture();

        if (tex != null) {
            tex.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS);

        width /= 2;
        height /= 2;

        gl.glNormal3f(0, 1, 0);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-width, 0, -height);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-width, 0, height);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(width, 0, height);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(width, 0, -height);

        gl.glEnd();
    }

    public static void DrawTexturedRectangleInCenter(ImageResource imgResource, float width, float height, float depth) {
        GL2 gl = instance.gl;

        Texture tex = imgResource.getTexture();

        if (tex != null) {
            tex.bind(gl);
        }


    }

    public static void oldDrawTexturedRectangle(ImageResource imgResource, float width, float height, float depth) {
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
        gl.glNormal3f(0, 0, 1);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(2f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(2f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        // Back Face
        gl.glNormal3f(0, 0, -1);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        // Top Face
        gl.glNormal3f(0, 1, 0);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        // Bottom Face
        gl.glNormal3f(0, -1, 0);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        // Right face
        gl.glNormal3f(1, 0, 0);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        // Left Face
        gl.glNormal3f(-1, 0, 0);
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
}
