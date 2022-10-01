package network.observer;

import java.io.Serializable;
import java.util.ArrayList;

public class Subject<T extends Serializable> {
    ArrayList<Observer> observers = null;
    Object state = null;
    public T GetState() {
        return (T)state;
    }
    public void SetState(T state) {
        this.state = state;
        Notify();
    }
    public Subject() {
        observers = new ArrayList<>();
    }
    void Attach(Observer observer) {
        observers.add(observer);
    }
    void Detach(Observer observer) {
        observers.remove(observer);
    }
    void Notify() {
        for (var element : observers) {
            element.Update();
        }
    }
}
