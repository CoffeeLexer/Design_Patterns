package client.components;


import java.security.Key;

public class Specs extends GameComponent {
    public int health = 0;
    public int ammo = 0;

    public Specs(int h, int a) {
        health = h;
        ammo = a;
    }

    @Override
    public String key() {
        return Key();
    }
    public static String Key() {
        return "Specs";
    }

    @Override
    public Prototype cloneShallow() {
        return new Specs(health, ammo);
    }

    @Override
    public Prototype cloneDeep() {
        return new Specs(health, ammo);
    }
}
