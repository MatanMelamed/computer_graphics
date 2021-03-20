// Matan Melamed 205973613
package com.matan.studies.computergraphics.Core.Graphics;

import com.matan.studies.computergraphics.Core.InputManager;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.newt.event.awt.AWTMouseAdapter;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

import static javax.media.opengl.GL.*;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

public class Renderer implements GLEventListener {

    // members
    private GL2 gl = null;
    private GLU glu = new GLU();
    private Runnable initCallBack = null;
    private Runnable displayCallBack = null;

    private boolean debuggerEnabled;

    //region Singleton
    private static Renderer instance = new Renderer();

    public static Renderer getInstance() { return instance;}

    private Renderer() {}
    //endregion

    public static void SetDisplayCallBack(Runnable r) {
        instance.displayCallBack = r;
    }

    public static void SetInitCallBack(Runnable r) {
        instance.initCallBack = r;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        updateGL(drawable);

        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
        gl.glClearDepth(1.0f);                    // Depth Buffer Setup
        gl.glEnable(GL_DEPTH_TEST);                  // Enables Depth Testing
        gl.glDepthFunc(GL2.GL_LEQUAL);               // The Type Of Depth Testing To Do
        gl.glEnable(GL2.GL_CULL_FACE); //enable cull face

        // Really Nice Perspective Calculations
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        // Texture
        gl.glEnable(GL_TEXTURE_2D);

        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

        gl.glEnable(GL2.GL_LIGHTING);

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

        if (initCallBack != null) {
            initCallBack.run();
        }

        if (debuggerEnabled) {
            Debugger.Initialize();
        }
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        updateGL(drawable);

        // clear drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        if (displayCallBack != null) {
            displayCallBack.run();
        }

        if (debuggerEnabled) {
            Debugger.Render(gl);
        }
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
        glu.gluPerspective(70.0f, h, 0.005f, 100.0);
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        updateGL(drawable);
        if (debuggerEnabled) {
            Debugger.Dispose();
        }
    }

    private void updateGL(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        Graphics.UpdateGL(gl);
    }

    public static GLU GetGLU() {return instance.glu;}

    public static void EnableDebugger() {
        instance.debuggerEnabled = true;
    }

    public static void DisableDebugger() {
        instance.debuggerEnabled = false;
    }
}
