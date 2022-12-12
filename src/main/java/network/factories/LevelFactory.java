package network.factories;

import client.gameObjects.GameObject;
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

    @Override
    public Builder build() {
        buildEdges();
        buildBody();
        return this;
    }

    protected abstract void buildEdges();
    protected abstract void buildBody();

    protected abstract void buildWall(int x, int y);

    public Product getProduct() {
        return level;
    }

    public void addProduct(Product product) {
        this.level = (LevelProduct) product;
    }
}
