package client.gameObjects;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

public abstract class GameObject implements Serializable {

    public int uniqueID = -1;
    Map<String, GameComponent> components;
    public String tag = "Default";

    public GameObject() {
        components = new TreeMap<>();
        start();
    }
    public void foreach(Consumer<? super GameComponent> function) {
        components.values().forEach(function);
    }
    public void start() {
        this.foreach(GameComponent::start);
    }
    public void update() {
        this.foreach(GameComponent::update);
        this.foreach(GameComponent::sync);
    }

    public void destroy() {
        this.foreach(GameComponent::destroy);
    }

    public void render() {
        this.foreach(GameComponent::render);
    }

    public <T extends GameComponent> void addComponent(T component) {
        component.gameObject = this;
        if(!components.containsKey(component.key()))
            components.put(component.key(), component);
    }
    public <T extends GameComponent> void updateComponent(T component) {
        component.gameObject = this;
        components.put(component.key(), component);
    }
    public <T extends GameComponent> void removeComponent(String key) {
        components.remove(key);
    }
    public <T extends GameComponent> T getComponent(String key) {
        return (T)components.get(key);
    }

    public boolean active = true;
}
