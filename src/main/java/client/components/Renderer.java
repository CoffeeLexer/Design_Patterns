package client.components;

import client.utilities.Assets;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Renderer extends GameComponent {
    private Integer width;
    private Integer height;
    private String imagePath;
    private float boxSize = 0;
    public void setTexture(String newTexture) {
        imagePath = newTexture;
    }
    public Point2D.Float getCenter() {
        Transform transform = gameObject.getComponent(Transform.Key());
        if(transform == null) return new Point2D.Float();
        return new Point2D.Float(transform.position.x + boxSize / 2, transform.position.y + boxSize / 2);
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
        BufferedImage image = getImage();
        Point2D.Float position = ((Transform)this.gameObject.getComponent(Transform.Key())).position;
        g2d.drawImage(image, null, (int) position.x, (int) position.y);
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(5));
//  DEBUG STUFF MIGHT NEED LATER
//        g2d.drawLine((int)position.x, (int)position.y, (int)position.x, (int)position.y);
//        g2d.drawLine((int)position.x, (int)position.y, (int)position.x + width, (int)position.y);
//        g2d.drawLine((int)position.x, (int)position.y, (int)position.x, (int)position.y + height);

//        List<Point2D.Float> corners = getCorners();
//        for(int i = 0; i < corners.size() - 1; i++) {
//            var p0 = corners.get(i);
//            var p1 = corners.get(i +1);
//            g2d.drawLine((int)p0.x, (int)p0.y, (int)p1.x, (int)p1.y);
//        }

//        Point2D.Float center = getCenter();
//        g2d.drawOval((int)position.x - 2, (int)position.y - 2,5, 5);
//        g2d.drawOval((int)center.x, (int)center.y - 2,5, 5);
    }
    public List<Point2D.Float> getCorners() {
        List<Point2D.Float> corners = new ArrayList<>();
        corners.add(new Point2D.Float(-width, height));
        corners.add(new Point2D.Float(width, height));
        corners.add(new Point2D.Float(width, -height));
        corners.add(new Point2D.Float(-width, -height));
        var center = getCenter();
        Transform transform = gameObject.getComponent(Transform.Key());
        if(transform == null) return corners;
        float rotation = (float) Math.toRadians(transform.rotation);
        float c = (float)Math.cos(rotation);
        float s = (float)Math.sin(rotation);
        for(int i = 0; i < corners.size(); i++)
        {
            Point2D.Float vertex = corners.get(i);
            float x = vertex.x * c - vertex.y * s;
            float y = vertex.x * s + vertex.y * c;
            corners.set(i, new Point2D.Float(x / 2 + center.x, y / 2 + center.y));
        }
        return corners;
    }

    @Override
    public String key() {
        return "Renderer";
    }
    public static String Key() {
        return "Renderer";
    }

    @Override
    public Renderer clone() {
        return new Renderer(this.imagePath, this.width, this.height);
    }
}
