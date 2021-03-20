// Matan Melamed 205973613
package com.matan.studies.computergraphics.Models;

import com.matan.studies.computergraphics.Core.Graphics.WindowManager;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ImageResource {

    private Texture texture = null;

    private BufferedImage image = null;

    public ImageResource(String path) {
        URL url = ImageResource.class.getResource("/images/" + path);

        try {
            image = ImageIO.read(url);
        } catch (Exception e) {

            System.err.println("Cannot find image in /images/" + path);
            e.printStackTrace();
        }

        if (image != null) {
            image.flush();
        }
    }

    public Texture getTexture() {
        if (image == null) {
            return null;
        }

        if (texture == null) {
            texture = AWTTextureIO.newTexture(WindowManager.GetProfile(), image, true);
        }

        return texture;
    }
}
