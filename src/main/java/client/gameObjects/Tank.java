package client.gameObjects;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Position;

import client.components.Controller;
import client.components.Renderer;
import client.components.Transform;
import client.controls.ControlInput;
import client.controls.ControlListener;
import client.panels.MainPanel;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Float;

public class Tank extends GameObject implements ControlListener {



    public Tank(String imagePath) {
        this(100, 100, 0, imagePath);
        tag = "Dynamic";
    }

    public Tank(float x, float y, float angle, String imagePath) {
        addComponent(new Transform().setPosition(x, y).setRotation(angle));
        addComponent(new Renderer(imagePath, Controller.TankSize(), true));
        tag = "Dynamic";
    }

    public Tank listensToInput() {
        addComponent(new Controller());
        ControlInput.addControlListener(this);
        return this;
    }

    @Override
    public void onMove(Point2D.Float input) {
        Controller controller = getComponent(Controller.Key());
        controller.movement = input;
    }

    @Override
    public void onFire() {
        Transform transform = getComponent(Transform.Key());
        Point2D.Float currentPosition = transform.getPosition();
        String projectileImage = "images/tank-projectile.png";

        // projectile spawns on the center of the tank
        float xCoords = currentPosition.x + Controller.TankSize() / 2;
        float yCoords = currentPosition.y + Controller.TankSize() / 2;

        MainPanel.addObject(new Projectile(xCoords, yCoords, transform.rotation, projectileImage));
    }
}
