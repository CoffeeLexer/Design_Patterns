package network.builders;

import java.util.ArrayList;

import client.gameObjects.GameObject;
import client.gameObjects.consumables.Consumable;
import network.server.SEngine;

public class LevelProduct implements Product {
    public GameObject[][] objects;
    private boolean[][] spawned;

    public LevelProduct(int width, int height) {
        objects = new GameObject[height][width];
        spawned = new boolean[height][width];
    }

    @Override
    public void spawn() {
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[0].length; j++) {
                if (objects[i][j] == null || spawned[i][j]) {
                    continue;
                }

                SEngine.GetInstance().Add(objects[i][j]);
                spawned[i][j] = true;
            }
        }
    }

    @Override
    public void destroy() {
        for (GameObject[] objectsRow : objects) {
            for (GameObject object : objectsRow) {
                SEngine.GetInstance().Destroy(object);
            }
        }

        objects = null;
    }
}
