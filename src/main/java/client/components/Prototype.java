package client.components;

public abstract interface Prototype {
    public Prototype cloneShallow();
    public Prototype cloneDeep();
}
