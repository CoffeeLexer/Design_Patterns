package network.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

import client.gameObjects.CityWall;
import client.gameObjects.Wall;

public class CityFactory extends LevelFactory {

    private Random random = new Random();

    private double density = 0.75;

    public CityFactory(int gridWidth, int gridHeight, int wallSize) {
        super(gridWidth, gridHeight, wallSize);
    }

    @Override
    public void buildLevel() {
        for (int i = 0; i < gridHeight / 4; i++) {
            for (int j = 0; j < gridWidth / 4; j++) {
                if (random.nextDouble() <= density) {
                    buildBuilding(j * 4, i * 4);
                }
            }
        }
    }

    private void buildBuilding(int x, int y) {
        int xCorner = x + 1;
        int yCorner = y + 1;

        switch (random.nextInt(0, 4)) {
            case 0 -> {
                buildWall(xCorner, yCorner);
                buildWall(xCorner + 1, yCorner);
                buildWall(xCorner, yCorner + 1);
            }
            case 1 -> {
                buildWall(xCorner + 1, yCorner);
                buildWall(xCorner, yCorner + 1);
                buildWall(xCorner + 1, yCorner + 1);
            }
            case 2 -> {
                buildWall(xCorner, yCorner);
                buildWall(xCorner, yCorner + 1);
                buildWall(xCorner + 1, yCorner + 1);
            }
            case 3 -> {
                buildWall(xCorner, yCorner);
                buildWall(xCorner, yCorner + 1);
                buildWall(xCorner + 1, yCorner);
                buildWall(xCorner + 1, yCorner + 1);
            }
        }
    }
    
    @Override
    protected void buildWall(int x, int y) {
        add(new CityWall(x * wallSize, y * wallSize, wallSize));
    }

}
