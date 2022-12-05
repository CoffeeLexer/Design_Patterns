package client.utilities;

public abstract class Iterator<T> {
    public abstract boolean hasNext();
    public abstract T next();
    public abstract T current();
}
