package network.factories;

import java.util.Random;
import client.gameObjects.CityWall;

public class CityFactory extends LevelFactory {

    private Random random = new Random();

    private double density = 1;

    public CityFactory(int gridWidth, int gridHeight, int wallSize) {
        super(gridWidth, gridHeight, wallSize);
    }

    public void buildBody() {
        buildEdges();
        for (int i = 0; i < (gridHeight - 1) / 4; i++) {
            for (int j = 0; j < (gridWidth - 1) / 4; j++) {
                if (random.nextDouble() <= density) {
                    buildBuilding(j * 4 + 1, i * 4 + 1);
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
    
    protected void buildEdges() {
        buildWall(1, 1);
        buildWall(gridWidth - 2, 1);
        buildWall(gridWidth - 2, gridHeight - 2);
        buildWall(1, gridHeight - 2);

        for (int i = 0; i < gridWidth; i++) {
            buildWall(i, 0);
            buildWall(i, gridHeight - 1);
        }
        
        for (int j = 1; j < gridHeight - 1; j++) {
            buildWall(0, j);
            buildWall(gridWidth - 1, j);
        }
    }

    protected void buildWall(int x, int y) {
        level.objects[y][x] = new CityWall(x * wallSize, y * wallSize, wallSize);
    }
}
