package game.data;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImageOp;
import java.io.Serializable;

public class GameObject implements Serializable {
    public int id = -1;
    public Vector position = new Vector(0, 0);
    public Vector dimensions = new Vector(100, 100);
    // 0, 0 - TOP LEFT
    // 0.5, 0.5 - CENTER
    // 1, 1 - BOTTOM RIGHT
    public Vector pivot = new Vector(0.5f, 0.5f);
    public double rotation;
    public String texture;
    public Tag tag;


    /**
     * @param delta float value of seconds from last frame update
     */
    public void update(float delta) {}

    public void render(Graphics2D g2d) {
        var textureData = Textures.getTexture(texture);
        AffineTransform transform = AffineTransform.getTranslateInstance(position.x, position.y);
        transform.rotate(rotation);
        transform.scale(dimensions.x / textureData.getWidth(), dimensions.y / textureData.getHeight());
        transform.translate(-textureData.getWidth() * pivot.x, -textureData.getHeight() * pivot.y);
        BufferedImageOp imageOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        //g2d.drawImage(textureData, imageOp, (int)position.x, (int)position.y);
        g2d.drawImage(textureData, imageOp, 0, 0);
    }

    //  Dynamic object will be sent over UDP every frame
    //      - fast protocol
    //      - client renders every second
    //      - not all packets reach destination

    //  Static object will be sent over TCP once it is required
    //      - slow protocol
    //      - client renders all static objects into one pane
    //      - all packets reach destination
    public enum Tag {
        Dynamic,
        Static,
    }
}
