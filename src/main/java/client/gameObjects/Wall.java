package client.gameObjects;

import java.awt.*;
import java.awt.image.*;

public class Wall extends GameObject implements Prototype {

    public Wall(String imagePath, int width) {
        super(imagePath, width, width);
        boxSize = width;
    }

    public Wall(BufferedImage texture, int width) {
        super(texture, width, width);
        boxSize = width;
    }

    public void renderOn(Graphics2D g2d) {
        g2d.drawImage(getImage(), null, (int) position.x,(int) position.y);
    }

    public BufferedImage getImage() {
        return getTexture();
    }

    @Override
    public Wall cloneShallow() {
        return new Wall(getTexture(), width);
    }
    @Override
    public Wall cloneDeep() {
        ColorModel cm = getTexture().getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster =  getTexture().copyData(null);
        BufferedImage img = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        int width = Integer.valueOf(this.width);
        return new Wall(img, width);
    }
}