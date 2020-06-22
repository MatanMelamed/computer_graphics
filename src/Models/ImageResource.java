package Models;

import Graphics.Renderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageResource {

    private Texture texture = null;

    private BufferedImage image = null;

    public ImageResource(String path) {
        URL url = ImageResource.class.getResource("/images/" + path);

        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
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
            texture = AWTTextureIO.newTexture(Renderer.GetProfile(), image, true);
        }

        return texture;
    }
}
