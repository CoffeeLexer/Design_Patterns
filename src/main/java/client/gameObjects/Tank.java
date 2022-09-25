package client.gameObjects;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Position;

import client.MainPanel;
import client.controls.ControlInput;
import client.controls.ControlListener;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Float;

public class Tank extends GameObject implements ControlListener {
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
    public void onFire() {
        Point2D.Float currentPosition = getPosition();
        String projectileImage = "images/tank-projectile.png";

        // projectile spawns on the center of the tank
        float xCoords = currentPosition.x + tankSize / 2;
        float yCoords = currentPosition.y + tankSize / 2;

        MainPanel.addObject(new Projectile(xCoords, yCoords, this.rotation, projectileImage));
    }
}
