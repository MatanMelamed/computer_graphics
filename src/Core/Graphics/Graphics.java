// Matan Melamed 205973613
package Core.Graphics;

import Models.ImageResource;
import Models.Vector3D;
import Models.WavefrontObjectLoader_DisplayList;
import com.jogamp.opengl.util.texture.Texture;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import java.nio.FloatBuffer;

import static Utils.Utils.floats;
import static javax.media.opengl.GL.*;

public class Graphics {

    public static float[] DEF_MATERIAL = {0.6f, 0.6f, 0.6f, 1f};

    // members
    private GL2 gl;

    public static float angle = 40f;
    public static float expo = 2f;
    public static float atte = 0.5f;

    //region Singleton
    private static Graphics instance = new Graphics();

    private Graphics() { gl = null; }
    //endregion

    static void UpdateGL(GL2 newGl) { instance.gl = newGl; }

    //region basic operations
    public static void PushMatrix() {
        instance.gl.glPushMatrix();
    }

    public static void PopMatrix() {
        instance.gl.glPopMatrix();
    }

    public static void Rotate(float angle, float xAxis, float yAxis, float zAxis) {
        instance.gl.glRotatef(angle, xAxis, yAxis, zAxis);
    }


    public static void Translate(float x, float y, float z) {
        instance.gl.glTranslatef(x, y, z);
    }

    public static void Scale(float scalar) {
        instance.gl.glScalef(scalar, scalar, scalar);
    }
    //endregion

    //region Lights
    private static int GetLight(int index) {
        return index == 0 ? GL2.GL_LIGHT0 :
                index == 1 ? GL2.GL_LIGHT1 :
                        index == 2 ? GL2.GL_LIGHT2 :
                                index == 3 ? GL2.GL_LIGHT3 :
                                        index == 4 ? GL2.GL_LIGHT4 :
                                                index == 5 ? GL2.GL_LIGHT5 :
                                                        index == 6 ? GL2.GL_LIGHT6 : GL2.GL_LIGHT7;
    }

    public static void EnableLight(int index) { instance.gl.glEnable(GetLight(index)); }

    public static void DisableLight(int index) {
        NextRenderExecutioner.AddRequestToNextRender(() -> SetLightColor(index, 0, 0, 0, 0, 0, 0));
    }

    public static void SetLightColor(int index,
                                     float ambientX, float ambientY, float ambientZ,
                                     float diffuseX, float diffuseY, float diffuseZ) {
        instance.gl.glLightfv(GetLight(index), GL2.GL_AMBIENT, floats(ambientX, ambientY, ambientZ, 1), 0);
        instance.gl.glLightfv(GetLight(index), GL2.GL_DIFFUSE, floats(diffuseX, diffuseY, diffuseZ, 1), 0);
    }

    public static void SetLightPosition(int index, float[] position) {
        instance.gl.glLightfv(GetLight(index), GL2.GL_POSITION, position, 0);
    }

    public static void SetLightDirection(int index, Vector3D direction, float angle, float exponent, float constAttenuation) {
        instance.gl.glLightf(GetLight(index), GL2.GL_SPOT_CUTOFF, angle);
        instance.gl.glLightfv(GetLight(index), GL2.GL_SPOT_DIRECTION, FloatBuffer.wrap(new float[]{direction.x, direction.y, direction.z}));
        instance.gl.glLightf(GetLight(index), GL2.GL_SPOT_EXPONENT, exponent);
        instance.gl.glLightf(GetLight(index), GL2.GL_CONSTANT_ATTENUATION, constAttenuation);
    }

    public static void SetMaterial(float[] material) {
        instance.gl.glMaterialfv(GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, material, 0);
    }
    //endregion

    //region lists & draw

    public static GL2 GetGL() {return instance.gl;}

    public static void SetColor(float r, float g, float b) {
        instance.gl.glColor3f(r, g, b);
    }

    public static void BindTexture(ImageResource imageResource) {
        instance.gl.glTexParameteri(GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        instance.gl.glTexParameteri(GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);

        Texture tex = imageResource.getTexture();

        if (tex != null) {
            tex.bind(instance.gl);
        }
    }

    public static int LoadWavefrontObject(String resourcePath) {
        return WavefrontObjectLoader_DisplayList.loadWavefrontObjectAsDisplayList(instance.gl, resourcePath);
    }

    public static void CallList(int list) {
        instance.gl.glCallList(list);
    }

    public static void DrawAABB(Vector3D min, Vector3D max) {
        GL2 gl = instance.gl;

        gl.glBegin(GL2.GL_QUADS);

        float width = max.x - min.x;
        float height = max.y - min.y;
        float depth = max.z - min.z;

        width /= 2;
        height /= 2;
        depth /= 2;

        // Front Face
        gl.glVertex3f(-width, -height, depth);
        gl.glVertex3f(width, -height, depth);
        gl.glVertex3f(width, height, depth);
        gl.glVertex3f(-width, height, depth);
        // Back Face
        gl.glVertex3f(-width, -height, -depth);
        gl.glVertex3f(-width, height, -depth);
        gl.glVertex3f(width, height, -depth);
        gl.glVertex3f(width, -height, -depth);
        // Top Face
        gl.glVertex3f(-width, height, -depth);
        gl.glVertex3f(-width, height, depth);
        gl.glVertex3f(width, height, depth);
        gl.glVertex3f(width, height, -depth);
        // Bottom Face
        gl.glVertex3f(-width, -height, -depth);
        gl.glVertex3f(width, -height, -depth);
        gl.glVertex3f(width, -height, depth);
        gl.glVertex3f(-width, -height, depth);
        // Right face
        gl.glVertex3f(width, -height, -depth);
        gl.glVertex3f(width, height, -depth);
        gl.glVertex3f(width, height, depth);
        gl.glVertex3f(width, -height, depth);
        // Left Face
        gl.glVertex3f(-width, -height, -depth);
        gl.glVertex3f(-width, -height, depth);
        gl.glVertex3f(-width, height, depth);
        gl.glVertex3f(-width, height, -depth);
        gl.glEnd();
//        gl.glFlush();
    }

    public static void DrawSphere(float radius) {
        SetColor(0, 0, 1);
        GLU glu = Renderer.GetGLU();
        GLUquadric quad = glu.gluNewQuadric();
        glu.gluSphere(quad, radius, 10, 15);
        SetColor(0, 0, 0);
    }

    public static int Create2DTexturedPlane(float width, float height, float textureWRatio, float textureHRatio) {
        GL2 gl = instance.gl;
        int list = gl.glGenLists(1);
        gl.glNewList(list, GL2.GL_COMPILE);
        gl.glBegin(GL2.GL_QUADS);

        width /= 2f;
        height /= 2f;

        gl.glNormal3f(0, 1f, 0);
        gl.glTexCoord2f(textureWRatio, textureHRatio);
        gl.glVertex3f(-width, 0, -height);
        gl.glTexCoord2f(textureWRatio, 0.0f);
        gl.glVertex3f(-width, 0, height);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(width, 0, height);
        gl.glTexCoord2f(0.0f, textureHRatio);
        gl.glVertex3f(width, 0, -height);

        gl.glEnd();
        gl.glEndList();
        return list;
    }

    public static int Create3DTexturedRectangle(float width, float height, float depth, float textureWRatio, float textureHRatio) {
        GL2 gl = instance.gl;
        int list = gl.glGenLists(1);
        gl.glNewList(list, GL2.GL_COMPILE);
        gl.glBegin(GL2.GL_QUADS);

        width /= 2f;
        height /= 2f;
        depth /= 2f;

        // Front Face
        gl.glNormal3f(0, 0, 1);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-width, -height, depth);
        gl.glTexCoord2f(width, 0.0f);
        gl.glVertex3f(width, -height, depth);
        gl.glTexCoord2f(width, height);
        gl.glVertex3f(width, height, depth);
        gl.glTexCoord2f(0.0f, height);
        gl.glVertex3f(-width, height, depth);
        // Back Face
        gl.glNormal3f(0, 0, -1);
        gl.glTexCoord2f(width, 0.0f);
        gl.glVertex3f(-width, -height, -depth);
        gl.glTexCoord2f(width, height);
        gl.glVertex3f(-width, height, -depth);
        gl.glTexCoord2f(0.0f, height);
        gl.glVertex3f(width, height, -depth);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(width, -height, -depth);
        // Top Face
        gl.glNormal3f(0, 1, 0);
        gl.glTexCoord2f(0.0f, height);
        gl.glVertex3f(-width, height, -depth);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-width, height, depth);
        gl.glTexCoord2f(textureWRatio, 0.0f);
        gl.glVertex3f(width, height, depth);
        gl.glTexCoord2f(textureWRatio, height);
        gl.glVertex3f(width, height, -depth);
        // Bottom Face
        gl.glNormal3f(0, -1, 0);
        gl.glTexCoord2f(textureWRatio, textureHRatio);
        gl.glVertex3f(-width, -height, -depth);
        gl.glTexCoord2f(0.0f, textureHRatio);
        gl.glVertex3f(width, -height, -depth);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(width, -height, depth);
        gl.glTexCoord2f(textureWRatio, 0.0f);
        gl.glVertex3f(-width, -height, depth);
        // Right face
        gl.glNormal3f(1, 0, 0);
        gl.glTexCoord2f(depth, 0.0f);
        gl.glVertex3f(width, -height, -depth);
        gl.glTexCoord2f(depth, height);
        gl.glVertex3f(width, height, -depth);
        gl.glTexCoord2f(0.0f, height);
        gl.glVertex3f(width, height, depth);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(width, -height, depth);
        // Left Face
        gl.glNormal3f(-1, 0, 0);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-width, -height, -depth);
        gl.glTexCoord2f(depth, 0.0f);
        gl.glVertex3f(-width, -height, depth);
        gl.glTexCoord2f(depth, height);
        gl.glVertex3f(-width, height, depth);
        gl.glTexCoord2f(0.0f, height);
        gl.glVertex3f(-width, height, -depth);
        gl.glEnd();
        gl.glEndList();
        return list;
    }
    //endregion
}
