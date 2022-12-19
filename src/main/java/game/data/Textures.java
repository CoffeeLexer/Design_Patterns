package game.data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Textures {
    private static Map<String, BufferedImage> textures = new HashMap<>();

    // Read an image into BufferedImage texture from a file path
    public static BufferedImage getTexture(String filePath) {
        if(textures.containsKey(filePath)) {
            return textures.get(filePath);
        }
        else {
            BufferedImage image = null;

            try {
                image = ImageIO.read(new File(filePath));
                BufferedImage background = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = background.createGraphics();
                g2d.fillRect(0, 0, background.getWidth(), background.getHeight());
                g2d.drawImage(image, 0, 0, null);
                g2d.dispose();
                image = background;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            textures.put(filePath, image);
            return image;
        }
    }
}
