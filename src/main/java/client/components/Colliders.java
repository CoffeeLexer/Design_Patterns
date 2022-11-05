package client.components;

import client.gameObjects.GameObject;
import client.gameObjects.Projectile;
import client.gameObjects.Tank;
import client.gameObjects.Wall;
import network.server.SEngine;

import java.awt.geom.Point2D;
import java.util.function.BiFunction;

public class Colliders {
    public static BiFunction<GameObject, GameObject, Void> tank = (me, other) -> {
        if(Wall.class.isAssignableFrom(other.getClass()))
        {
            Collider collider = me.getComponent(Collider.Key());
            Collider colliderOther = other.getComponent(Collider.Key());
            Transform transform = me.getComponent(Transform.Key());
            Transform transformOther = other.getComponent(Transform.Key());

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
        if(Tank.class.isAssignableFrom(other.getClass()))
        {
            Transform transform = me.getComponent(Transform.Key());
            Transform transformOther = other.getComponent(Transform.Key());

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
            transform.position.x += v.x;
            transform.position.y += v.y;
        }
        return null;
    };
    public static BiFunction<GameObject, GameObject, Void> projectile = (me, other) -> {
        if(Wall.class.isAssignableFrom(other.getClass())) {
            SEngine.GetInstance().Destroy(me);
        }
        if(Tank.class.isAssignableFrom(other.getClass())) {
            Projectile p = (Projectile)me;
            if(p.owner.uniqueID != other.uniqueID) {
                SEngine.GetInstance().Destroy(me);
                ((Tank) other).setDamage(1);
            }
        }
        return null;
    };
}
