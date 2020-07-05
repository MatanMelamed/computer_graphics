package Core.Graphics;

import com.jogamp.opengl.util.awt.TextRenderer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Supplier;

public class Debugger {
    ArrayList<Supplier<String>> messages = new ArrayList<>();

    private static Debugger instance = new Debugger();

    private Debugger(){}

    public static void AddDebug(Supplier<String> s){
        instance.messages.add(s);
    }

    public static void Render(GL2 gl) {

        gl.glDisable(GL.GL_DEPTH_TEST);
        gl.glDisable(GL.GL_BLEND);
        gl.glDisable(GL.GL_TEXTURE_2D);

        TextRenderer textRenderer = new TextRenderer(new Font("Verdana", Font.BOLD, 12));
        textRenderer.beginRendering(1366, 768);
        textRenderer.setColor(Color.green);
        textRenderer.setSmoothing(true);
        for (int i = 0; i < instance.messages.size(); i++) {
            textRenderer.draw(instance.messages.get(i).get(), 25, 25 + 25 * i);
        }
        textRenderer.endRendering();
        textRenderer.setColor(Color.WHITE);

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_BLEND);
        gl.glEnable(GL.GL_TEXTURE_2D);
    }
}
