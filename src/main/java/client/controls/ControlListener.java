package client.controls;

import java.awt.geom.*;

public interface ControlListener {

    public void onMove(Point2D.Float input);
    public void onFire();

    // ...
}
