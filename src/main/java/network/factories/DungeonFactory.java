package network.factories;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import client.gameObjects.DungeonWall;

public class DungeonFactory extends LevelFactory {

    Stack<Node> nodeStack = new Stack<>();
    private Random random = new Random();

    private double density = 0.75;

    private boolean[][] grid;

    public DungeonFactory(int gridWidth, int gridHeight, int wallSize) {
        super(gridWidth, gridHeight, wallSize);
        grid = new boolean[gridHeight][gridWidth];
    }

    @Override
    public void buildLevel() {
        nodeStack.push(new Node(0, 0));
        while (!nodeStack.empty()) {
            Node next = nodeStack.pop();
            if (isValid(next)) {
                grid[next.y][next.x] = true;
                pushToStackRandomly(getNeighboors(next));
            }
        }

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if (grid[y][x] && random.nextDouble() <= density) {
                    buildWall(x, y);
                }
            }
        }
    }

    private void pushToStackRandomly(ArrayList<Node> nodes) {
        while (!nodes.isEmpty()) {
            int index = random.nextInt(nodes.size());
            nodeStack.push(nodes.remove(index));
        }
    }

    private boolean isValid(Node node) {
        if (grid[node.y][node.x]) {
            return false;
        }

        int neighboorCount = 0;
        for (int x = node.x - 1; x <= node.x + 1; x++) {
            for (int y = node.y - 1; y <= node.y + 1; y++) {
                if (inBounds(x, y) && notNode(node, x, y) && grid[y][x]) {
                    neighboorCount++;
                }
            }
        }

        return neighboorCount < 3;
    }

    private ArrayList<Node> getNeighboors(Node node) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (int x = node.x - 1; x <= node.x + 1; x++) {
            for (int y = node.y - 1; y <= node.y + 1; y++) {
                if (inBounds(x, y) && notNode(node, x, y) && notCorner(node, x, y)) {
                    nodes.add(new Node(x, y));
                }
            }
        }

        return nodes;
    }

    private boolean inBounds(int x, int y) {
        return x < gridWidth && x >= 0 && y < gridHeight && y >= 0;
    }

    private boolean notNode(Node node, int x, int y) {
        return node.x != x || node.y != y;
    }

    private boolean notCorner(Node node, int x, int y) {
        return !(node.x != x && node.y != y);
    }

    @Override
    protected void buildWall(int x, int y) {
        add(new DungeonWall(x * wallSize, y * wallSize, wallSize));
    }

    private class Node {
        public int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
