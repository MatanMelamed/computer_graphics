package Graphics;

import Core.InputManager;
import GameObjects.PlayerObject;
import World.WorldManager;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.newt.event.awt.AWTMouseAdapter;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.jogamp.opengl.util.ImmModeSink.GL_QUADS;
import static javax.media.opengl.GL.*;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

public class GraphicsEventListener implements GLEventListener {

    // members
    private GL2 gl;
    private GLU glu;
    private BlockingQueue<Runnable> extraRendering;
    private ArrayList<Supplier<String>> messages;

    //region Singleton
    private static GraphicsEventListener instance = new GraphicsEventListener();

    public static GraphicsEventListener getInstance() { return instance;}

    private GraphicsEventListener() {
        gl = null;
        glu = new GLU();
        extraRendering = new LinkedBlockingDeque<>();
        messages = new ArrayList<>();
    }
    //endregion

    public static void AddDebugMessageGenerator(Supplier<String> s) {
        instance.messages.add(s);
    }

    public static void AddRendering(Runnable r) {
        instance.extraRendering.add(r);
    }

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
        gl.glClearDepth(1.0f);                    // Depth Buffer Setup
        gl.glEnable(GL_DEPTH_TEST);                  // Enables Depth Testing
        gl.glDepthFunc(GL2.GL_LEQUAL);               // The Type Of Depth Testing To Do
        // Really Nice Perspective Calculations
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        // Texture
        gl.glEnable(GL_TEXTURE_2D);

        gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, new float[]{1f, 0f, 0f, 1.0f}, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, new float[]{1f, 0f, 0f, 1.0f}, 0);
        gl.glEnable(GL2.GL_LIGHT0);

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


    void renderDebug() {
        TextRenderer textRenderer = new TextRenderer(new Font("Verdana", Font.BOLD, 12));
        textRenderer.beginRendering(1366, 768);
        textRenderer.setColor(Color.green);
        textRenderer.setSmoothing(true);
        for (int i = 0; i < messages.size(); i++) {
            textRenderer.draw(messages.get(i).get(), 25, 25 + 25 * i);
        }
        textRenderer.endRendering();

        textRenderer.setColor(Color.WHITE);
        //gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

    }

    void Enable3D() {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        gl.glDepthMask(true);
        gl.glEnable(GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glEnable(GL_TEXTURE_2D);
        gl.glEnable(GL_LIGHTING);
        gl.glEnable(GL_CULL_FACE);

        gl.glMatrixMode(GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    }

    void Enable2D() {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        gl.glDepthMask(false);
        gl.glDisable(GL_DEPTH_TEST);
        gl.glDisable(GL_TEXTURE_2D);
        gl.glDisable(GL_LIGHTING);
        gl.glDisable(GL_CULL_FACE);

        gl.glMatrixMode(GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, 768, 1366, 0);
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glClear(GL_DEPTH_BUFFER_BIT);
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        updateGL(drawable);

        // clear drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();

        // Set look at from players view point using world manager
        WorldManager.SetLookAt(glu);

        //gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, new float[]{0, 0, 0}, 0);

        gl.glTexParameteri(GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glTexParameteri(GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);

        //gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, new float[]{0.8f, 0.8f, 0.8f, 1.0f}, 0);

        // Draw the world
        WorldManager.Render();


        // Enable2D();
        renderDebug();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        updateGL(drawable);

        if (height <= 0) {
            height = 1;
        }
        float h = (float) width / (float) height;
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
    }
}
