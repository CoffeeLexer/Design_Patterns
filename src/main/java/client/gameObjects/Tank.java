package client.gameObjects;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Position;

import client.controls.ControlInput;
import client.controls.ControlListener;
import client.gameObjects.projectiles.Projectile;
import client.gameObjects.tankDecorators.ITankDecorator;
import client.panels.MainPanel;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Float;

import java.util.concurrent.TimeUnit;

public class Tank extends GameObject implements ControlListener, ITankDecorator {
    private double maxHP = 3;
    private double currentHP = 2.5;
    private double shieldAmount = 0;
    private float movementSpeed = 5f;
    private float rotationSpeed = 3f;
    private static int tankSize = 60;
    private Point2D.Float movement = new Point2D.Float(0, 0);

    BufferedImage tankImage;

    public Tank(String imagePath) {
        this(100, 100, 0, imagePath);
    }

    public Tank(Tank tank) {
        super(tank.imagePath, tankSize, true);
        setPosition(tank.getPosition().x, tank.getPosition().y);
        this.rotation = tank.getAngle();
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

    public double getShieldAmount() {
        return this.shieldAmount;
    }

    public double getMaxHP() {
        return this.maxHP;
    }

    public double getCurrentHP() {
        return this.currentHP;
    }

    public void setShield(double amount) {
        this.shieldAmount = amount;
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

    @Override
    public void onShieldActivated() {
        // create shield toggle functionality for Decoration testing
        if (this.shieldAmount > 0) {
            this.shieldAmount = 0;
            decorate(this.currentHP, this.maxHP);
        } else {
            this.shieldAmount = 1;
            decorate(this.currentHP + this.shieldAmount, this.maxHP, "blue");
        }

        decorate(this.shieldAmount);
    }

    // these calls decorators that override these functions
    @Override public void decorate(String text) {}
    @Override public void decorate(double amount) {}
    @Override public void decorate(double a, double b) {}
    @Override public void decorate(double a, double b, String modifier) {}
}
