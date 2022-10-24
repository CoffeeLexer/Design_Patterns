package network.factories;

import client.components.GameComponent;
import client.gameObjects.GameObject;
import network.server.SEngine;

public abstract class LevelFactory {

    protected int gridHeight;
    protected int gridWidth;
    protected int wallSize;

    public LevelFactory(int gridWidth, int gridHeight, int wallSize) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.wallSize = wallSize;
    }

    protected void add(GameObject gameObject) {
        SEngine.GetInstance().Add(gameObject);
    }

    public abstract void buildLevel();

    protected abstract void buildWall(int x, int y);
}
