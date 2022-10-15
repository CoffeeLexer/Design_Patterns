package client.gameObjects;

public interface Prototype {
    public Prototype cloneShallow();
    public Prototype cloneDeep();
}
