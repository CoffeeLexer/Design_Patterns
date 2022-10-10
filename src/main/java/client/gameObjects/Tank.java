package client.gameObjects;

import client.components.Controller;
import client.components.Renderer;
import client.components.Transform;

public class Tank extends GameObject {
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
        Controller control = getComponent(Controller.Key());
        client.controls.Controller.getInstance().listen(control);
        return this;
    }
}
