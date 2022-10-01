package network;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    ArrayList<Observer> observers = null;

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
