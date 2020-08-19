package Core.Graphics;

import Models.ImageResource;
import Models.Vector3D;
import Models.WavefrontObjectLoader_DisplayList;
import com.jogamp.opengl.util.texture.Texture;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import static Utils.Utils.floats;
import static javax.media.opengl.GL.GL_FRONT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;

public class Graphics {

    public static float[] DEF_MATERIAL = {0.6f, 0.6f, 0.6f, 1f};

    // members
    public GL2 gl;
    private float xAngle;
    private float xAngleSave;
    private float yAngle;
    private float yAngleSave;
    private float zAngle;
    private float zAngleSave;

    //region Singleton
    private static Graphics instance = new Graphics();

    private Graphics() {
        gl = null;
    }
    //endregion

    public static void UpdateGL(GL2 newGl) { instance.gl = newGl; }

    //region basic operations
    public static void PushMatrix() {
        instance.gl.glPushMatrix();
        instance.xAngleSave = instance.xAngle;
        instance.yAngleSave = instance.yAngle;
        instance.zAngleSave = instance.zAngle;
    }

    public static void PopMatrix() {
        instance.gl.glPopMatrix();
        instance.xAngle = instance.xAngleSave;
        instance.yAngle = instance.yAngleSave;
        instance.zAngle = instance.zAngleSave;
    }

    public static void Rotate(float angle, float xAxis, float yAxis, float zAxis) {
        instance.gl.glRotatef(angle, xAxis, yAxis, zAxis);
        if (xAxis != 0) {
            instance.xAngle += angle;
        }
        if (yAxis != 0) {
            instance.yAngle += angle;
        }
        if (zAxis != 0) {
            instance.zAngle += angle;
        }
    }

    public static float[] GetRotations() {return new float[]{instance.xAngle, instance.yAngle, instance.zAngle};}

    public static void RotateToZero() {
        instance.gl.glRotatef(-instance.zAngle, 0, 0, 1);
        instance.gl.glRotatef(-instance.yAngle, 0, 1, 0);
        instance.gl.glRotatef(-instance.zAngle, 1, 0, 0);
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
        NextRenderExecutioner.AddRequestToNextRender(() -> SetLightColor(index, floats(0, 0, 0, 1), floats(0, 0, 0, 1)));
        //SetLightColor(index, floats(0, 0, 0, 1), floats(0, 0, 0, 1));
    }

    public static void SetLightColor(int index, float[] ambient, float[] diffuse) {
        instance.gl.glLightfv(GetLight(index), GL2.GL_AMBIENT, ambient, 0);
        instance.gl.glLightfv(GetLight(index), GL2.GL_DIFFUSE, diffuse, 0);
        instance.gl.glLightfv(GetLight(index), GL2.GL_SPECULAR, ambient, 0);
    }

    public static void SetLightPosition(int index, float[] position) {
        instance.gl.glLightfv(GetLight(index), GL2.GL_POSITION, position, 0);
    }

    public static void SetMaterial(float[] material) {
        instance.gl.glMaterialfv(GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, material, 0);
    }
    //endregion

    //region lists & draw
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

//        width = 1;
//        height = 1;
//        depth = 1;

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
        GLU glu = Renderer.GetGLU();
        GLUquadric quad = glu.gluNewQuadric();
        glu.gluSphere(quad, radius, 10, 15);
    }

    public static int Create2DTexturedPlane(float width, float height, float textureWRatio, float textureHRatio) {
        GL2 gl = instance.gl;
        int list = gl.glGenLists(1);
        gl.glNewList(list, GL2.GL_COMPILE);
        gl.glBegin(GL2.GL_QUADS);

        width /= 2f;
        height /= 2f;

        gl.glNormal3f(0, 1f, 0);
        gl.glTexCoord2f(0.0f, textureHRatio);
        gl.glVertex3f(-width, 0, -height);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-width, 0, height);
        gl.glTexCoord2f(textureWRatio, 0.0f);
        gl.glVertex3f(width, 0, height);
        gl.glTexCoord2f(textureWRatio, textureHRatio);
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
        gl.glTexCoord2f(textureWRatio, 0.0f);
        gl.glVertex3f(width, -height, depth);
        gl.glTexCoord2f(textureWRatio, textureHRatio);
        gl.glVertex3f(width, height, depth);
        gl.glTexCoord2f(0.0f, textureHRatio);
        gl.glVertex3f(-width, height, depth);
        // Back Face
        gl.glNormal3f(0, 0, -1);
        gl.glTexCoord2f(textureWRatio, 0.0f);
        gl.glVertex3f(-width, -height, -depth);
        gl.glTexCoord2f(textureWRatio, textureHRatio);
        gl.glVertex3f(-width, height, -depth);
        gl.glTexCoord2f(0.0f, textureHRatio);
        gl.glVertex3f(width, height, -depth);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(width, -height, -depth);
        // Top Face
        gl.glNormal3f(0, 1, 0);
        gl.glTexCoord2f(0.0f, textureHRatio);
        gl.glVertex3f(-width, height, -depth);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-width, height, depth);
        gl.glTexCoord2f(textureWRatio, 0.0f);
        gl.glVertex3f(width, height, depth);
        gl.glTexCoord2f(textureWRatio, textureHRatio);
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
        gl.glTexCoord2f(textureWRatio, 0.0f);
        gl.glVertex3f(width, -height, -depth);
        gl.glTexCoord2f(textureWRatio, textureHRatio);
        gl.glVertex3f(width, height, -depth);
        gl.glTexCoord2f(0.0f, textureHRatio);
        gl.glVertex3f(width, height, depth);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(width, -height, depth);
        // Left Face
        gl.glNormal3f(-1, 0, 0);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-width, -height, -depth);
        gl.glTexCoord2f(textureWRatio, 0.0f);
        gl.glVertex3f(-width, -height, depth);
        gl.glTexCoord2f(textureWRatio, textureHRatio);
        gl.glVertex3f(-width, height, depth);
        gl.glTexCoord2f(0.0f, textureHRatio);
        gl.glVertex3f(-width, height, -depth);
        gl.glEnd();
        gl.glEndList();
        return list;
    }
    //endregion

    //region not used
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

    public static void oldDrawTexturedRectangle(ImageResource imgResource, float width, float height, float depth) {
        GL2 gl = instance.gl;

        if (imgResource != null) {
            Texture tex = imgResource.getTexture();
            if (tex != null) {
                tex.bind(gl);
            }
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
    //endregion
}
