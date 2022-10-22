package client.components;

import network.client.CEngine;
import network.server.SEngine;

import java.util.concurrent.TimeUnit;

public class Lifetime extends GameComponent{
    long end;

    public Lifetime(TimeUnit unit, long duration) {
        end = System.currentTimeMillis() + unit.toMillis(duration);
    }
    public Lifetime(long end) {
        this.end = end;
    }
    @Override
    public void update(float delta) {
        if(System.currentTimeMillis() > end) {
            SEngine.GetInstance().Destroy(gameObject);
        }
    }

    @Override
    public String key() {
        return Lifetime.Key();
    }
    public static String Key() {
        return "Lifetime";
    }
    @Override
    public Lifetime clone() {
        return new Lifetime(end);
    }
}
