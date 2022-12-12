package client.components;

import network.server.SEngine;
import java.util.concurrent.TimeUnit;
import client.components.weaponFacade.WeaponFacade;
import java.awt.event.KeyEvent;

public class Lifetime extends GameComponent{
    long end;
    boolean isTankClone = false;

    public Lifetime(TimeUnit unit, long duration, boolean isTankClone) {
        end = System.currentTimeMillis() + unit.toMillis(duration);
        this.isTankClone = isTankClone;
    }
    public Lifetime(long end) {
        this.end = end;
    }
    @Override
    public void update(float delta) {
        if(System.currentTimeMillis() > end) {
            if (this.isTankClone) {
                WeaponFacade weaponFacade = gameObject.getComponent(WeaponFacade.Key());
                weaponFacade.shoot(null, KeyEvent.VK_J); // shoot a shotgun projectile
            }
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
    public Lifetime cloneShallow() {
        return new Lifetime(end);
    }
    public Lifetime cloneDeep() {
        return new Lifetime(Long.valueOf(end));
    }
}
