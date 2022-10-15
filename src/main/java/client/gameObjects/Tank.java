package client.gameObjects;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Position;

import client.controls.ControlInput;
import client.controls.ControlListener;
import client.gameObjects.projectiles.Projectile;
import client.panels.MainPanel;
import client.panels.StaticPanel;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Float;

import java.util.concurrent.TimeUnit;

public class Tank extends GameObject implements ControlListener, Prototype {
    private float movementSpeed = 5f;
    private float rotationSpeed = 3f;
    private static int tankSize = 60;
    private Point2D.Float movement = new Point2D.Float(0, 0);

    BufferedImage tankImage;

    public Tank(String imagePath) {
        this(100, 100, 0, imagePath);
    }

    public Tank(float x, float y, float angle, String imagePath) {
        super(imagePath, tankSize, true);
        setPosition(x, y);
        this.rotation = angle;
    }

    public Tank(float x, float y, float angle, BufferedImage texture) {
        super(texture, tankSize, true);
        setPosition(x, y);
        this.rotation = angle;
    }

    public Tank listensToInput() {
        ControlInput.addControlListener(this);
        return this;
    }

    public void drive(float direction) {
        float x = -direction * movementSpeed * (float) Math.sin(Math.toRadians(rotation));
        float y = direction * movementSpeed * (float) Math.cos(Math.toRadians(rotation));
        position.setLocation(position.getX() + x, position.getY() + y);
    }

    public void rotate(float direction) {
        rotation += (direction * rotationSpeed);
        rotation %= 360;
    }

    @Override
    public void update() {
        drive(movement.y);
        rotate(movement.x);
    }

    @Override
    public void onMove(Point2D.Float input) {
        movement = input;
    }

    @Override
    public void onClone() {
        Tank clone = this.cloneShallow();
        MainPanel.addObject(clone);
        clone.movement.y = -1;
        clone.movement.x = 0;
        clone.explode(1000);
        //System.out.println(System.identityHashCode(clone)); //object hashcodes
        //System.out.println(System.identityHashCode(this));
    }

    @Override
    public Tank cloneShallow() {
        return new Tank(position.x, position.y, rotation, getTexture());
    }

    @Override
    public Tank cloneDeep() {
        Point2D.Float pos = new Point2D.Float(position.x, position.y);
        Point2D.Float rot = new Point2D.Float(rotation, rotation); //workaround for new float
        ColorModel cm = getTexture().getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster =  getTexture().copyData(null);
        BufferedImage img = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        return new Tank(pos.x, pos.y, rot.x, img);
    }

    public void explode(int delay) {
        Tank tank = this;
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Projectile projectile = new Projectile(position.x + tankSize / 2, position.y + tankSize / 2, 0, "images/tank-projectile.png", "fragmenting");
                projectile.beforeCollide();
                MainPanel.deleteObject(tank);
            }
        });
        timer.setRepeats(false);
        timer.start();
        

    }

    @Override
    public void renderOn(Graphics2D g2d) {
        // Float corner = getUpperLeftCorner();
        // g2d.setStroke(new BasicStroke(10));
        // g2d.setColor(Color.RED);
        // g2d.drawLine((int) corner.x, (int) corner.y, (int) corner.x, (int) corner.y);
        g2d.drawImage(getImage(), null, (int) Math.round(position.getX()), (int) Math.round(position.getY()));
    }

    @Override
    public void onFire(char keyName) {
        Point2D.Float currentPosition = getPosition();
        String projectileImage = "images/tank-projectile.png";

        // projectile spawns at the center of the tank
        float xCoords = currentPosition.x + tankSize / 2;
        float yCoords = currentPosition.y + tankSize / 2;

        String projectileAlgorithm = "";

        if (keyName == 'n') {
            projectileAlgorithm = "straight";
        } else if (keyName == 'm') {
            projectileAlgorithm = "fragmenting";
        }

        MainPanel.addObject(new Projectile(xCoords, yCoords, this.rotation, projectileImage, projectileAlgorithm));
    }
}
