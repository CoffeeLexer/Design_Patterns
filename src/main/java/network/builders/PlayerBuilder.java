package network.builders;

import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;

import client.components.Transform;
import client.gameObjects.Tank;

public class PlayerBuilder implements Builder {
    private LevelProduct levelProduct;
    private PlayerProduct playerProduct;

    private Random random = new Random();

    int tileSize;

    public PlayerBuilder(int tileSize) {
        this.tileSize = tileSize;
    }

    public Pair<Integer, Integer> getFreeSpot() {
        return (Pair<Integer, Integer>) levelProduct.freeSpace.toArray()[random.nextInt(levelProduct.freeSpace.size())];
    }

    @Override
    public Builder build() {
        playerProduct = new PlayerProduct();
        playerProduct.tank = new Tank("images/tank-blue.png");
        Transform transform = playerProduct.tank.getComponent(Transform.Key());
        var freeSpot = getFreeSpot();
        transform.setPosition(freeSpot.getLeft() * tileSize + 10, freeSpot.getRight() * tileSize + 10);
        return this;
    }

    @Override
    public void addProduct(Product product) {
        levelProduct = (LevelProduct) product;
    }

    @Override
    public Product getProduct() {
        return playerProduct;
    }

}
