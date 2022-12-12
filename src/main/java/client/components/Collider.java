package client.components;

import client.gameObjects.GameObject;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class Collider extends GameComponent implements Prototype {
    public Transform previous = new Transform();
    List<Point2D.Float> vertices = null;
    List<Point2D.Float> axes = null;
    List<Point2D.Float> ranges = null;

    private Collider() {

    }

    public static Collider fromTexture(Renderer renderer)
    {
        Collider collider = new Collider();
        if(renderer == null)
        {
            System.err.println("Collider used in object without renderer!");
            return null;
        }
        collider.vertices = renderer.getCorners();
        collider.axes = new ArrayList<>(collider.vertices.size() - 2);
        collider.ranges = new ArrayList<>(collider.vertices.size() - 2);
        for(int i = 0; i < 2; i++) {
            collider.axes.add(null);
            collider.ranges.add(null);
        }
        collider.updateAxes();
        return collider;
    }
    BiFunction<GameObject, GameObject, Void> function = null;
    public void setFunction(BiFunction<GameObject, GameObject, Void> function) {
        this.function = function;
    }
    public void onCollision(GameObject me, GameObject other)
    {
        if(function == null) return;
        function.apply(me, other);
    }
    public boolean isColliding(Collider other) {
        Transform current = parent.getComponent(Transform.Key());
        if(previous != current)
            previous = current.cloneDeep();
        updateAxes();

        Transform transformThis = parent.getComponent(Transform.Key());
        Transform transformOther = other.parent.getComponent(Transform.Key());

        Point2D.Float offsetVector = new Point2D.Float(
                transformThis.position.x - transformOther.position.x,
                transformThis.position.y - transformOther.position.y);

        var i = axes.iterator();
        var j = ranges.iterator();
        while(i.hasNext() && j.hasNext())
        {
            var axis = i.next();
            var range = j.next();

            float min = (float)Integer.MAX_VALUE;
            float max = (float)Integer.MIN_VALUE;
            for(var vertex: other.vertices)
            {
                float dot = dotProduct(axis, vertex);
                min = Math.min(min, dot);
                max = Math.max(max, dot);
            }
            if (range.x < min && min < range.y || range.x < max && max < range.y || min < range.x && range.x < max || min < range.y && range.y < max)
            {
                continue;
            }
            return false;
        }
        return true;
    }

    @Override
    public void update(float delta) {
        Transform current = parent.getComponent(Transform.Key());
        if(previous != current)
            previous = current.cloneDeep();
        updateAxes();
    }

    public void updateAxes()
    {
        if(parent != null) {
            Renderer renderer = parent.getComponent(Renderer.Key());
            if(renderer != null)  {
                vertices = renderer.getCorners();
            }
        }
        for(int i = 0; i < vertices.size() - 2; i++)
        {
            var v0 = vertices.get(i);
            var v1 = vertices.get(i + 1);
            var axis = new Point2D.Float(v1.x - v0.x, v1.y - v0.y);
            var magnitude = Math.sqrt(Math.pow(axis.x, 2) + Math.pow(axis.y, 2));
            if(magnitude != 0)
            {
                axis.x /= magnitude;
                axis.y /= magnitude;
            }
            axes.set(i, axis);

            float min = (float)Integer.MAX_VALUE;
            float max = (float)Integer.MIN_VALUE;

            float dot = dotProduct(axis, v0);
            min = Math.min(min, dot);
            max = Math.max(max, dot);
            dot = dotProduct(axis, v1);
            min = Math.min(min, dot);
            max = Math.max(max, dot);
            ranges.set(i, new Point2D.Float(min, max));
        }
    }
    public static float dotProduct(Point2D.Float vertex0, Point2D.Float vertex1)
    {
        return vertex0.x * vertex1.x + vertex0.y * vertex1.y;
    }
    @Override
    public String key()
    {
        return Collider.Key();
    }
    public static String Key()
    {
        return "Collider";
    }

    @Override
    public Prototype cloneShallow() {
        var c = Collider.fromTexture(parent.getComponent(Renderer.Key()));
        c.previous = this.previous;
        c.function = this.function;
        return c;
    }

    @Override
    public Prototype cloneDeep() {
        var c = Collider.fromTexture(parent.getComponent(Renderer.Key()));
        c.previous = this.previous;
        c.function = this.function;
        return c;
    }
}