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

    public void buildBody() {
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

    protected void buildEdges() {
        for (int i = 0; i < gridWidth; i++) {
            buildWall(i, 0);
            buildWall(i, gridHeight - 1);
        }
        
        for (int j = 1; j < gridHeight - 1; j++) {
            buildWall(0, j);
            buildWall(gridWidth - 1, j);
        }
    }

    @Override
    protected void buildWall(int x, int y) {
        level.objects[y][x] = new StrongholdWall(x * wallSize, y * wallSize, wallSize);
    }

}