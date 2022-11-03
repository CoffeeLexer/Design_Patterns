package network.factories;

import java.util.Random;

import client.gameObjects.StrongholdWall;

public class StrongholdFactory extends LevelFactory {

    private Random random = new Random();
    private double density = 0.75;
    private int gapSize = 1;

    public StrongholdFactory(int gridWidth, int gridHeight, int wallSize) {
        super(gridWidth, gridHeight, wallSize);
    }

    @Override
    public void buildLevel() {
        for (int x = 1, y = 1; y < gridHeight / 2; y += gapSize + 1) {
            for (int i = 0; i < gridWidth - x * 2; i++) {
                buildWall(x + i, y);
                buildWall(x + i, gridHeight - y - 1);
            }

            for (int j = 1; j < gridHeight - y * 2 - 1; j++) {
                buildWall(x, y + j);
                buildWall(gridWidth - x - 1, y + j);
            }
            x += gapSize + 1;
        }
    }

    @Override
    protected void buildWall(int x, int y) {
        if (random.nextDouble() <= density) {
            add(new StrongholdWall(x * wallSize, y * wallSize, wallSize));
        }
    }

}