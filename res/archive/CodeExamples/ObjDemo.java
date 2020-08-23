package CodeExamples;
/* This code was written by: Oren Kapah */

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import com.jogamp.newt.Window;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;


public class ObjDemo extends KeyAdapter implements GLEventListener {
    private float xrot;        // X Rotation ( NEW )
    private float yrot;        // Y Rotation ( NEW )
    private float zrot;        // Z Rotation ( NEW )
    private Texture texture;
    private objDemo.WwavefrontObjectLoader_DisplayList axe;

    static GLU glu = new GLU();
    static GLCanvas canvas = new GLCanvas();
    static Frame frame = new Frame("Jogl 3D Shape/Rotation");
    static Animator animator = new Animator(canvas);

    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();  // Reset The View
        gl.glTranslatef(0.0f, 0.0f, -5.0f);

        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);

        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        texture.bind(gl);

        gl.glScalef(100, 100, 100);
        axe.drawModel(gl);

        xrot += 0.03f;
        yrot += 0.02f;
        zrot += 0.04f;
    }

    public void displayChanged(GLAutoDrawable gLDrawable,
                               boolean modeChanged, boolean deviceChanged) {
    }

    public void init(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL2.GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glDepthFunc(GL2.GL_LEQUAL);               // The Type Of Depth Testing To Do
        // Really Nice Perspective Calculations
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            String filename = "res/wavefront_objs/axe.jpg"; // the FileName to open
            texture = TextureIO.newTexture(new File(filename), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

        axe = new objDemo.WwavefrontObjectLoader_DisplayList("/wavefront_objs/axe.obj");

        // Keyboard
        if (gLDrawable instanceof Window) {
            Window window = (Window) gLDrawable;
            window.addKeyListener(this);
        } else if (GLProfile.isAWTAvailable() && gLDrawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) gLDrawable;
            new AWTKeyAdapter(this, gLDrawable).addTo(comp);
        }
    }

    public void reshape(GLAutoDrawable gLDrawable, int x,
                        int y, int width, int height) {
        GL2 gl = gLDrawable.getGL().getGL2();
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

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            exit();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void exit() {
        animator.stop();
        frame.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        canvas.addGLEventListener(new ObjDemo());
        frame.add(canvas);
        frame.setSize(640, 480);
//		frame.setUndecorated(true);
//		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        frame.setVisible(true);
        animator.start();
        canvas.requestFocus();
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub

    }
}
