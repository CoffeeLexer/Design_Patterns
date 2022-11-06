package network.builders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import client.gameObjects.GameObject;
import client.gameObjects.Wall;
import client.gameObjects.consumables.AmmunitionConsumable;
import client.gameObjects.consumables.Consumable;
import network.factories.LevelFactory;
import network.server.SEngine;

import java.awt.geom.Point2D;
import java.io.PipedInputStream;

public class ConsumablesBuilder implements Builder {

    public LevelProduct levelProduct;
    private int countPerCycle;
    private Random random = new Random();
    private int tileSize;

    public ConsumablesBuilder(int countPerCycle, int tileSize) {
        this.countPerCycle = countPerCycle;
        this.tileSize = tileSize;
    }

    @Override
    public Builder build() {
        ArrayList<Integer> indexArray = new ArrayList<Integer>();
        for (int i = 1; i < countPerCycle; i++)
            indexArray.add(i);
        Collections.shuffle(indexArray);

        for (int i = 0; i < countPerCycle; i++) {
            var freeSpot = levelProduct.freeSpace.get(random.nextInt(levelProduct.freeSpace.size()));
            int x = freeSpot.getLeft();
            int y = freeSpot.getRight();
            if (levelProduct.objects[y][x] instanceof Consumable) {
                SEngine.GetInstance().Destroy(levelProduct.objects[y][x]);
            }

            levelProduct.objects[y][x] = new AmmunitionConsumable().setPosition(x * tileSize + 20, y * tileSize + 20);
        }

        return this;
    }

    @Override
    public Product getProduct() {
        return levelProduct;
    }

    @Override
    public void addProduct(Product product) {
        levelProduct = (LevelProduct) product;
    }

}
