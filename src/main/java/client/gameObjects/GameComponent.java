package client.gameObjects;

import network.Client;

import java.io.Serializable;

public abstract class GameComponent implements Serializable {
    public abstract String key();

    public GameObject gameObject;
    public boolean syncRequired = false;

    public void start() {

    }
    public void update() {}
    public void destroy() {}
    public void render() {}
    public void sync() {
        Client.getInstance().update(this);
    }
}
