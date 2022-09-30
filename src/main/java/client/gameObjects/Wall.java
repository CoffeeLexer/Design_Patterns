package client.gameObjects;

import java.awt.*;
import java.awt.image.*;

public class Wall extends GameObject {

    public Wall(String imagePath, int width) {
        super(imagePath, width, width);
        boxSize = width;
    }

    public void renderOn(Graphics2D g2d) {
        g2d.drawImage(getImage(), null, (int) position.x,(int) position.y);
    }

    public BufferedImage getImage() {
        return getTexture();
    }
}