package client.gameObjects;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Position;

import client.ControlInput;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.awt.geom.*;

public class Tank extends GameObject {
    public float movementSpeed = 5f;
    public float rotationSpeed = 1f;

    public ControlInput movementInput = ControlInput.getInstance();

    BufferedImage tankImage;

    public Tank(String imagePath) {
        this(100, 100, 0, imagePath);
    }

    public Tank(float x, float y, float angle, String imagePath) {
        super(imagePath, 100, true);
        setPosition(x, y);
        this.rotation = angle;
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
        drive(movementInput.y);
        rotate(movementInput.x);
    }
}
