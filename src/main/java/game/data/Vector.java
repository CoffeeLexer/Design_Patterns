package game.data;

import java.io.Serializable;

// Vector is used just to conveniently describe a pair of X and Y position
public class Vector implements Serializable {
    public float x;
    public float y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
