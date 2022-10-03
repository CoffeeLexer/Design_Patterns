package client.gameObjects;

import client.components.Renderer;

import java.io.Serializable;

public class Wall extends GameObject implements Serializable {
    public Wall(String imagePath) {
        addComponent(new Renderer(imagePath, 100, 100));
        tag = "Static";
    }
}
