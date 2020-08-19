package Tests;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.glu.GLU;

import com.jogamp.newt.Window;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class experiment extends KeyAdapter implements GLEventListener {


    private Texture texture;

    static GLU glu = new GLU();
    static GLCanvas canvas = new GLCanvas();
    static Frame frame = new Frame("Jogl 3D Shape/Rotation");
    static Animator animator = new Animator(canvas);

    @Override
    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL2.GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glDepthFunc(GL2.GL_LEQUAL);               // The Type Of Depth Testing To Do
        // Really Nice Perspective Calculations
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        // Texture
        gl.glEnable(GL.GL_TEXTURE_2D);
        try {
            String filename="res/images/concc.jpg"; // the FileName to open
            texture= TextureIO.newTexture(new File( filename ),true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);


        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_LIGHTING);

        // Keyboard
        if (drawable instanceof com.jogamp.newt.Window) {
            com.jogamp.newt.Window window = (Window) drawable;
            window.addKeyListener(this);
        } else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) drawable;
            new AWTKeyAdapter(this, drawable).addTo(comp);
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    void light(GL2 gl){
        // Light
        float	ambient[] = {0.1f,0.1f,0.1f,1.0f};
        float	diffuse[] = {1f,0f,0f,1.0f};

        float	position[] = {0f,2f,0f,1.0f};		// red light on the right side (light 0)

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position, 0);
        gl.glLightf(GL2.GL_LIGHT0, GLLightingFunc.GL_SPOT_CUTOFF, 45.0f);
        gl.glLightfv(GL2.GL_LIGHT0, GLLightingFunc.GL_SPOT_DIRECTION, FloatBuffer.wrap(new float[]{0f, -1f, 0f}));
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        float	material[] = {0.8f,0.8f,0.8f,1.0f};

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();  // Reset The View

        glu.gluLookAt(0, 1, 0, 0, 0.5f, -1, 0, 1, 0);

        light(gl);

        gl.glTranslatef(0.0f, 0.0f, -5.0f);

        gl.glTexParameteri ( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT );
        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT );

        float s =2f;

        texture.bind(gl);
        gl.glBegin(GL2.GL_QUADS);
        // Front Face
        gl.glNormal3f(0,0,1);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1f*s, -1f*s, -1.0f*s);
        gl.glTexCoord2f(2f, 0.0f);
        gl.glVertex3f(1.0f*s, -1.0f*s, -1.0f*s);
        gl.glTexCoord2f(2f, 1.0f);
        gl.glVertex3f(1.0f*s, 1.0f*s, -1.0f*s);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f*s, 1.0f*s, -1.0f*s);

        gl.glNormal3f(0,1,0);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f*s, -s, -1.0f*s);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f*s, -s, 1.0f*s);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f*s, -s, 1.0f*s);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f*s, -s, -1.0f*s);
        gl.glEnd();

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            GL2 gl = drawable.getGL().getGL2();
            if(height <= 0) {
                height = 1;
            }
            float h = (float)width / (float)height;
            gl.glMatrixMode(GL2.GL_PROJECTION);
            gl.glLoadIdentity();
            glu.gluPerspective(50.0f, h, 1.0, 1000.0);
            gl.glMatrixMode(GL2.GL_MODELVIEW);
            gl.glLoadIdentity();
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            exit();
        }
    }

    public static void exit(){
        animator.stop();
        frame.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        canvas.addGLEventListener(new experiment());
        frame.add(canvas);
        frame.setSize(1280, 720);
//		frame.setUndecorated(true);
//		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.setVisible(true);
        animator.start();
        canvas.requestFocus();
    }
}
