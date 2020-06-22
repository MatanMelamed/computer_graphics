package Graphics;

import Core.InputManager;
import World.WorldManager;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.newt.event.awt.AWTMouseAdapter;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

public class GraphicsEventListener implements GLEventListener {

    // members
    private GL2 gl;
    private GLU glu;

    //region Singleton
    private static GraphicsEventListener instance = new GraphicsEventListener();

    public static GraphicsEventListener getInstance() { return instance;}

    private GraphicsEventListener() {
        gl = null;
        glu = new GLU();
    }
    //endregion

    public static GL2 GetCurrentGL() {return instance.gl;}

    public static GLU GetGLU() {return instance.glu;}


    private void updateGL(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        Graphics.UpdateGL(gl);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        updateGL(drawable);

        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL2.GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glDepthFunc(GL2.GL_LEQUAL);               // The Type Of Depth Testing To Do
        // Really Nice Perspective Calculations
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        // Texture
        gl.glEnable(GL.GL_TEXTURE_2D);

        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

        // keyboard
        if (drawable instanceof com.jogamp.newt.Window) {
            com.jogamp.newt.Window window = (Window) drawable;
            window.addKeyListener(InputManager.getInstance());
            window.addMouseListener(InputManager.getInstance());
        } else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) drawable;
            new AWTKeyAdapter(InputManager.getInstance(), drawable).addTo(comp);
            new AWTMouseAdapter(InputManager.getInstance(), drawable).addTo(comp);
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        updateGL(drawable);

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        updateGL(drawable);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();  // Reset The View
        glu.gluLookAt(0, 0, 0, InputManager.x, InputManager.y, InputManager.z, 0, 1, 0);
        gl.glPushMatrix();

        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
        WorldManager.Render();
        gl.glPopMatrix();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        updateGL(drawable);

        if (height <= 0) {
            height = 1;
        }
        float h = (float) width / (float) height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
}
