package client.gameObjects;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Position;

import client.controls.ControlInput;
import client.controls.ControlListener;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Float;

public class Projectile extends GameObject {
    private float movementSpeed = 15; // pixels per frame
    private float flyingXSpeed = 0;
    private float flyingYSpeed = 0;

    public Projectile(float x, float y, float angle, String imagePath) {
      super(imagePath, 30, true);
      setPosition(x, y);
      this.rotation = angle - 90; // image is horizontal so rotate it by 90 degrees

      float angleRadians = (angle) % 360;
      Point2D.Float direction = getTrajectory();
      System.out.println("calculated trajectory:");
      System.out.println(new Point2D.Float(direction.x, direction.y));

      this.flyingXSpeed = direction.x * movementSpeed * (float) Math.sin(Math.toRadians(angleRadians));
      this.flyingYSpeed = direction.y * movementSpeed * (float) Math.cos(Math.toRadians(angleRadians));
      System.out.println("Projectile speed (x, y):");
      System.out.println(this.flyingXSpeed);
      System.out.println(this.flyingYSpeed);
    }

    private void fly() {
      position.setLocation(position.getX() + flyingXSpeed, position.getY() + flyingYSpeed);
    }

    private Point2D.Float getTrajectory() {
      float rot = this.rotation + 90; // adjusted rotation for initial image rotation
      int xDirection;
      int yDirection;

      if ((rot >= 0 && (rot < 90 || rot > 270)) || (rot <= 0 && (rot < -270 || rot > -90))) {
        yDirection = -1;
      } else if ((rot >= 90 && rot < 270) || (rot <= 0 && (rot > -270 || rot < -90))) {
        yDirection = 1;
      } else {
        yDirection = 0;
      }

      if ((rot > 180 && rot < 360) || (rot > -180 && rot < 0)) {
        xDirection = -1;
      } else if ((rot > 0 && rot < 180) || (rot < -180 && rot > -360)) {
        xDirection = 1;
      } else {
        xDirection = 0;
      }

      return new Point2D.Float(xDirection, yDirection);
    }

    @Override
    public void update() {
      fly();
    }
}
