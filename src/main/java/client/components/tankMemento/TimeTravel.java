package client.components.tankMemento;
import java.util.concurrent.TimeUnit;
import client.gameObjects.Tank;
import client.components.GameComponent;

// this object is Caretaker
public class TimeTravel extends GameComponent {
    long end;
    Tank tank;
    TankState state;
    boolean alreadyEnded = false;

    public TimeTravel(TimeUnit unit, long duration, Tank tank) {
        end = System.currentTimeMillis() + unit.toMillis(duration);
        this.tank = tank;
        this.state = this.tank.savePosition();
    }

    public TimeTravel(long end) {
        this.end = end;
    }

    @Override
    public void update(float delta) {
        if(!alreadyEnded && System.currentTimeMillis() > end) {
          this.tank.restorePosition(this.state);
          this.alreadyEnded = true;
        }
    }

    @Override
    public String key() {
        return TimeTravel.Key();
    }

    public static String Key() {
        return "TimeTravel";
    }

    @Override
    public TimeTravel cloneShallow() {
        return new TimeTravel(end);
    }

    public TimeTravel cloneDeep() {
        return new TimeTravel(Long.valueOf(end));
    }
}
