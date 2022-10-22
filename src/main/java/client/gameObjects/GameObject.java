package client.gameObjects;

import client.components.GameComponent;
import client.components.Renderer;
import client.components.Transform;

import java.awt.*;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class GameObject implements Serializable {
    public boolean newState = true;
    public int uniqueID = -1;
    Map<String, GameComponent> components;
    public Tag tag = Tag.Undefined;

    public GameObject() {
        components = new TreeMap<>();
    }

    public void update(float delta) {
        components.values().forEach(e -> e.update(delta));
    }

    public void destroy() {
        components.values().forEach(GameComponent::destroy);
    }

    public void render(Graphics2D g2d) {
        components.values().forEach(e -> e.render(g2d));
    }

    public <T extends GameComponent> void addComponent(T component) {
        component.gameObject = this;
        if (!components.containsKey(component.key()))
            components.put(component.key(), component);
    }

    public <T extends GameComponent> void removeComponent(String key) {
        components.remove(key);
    }

    public <T extends GameComponent> T getComponent(String key) {
        return (T) components.get(key);
    }

    public GameObject ClientParse() {
        GameObject obj = new GameObject();
        obj.addComponent(((Transform) this.getComponent(Transform.Key())).clone());
        obj.addComponent(((Renderer) this.getComponent(Renderer.Key())).clone());
        obj.tag = this.tag;
        obj.uniqueID = this.uniqueID;
        return obj;
    }
}
