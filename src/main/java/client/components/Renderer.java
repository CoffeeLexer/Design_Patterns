package client.components;

import client.Assets;
import client.gameObjects.GameComponent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Renderer extends GameComponent {
    private Integer width;
    private Integer height;
    private String imagePath;
    private double boxSize;

    public Renderer(String imagePath, Integer width, Integer height) {
        this.imagePath = imagePath;
        setDimensions(width, height);
    }
    public Renderer(String imagePath) {
        this.imagePath = imagePath;
        BufferedImage texture = Assets.getInstance().getFile(imagePath);
        setDimensions(texture.getWidth(), texture.getHeight());
    }
    public Renderer(String imagePath, int size, boolean isWidth) {
        this.imagePath = imagePath;
        BufferedImage texture = Assets.getInstance().getFile(imagePath);
        if (isWidth) {

            setDimensions(size, (int) ((float) size * texture.getHeight() / texture.getWidth()));
        } else {
            setDimensions((int) ((float) size * texture.getWidth() / texture.getHeight()), size);
        }
    }

    public void setDimensions(Integer width, Integer height) {
        this.height = height;
        this.width = width;
        boxSize = (int) Math.round(Point2D.distance(0, 0, width, height));
    }
    public BufferedImage getImage() {
        BufferedImage newImageFromBuffer = new BufferedImage((int) Math.ceil(boxSize), (int) Math.ceil(boxSize),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = newImageFromBuffer.createGraphics();

        AffineTransform at = new AffineTransform();

        float rotation = ((Transform)this.gameObject.getComponent(Transform.Key())).getAngle();

        at.setToRotation(Math.toRadians(rotation), boxSize / 2, boxSize / 2);
        at.translate((boxSize - width) / 2, (boxSize - height) / 2);
        graphics2D.setTransform(at);

        BufferedImage texture = Assets.getInstance().getFile(imagePath);
        graphics2D.drawImage(texture, 0, 0, width, height, null);
        graphics2D.dispose();

        return newImageFromBuffer;
    }
    public void renderOn(Graphics2D g2d) {
        BufferedImage image = getImage();
        Point2D.Float position = ((Transform)this.gameObject.getComponent(Transform.Key())).position;
        g2d.drawImage(image, null, (int) Math.round(position.getX()), (int) Math.round(position.getY()));
    }

    @Override
    public String key() {
        return "Renderer";
    }
    public static String Key() {
        return "Renderer";
    }
}
