package client.components;

import client.utilities.Assets;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Renderer extends GameComponent {
    private Integer width;
    private Integer height;
    private String imagePath;
    private double boxSize;

    private String text = "";
    private boolean withImage = true;
    private Color color;

    public void setTexture(String newTexture) {
        this.imagePath = newTexture;
    }

    // for rendering text instead of images
    public Renderer(Integer width, Integer height, String text, Color color) {
        this.withImage = false;
        this.text = text;
        this.color = color;
        setDimensions(width, height);
    }

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

    public void render(Graphics2D g2d) {
        Point2D.Float position = ((Transform)this.gameObject.getComponent(Transform.Key())).position;
        int x = (int) Math.round(position.getX());
        int y = (int) Math.round(position.getY());

        if (this.withImage) {
            BufferedImage image = getImage();
            g2d.drawImage(image, null, x, y);
        } else {
            g2d.setColor(color);
            g2d.drawString(this.text, x, y);
            
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String key() {
        return "Renderer";
    }

    public static String Key() {
        return "Renderer";
    }

    @Override
    public Renderer cloneShallow() {
        if (this.withImage) {
            return new Renderer(this.imagePath, this.width, this.height);
        }
        return new Renderer(this.width, this.height, this.text, this.color);
    }
    @Override
    public Renderer cloneDeep() {
        if (this.withImage) {
            return new Renderer(String.valueOf(this.imagePath), Integer.valueOf(this.width), Integer.valueOf(this.height));
        }
        return new Renderer(Integer.valueOf(this.width), Integer.valueOf(this.height), String.valueOf(this.text), new Color(this.color.getRGB()));
    }
}
