package Graphics;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class BaseRenderer implements GLEventListener {

    protected Frame frame;
    protected GLCanvas canvas;
    protected FPSAnimator animator;


    BaseRenderer() {
        canvas = new GLCanvas();
        canvas.addGLEventListener(this);

        animator = new FPSAnimator(canvas, 60);
        animator.add(canvas);
    }

    protected void initialize(String appName, int width, int height) {
        frame = new Frame(appName);
        frame.setSize(width, height);
        frame.setLayout(new BorderLayout());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(() -> {
                    animator.stop();
                    System.exit(0);
                }).start();
            }
        });
        frame.add(canvas, java.awt.BorderLayout.CENTER);
        frame.validate();
        frame.setVisible(true);
    }

    protected void start() {
        animator.start();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        float h = (float) height / (float) width;

        gl.glMatrixMode(GL2.GL_PROJECTION);

        gl.glLoadIdentity();

        if (h < 1)
            gl.glFrustum(-1.0f, 1.0f, -h, h, 1.0f, 60.0f);
        else {
            h = 1.0f / h;
            gl.glFrustum(-h, h, -1.0f, 1.0f, 1.0f, 60.0f);
        }

        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }
}
