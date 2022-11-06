package network.factories;

import java.util.Random;
import client.gameObjects.CityWall;
import client.gameObjects.Wall;
import network.builders.Builder;

public class CityFactory extends LevelFactory {

    private Random random = new Random();

    private double density = 1;

    public CityFactory(int gridWidth, int gridHeight, int wallSize) {
        super(gridWidth, gridHeight, wallSize);
    }

    @Override
    public Builder build() {
        buildEdges();
        for (int i = 0; i < (gridHeight - 1) / 4; i++) {
            for (int j = 0; j < (gridWidth - 1) / 4; j++) {
                if (random.nextDouble() <= density) {
                    buildBuilding(j * 4 + 1, i * 4 + 1);
                }
            }
        }

        return this;
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
        level.objects[y][x] = new CityWall(x * wallSize, y * wallSize, wallSize);
    }
}
