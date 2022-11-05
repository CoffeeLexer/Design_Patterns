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
    public void build() {
        buildEdges();
        for (int x = 1 + gapSize, y = 1 + gapSize; y < gridHeight / 2; y = x += gapSize + 1) {
            for (int i = 0; i < gridWidth - x * 2; i++) {
                buildWallRandomly(x + i, y);
                buildWallRandomly(x + i, gridHeight - y - 1);
            }

            for (int j = 1; j < gridHeight - y * 2 - 1; j++) {
                buildWallRandomly(x, y + j);
                buildWallRandomly(gridWidth - x - 1, y + j);
            }
        }
    }

    private void buildWallRandomly(int x, int y) {
        if (random.nextDouble() <= density) {
            buildWall(x, y);
        }
    }

    @Override
    protected void buildWall(int x, int y) {
        level.objects[y][x] = new StrongholdWall(x * wallSize, y * wallSize, wallSize);
    }

}