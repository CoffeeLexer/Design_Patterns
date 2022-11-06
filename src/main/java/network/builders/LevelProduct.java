package network.builders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.apache.commons.lang3.tuple.Pair;

import client.gameObjects.GameObject;
import client.gameObjects.Wall;
import client.gameObjects.consumables.Consumable;
import network.server.SEngine;

public class LevelProduct implements Product {
    public GameObject[][] objects;
    private boolean[][] spawnedWall;

    public ArrayList<Pair<Integer, Integer>> freeSpace = new ArrayList<>();

    public int width, height;

    public LevelProduct(int width, int height) {
        this.width = width;
        this.height = height;
        objects = new GameObject[height][width];
        spawnedWall = new boolean[height][width];
    }

    @Override
    public void spawn() {
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[0].length; j++) {
                if (objects[i][j] == null) {
                    freeSpace.add(Pair.of(j, i));
                    continue;
                }

                if (spawnedWall[i][j]) {
                    continue;
                }

                SEngine.GetInstance().Add(objects[i][j]);
                if (objects[i][j] instanceof Wall) {
                    spawnedWall[i][j] = true;
                }
            }
        }

        Collections.shuffle(freeSpace);
    }

    @Override
    public void destroy() {
        for (GameObject[] objectsRow : objects) {
            for (GameObject object : objectsRow) {
                if (object != null) {
                    if(object instanceof Consumable){
                        System.out.println("Deleting ammo consumable");
                    }
                    SEngine.GetInstance().Destroy(object);
                }
                
            }
        }

        objects = null;
    }
}
