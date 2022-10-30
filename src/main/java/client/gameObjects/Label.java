package client.gameObjects;

import client.components.Renderer;
import client.components.Transform;
import java.awt.geom.Point2D;
import java.awt.Color;

public class Label extends GameObject {
    private int width = 100;
    private int height = 30;
    private int yOffset;

    private GameObject parent;
    private Renderer renderer;
    private Transform transform;

    public Label(float x, float y, String text, GameObject parent, Color color) {
        tag = Tag.Dynamic;
        this.parent = parent;
        this.yOffset = 0;

        this.renderer = new Renderer(width, height, text, color);
        this.transform = new Transform().setPosition(x, y).setRotation(0);

        addComponent(this.renderer);
        addComponent(this.transform);
    }

    public Label(float x, float y, String text, GameObject parent, Color color, int yOffset) {
        tag = Tag.Dynamic;
        this.parent = parent;
        this.yOffset = yOffset;

        this.renderer = new Renderer(width, height, text, color);
        this.transform = new Transform().setPosition(x, y).setRotation(0);

        addComponent(this.renderer);
        addComponent(this.transform);
    }

    public void setText(String text) {
        this.renderer.setText(text);;
    }

    public void setColor(Color color) {
        this.renderer.setColor(color);
    }

    @Override
    public void update(float delta) {
        // get parent coordinates
        Point2D.Float position = ((Transform)this.parent.getComponent(Transform.Key())).position;
        int x = (int) Math.round(position.getX());
        int y = (int) Math.round(position.getY()) + this.yOffset;

        // update label position to match parent position
        this.transform.setPosition(x, y);
    }
}
