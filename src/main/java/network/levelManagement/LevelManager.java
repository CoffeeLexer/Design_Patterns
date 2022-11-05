package network.levelManagement;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import client.gameObjects.Wall;
import client.gameObjects.consumables.Consumable;
import network.builders.Builder;
import network.builders.ConsumablesBuilder;
import network.builders.Product;
import network.factories.CityFactory;
import network.factories.DungeonFactory;
import network.factories.StrongholdFactory;
import network.server.SEngine;
import network.server.Server;

public class LevelManager implements ActionListener {
    public static int tileSize = 60;
    public static float spawnCycleDuration = 20;
    public static int countPerCycle = 2;
    
    private Builder consumablesBuilder;
    
    private Timer timer;
    private ArrayList<Builder> levelBuilders = new ArrayList<>();
    private Random random = new Random();

    private Product lastBuiltLevel;

    public LevelManager() {
        timer = new Timer((int)(spawnCycleDuration * 1000), this);
        setUpBuilders();
        consumablesBuilder = new ConsumablesBuilder(countPerCycle, tileSize);
    }

    private void setUpBuilders() {
        // levelBuilders.add(new StrongholdFactory(32, 18, tileSize));
        // levelBuilders.add(new CityFactory(32, 18, tileSize));
        levelBuilders.add(new DungeonFactory(32, 18, tileSize));
    }

    public void buildNextLevel() {
        timer.stop();
        
        if (lastBuiltLevel != null) {
            lastBuiltLevel.destroy();
        }

        if (levelBuilders.isEmpty()) {
            setUpBuilders();
        }

        Builder levelBuilder = levelBuilders.remove(random.nextInt(levelBuilders.size()));
        levelBuilder.build();

        lastBuiltLevel = levelBuilder.getProduct();
        lastBuiltLevel.spawn();
        timer.start();
    }

    public void buildConsumables() {
        consumablesBuilder.addProduct(lastBuiltLevel);
        consumablesBuilder.build();
        lastBuiltLevel.spawn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buildConsumables();
    }
}