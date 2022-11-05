package network.builders;

import java.util.ArrayList;
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

    private ArrayList<Pair<Integer, Integer>> freeSpace = new ArrayList<>();

    @Override
    public void build() {
        IntStream indexStream = random.ints(countPerCycle, 0, freeSpace.size());
        indexStream.forEach(e -> {
            Pair<Integer, Integer> freeSpot = freeSpace.get(e);
            int x = freeSpot.getLeft();
            int y = freeSpot.getRight();
            if(levelProduct.objects[y][x] instanceof Consumable){
                SEngine.GetInstance().Destroy(levelProduct.objects[y][x]);
            }

            levelProduct.objects[y][x] = new AmmunitionConsumable().setPosition(x * tileSize + 20, y * tileSize + 20);
        });
    }

    @Override
    public Product getProduct() {
        return levelProduct;
    }

    @Override
    public void addProduct(Product product) {
        levelProduct = (LevelProduct) product;
        var objects = levelProduct.objects;
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[0].length; j++) {
                if (objects[i][j] == null) {
                    freeSpace.add(Pair.of(j, i));
                }
            }
        }
    }

}
