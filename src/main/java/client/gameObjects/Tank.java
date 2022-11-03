package client.gameObjects;

import client.components.*;
import network.server.SEngine;

import java.awt.geom.Point2D;

public class Tank extends GameObject {
    public Tank(String imagePath) {
        this(100, 100, 15, imagePath);
    }
    public Tank(float x, float y, float angle, String imagePath) {
        addComponent(new Transform().setPosition(x, y).setRotation(angle));
        addComponent(new Renderer(imagePath, 50, true));
        addComponent(new ConstantSpeed(0.0f));
        addComponent(new ConstantRotation(0.0f));
        addComponent(new Weapon());
        addComponent(Collider.fromTexture(getComponent(Renderer.Key()), getComponent(Transform.Key())));
        ((Collider)getComponent(Collider.Key())).setFunction(gameObject -> {
            if(gameObject.getClass().equals(Wall.class))
            {
                Collider collider = this.getComponent(Collider.Key());
                Collider colliderOther = gameObject.getComponent(Collider.Key());
                Transform transform = this.getComponent(Transform.Key());
                Transform transformOther = gameObject.getComponent(Transform.Key());

                var fixedOffset = new Point2D.Float(transform.position.x - transformOther.position.x,  transform.position.y - transformOther.position.y);
                var state = Math.abs(fixedOffset.x) - Math.abs(fixedOffset.y);
                Point2D.Float v = null;
                if(state < 0) {
                    v = new Point2D.Float(0, fixedOffset.y / Math.abs(fixedOffset.y));
                }
                else if(state > 0) {
                    v = new Point2D.Float(fixedOffset.x / Math.abs(fixedOffset.x), 0);
                }
                else {
                    v = new Point2D.Float(fixedOffset.x / Math.abs(fixedOffset.x), fixedOffset.y / Math.abs(fixedOffset.y));
                }
                while (collider.isColliding(colliderOther) && colliderOther.isColliding(collider))
                {
                    transform.position.x += v.x;
                    transform.position.y += v.y;
                }
            }
        });
        tag = Tag.Dynamic;
    }
}
