package network.factories;

import java.util.ArrayList;

import client.gameObjects.GameObject;
import client.gameObjects.Wall;
import network.builders.Builder;
import network.builders.LevelProduct;
import network.builders.Product;
import network.server.SEngine;

public abstract class LevelFactory implements Builder {

    protected int gridHeight;
    protected int gridWidth;
    protected int wallSize;

    public LevelProduct level;

    public LevelFactory(int gridWidth, int gridHeight, int wallSize) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.wallSize = wallSize;
        level = new LevelProduct(gridWidth, gridHeight);
    }

    protected void add(GameObject gameObject) {
        SEngine.GetInstance().Add(gameObject);
    }

    protected void buildEdges() {
        for (int i = 0; i < gridWidth; i++) {
            buildWall(i, 0);
            buildWall(i, gridHeight - 1);
        }

        for (int j = 1; j < gridHeight - 1; j++) {
            buildWall(0, j);
            buildWall(gridWidth - 1, j);
        }
    }

    protected abstract void buildWall(int x, int y);

    public Product getProduct() {
        return level;
    }

    public void addProduct(Product product) {
        this.level = (LevelProduct) product;
    }
}
