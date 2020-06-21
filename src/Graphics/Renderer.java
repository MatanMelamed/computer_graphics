package Graphics;

import com.jogamp.newt.Window;
import com.jogamp.newt.event.awt.AWTKeyAdapter;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLProfile;


public class Renderer extends BaseRenderer {

    //region Singleton
    private static Renderer instance = new Renderer();

    public static Renderer getInstance() { return instance;}

    private Renderer() {
        super();
    }
    //endregion

    private boolean isInit = false;

    public static void Initialize(String name, int width, int height) {
        instance.isInit = true;
        instance.initialize(name, width, height);
    }

    public static void Run() {
        if (instance.isInit) {
            instance.start();
        } else {
            System.out.println("Cannot start renderer without init.");
        }
    }


    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearDepth(1.0f); // Depth Buffer Setup
        gl.glEnable(GL2.GL_DEPTH_TEST); // Enables Depth Testing
        gl.glDepthFunc(GL2.GL_LEQUAL); // The Type Of Depth Testing To Do

        // Really Nice Perspective Calculations
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        // keyboard
        if (drawable instanceof Window) {
            Window window = (Window) drawable;
            window.addKeyListener(this);
        } else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) drawable;
            new AWTKeyAdapter(this, drawable).addTo(comp);
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        // Get the GL corresponding to the drawable we are animating
        GL2 gl = drawable.getGL().getGL2();

        // red, green, blue and alpha values to clear screen with - 0,0,0,0 is white
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
    }

}
