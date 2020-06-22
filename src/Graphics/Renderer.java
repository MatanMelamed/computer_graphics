package Graphics;

import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import java.awt.*;


public class Renderer {

    // members
    private Frame frame;
    private GLCanvas canvas;
    private boolean isInit;
    private GLProfile profile;

    //region Singleton
    private static Renderer instance = new Renderer();

    private Renderer() {
        canvas = new GLCanvas();
        isInit = false;
        profile = GLProfile.get("GL2");
    }
    //endregion

    // Class API

    public static void Initialize(String appName, int width, int height) {
        instance.initialize(appName, width, height);
    }

    public static void Render() {
        instance.render();
    }

    public static GLProfile GetProfile() {
        return instance.profile;
    }


    // Private implementations

    private void initialize(String appName, int width, int height) {
        isInit = true;
        frame = new Frame(appName);
        frame.setSize(width, height);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, java.awt.BorderLayout.CENTER);
        frame.validate();
        frame.setVisible(true);
        canvas.addGLEventListener(GraphicsEventListener.getInstance());
        canvas.requestFocus();
    }

    private void render() {
        if (!isInit) {
            System.out.println("\n\nERROR :: Tried to render without initializing renderer.\n\n");
            return;
        }
        canvas.display();
    }

    public static int GetWindowWidth(){
        return instance.canvas.getWidth();
    }

    public static int GetWindowHeight(){
        return instance.canvas.getHeight();
    }
}
