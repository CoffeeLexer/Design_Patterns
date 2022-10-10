package client.controls;

import java.awt.geom.*;

public interface ControllerListener {
    void onMove(Point2D.Float input);
    void onFire();
}
