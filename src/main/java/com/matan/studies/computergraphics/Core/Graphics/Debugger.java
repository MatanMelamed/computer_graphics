// Matan Melamed 205973613
package com.matan.studies.computergraphics.Core.Graphics;

import com.jogamp.opengl.util.awt.TextRenderer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Supplier;

public class Debugger {
    private ArrayList<Supplier<String>> messages = new ArrayList<>();

    private static Debugger instance = new Debugger();
    private static TextRenderer textRenderer;

    private Debugger(){}

    public static void AddDebug(Supplier<String> s){
        instance.messages.add(s);
    }

    public static void Initialize(){
        textRenderer = new TextRenderer(new Font("Verdana", Font.BOLD, 12));
    }

    static void Dispose(){
        textRenderer.dispose();
    }

    public static void Render(GL2 gl) {

        gl.glDisable(GL.GL_DEPTH_TEST);
        gl.glDisable(GL.GL_TEXTURE_2D);


        textRenderer.beginRendering(WindowManager.GetWindowWidth(), WindowManager.GetWindowHeight());
        textRenderer.setColor(Color.green);
        for (int i = 0; i < instance.messages.size(); i++) {
            textRenderer.draw("hei", 25, 25 + 25 * i);
        }
        textRenderer.setColor(Color.WHITE);
        textRenderer.endRendering();


        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_TEXTURE_2D);
    }
}
