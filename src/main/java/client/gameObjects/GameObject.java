package client.gameObjects;

import client.Primitive;
import client.components.*;

import java.awt.*;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

public class GameObject extends Primitive implements Serializable, Prototype {
    private List<Integer> childrenIDs = new ArrayList<Integer>();
    public boolean newState = true;
    public int uniqueID = -1;
    Map<String, Primitive> components;
    public Tag tag = Tag.Undefined;
    public String imagePath;

    public GameObject() {
        components = new TreeMap<>();
    }

    public void update(float delta) {
        components.values().forEach(e -> e.update(delta));
    }

    public void destroy() {
        components.values().forEach(Primitive::destroy);
    }

    public void render(Graphics2D g2d) {
        components.values().forEach(e -> e.render(g2d));
    }

    public <T extends Primitive> void addComponent(T primitive) {
        primitive.parent = this;
        if (!components.containsKey(((GameComponent)primitive).key())) {
            components.put(((GameComponent)primitive).key(), primitive);
        }
    }
    public void bind() {
        for (var e: components.values()) {
            e.parent = this;
        }
    }

    public <T extends Primitive> void removeComponent(String key) {
        components.remove(key);
    }

    public <T extends Primitive> T getComponent(String key) {
        return (T) components.get(key);
    }

    public GameObject ClientParse() {
        GameObject obj = new GameObject();
        obj.addComponent(((Transform) this.getComponent(Transform.Key())).cloneShallow());
        obj.addComponent(((Renderer) this.getComponent(Renderer.Key())).cloneShallow());
        if(Tank.class.isAssignableFrom(this.getClass())) {
            obj.addComponent((Specs) this.getComponent(Specs.Key()));
        }
        obj.tag = this.tag;
        obj.uniqueID = this.uniqueID;
        return obj;
    }
    @Override
    public GameObject cloneShallow() {
        GameObject obj = new GameObject();
        for (var entry : components.entrySet()) {
            Primitive gc = entry.getValue();
            obj.addComponent((Primitive)((GameComponent)gc).cloneShallow());
        }
        obj.tag = this.tag;
        return obj;
    }
    @Override
    public GameObject cloneDeep() {
        GameObject obj = new GameObject();
        for (var entry : components.entrySet()) {
            Primitive gc = entry.getValue();
            obj.addComponent((Primitive)((GameComponent)gc).cloneDeep());
        }
        obj.tag = this.tag;
        return obj;
    }
    public void addChildID(Integer id) {
        this.childrenIDs.add(id);
    }

    public List<Integer> getChildrenIDs() {
        return this.childrenIDs;
    }
}
