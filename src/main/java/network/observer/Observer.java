package network.observer;

import java.io.Serializable;

public abstract class Observer<T extends Serializable> {
    protected Subject<T> subject;
    protected T state;
    void Update() {
        state = subject.GetState();
    }
}
