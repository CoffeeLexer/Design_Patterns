package network.levelManagement;

import java.awt.event.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Timer;

import client.gameObjects.Tank;
import network.builders.Builder;
import network.builders.ConsumablesBuilder;
import network.builders.PlayerBuilder;
import network.builders.PlayerProduct;
import network.builders.Product;
import network.client.PlayerClient;
import network.data.Handshake;
import network.data.Payload;
import network.factories.CityFactory;
import network.factories.DungeonFactory;
import network.factories.LevelFactory;
import network.factories.StrongholdFactory;
import network.server.SEngine;
import network.server.Server;

public class LevelManager implements ActionListener {
    public static int tileSize = 60;
    public static float spawnCycleDuration = 20;
    public static int countPerCycle = 2;
    private ReentrantLock clientLock = new ReentrantLock();

    private HashMap<Type, Builder> builders = new HashMap<>();

    private Timer timer;
    private ArrayList<Builder> levelBuilders = new ArrayList<>();
    private Random random = new Random();

    private Match match = new Match();
    private Product builtLevel;

    private LevelManager() {
        timer = new Timer((int) (spawnCycleDuration * 1000), this);
        setUpLevelBuilders();
    }

    private static LevelManager _instance;

    public static LevelManager getInstance() {
        if (_instance == null) {
            _instance = new LevelManager();
        }

        return _instance;
    }

    private void setUpLevelBuilders() {
        levelBuilders.add(new StrongholdFactory(32, 18, tileSize));
        levelBuilders.add(new CityFactory(32, 18, tileSize));
        levelBuilders.add(new DungeonFactory(32, 18, tileSize));
    }

    private void setUpBuilders() {
        if (builtLevel != null) {
            builtLevel.destroy();
        }

        if (levelBuilders.isEmpty()) {
            setUpLevelBuilders();
        }

        builders.put(LevelFactory.class, levelBuilders.remove(random.nextInt(levelBuilders.size())));
        builders.putIfAbsent(ConsumablesBuilder.class, new ConsumablesBuilder(countPerCycle, tileSize));
        builders.putIfAbsent(PlayerBuilder.class, new PlayerBuilder(tileSize));
    }

    public void buildNextLevel() {
        timer.stop();
        setUpBuilders();
        builtLevel = builders.get(LevelFactory.class).build().getProduct();
        builtLevel.spawn();
        SEngine.GetInstance().syncAllConnections();
        timer.start();
    }

    public Integer spawnPlayer(PlayerClient idRef) {
        clientLock.lock();
        Builder playerBuilder = builders.get(PlayerBuilder.class);
        playerBuilder.addProduct(builtLevel);
        playerBuilder.build();
        Product player = playerBuilder.getProduct();
        player.spawn();
        int id = ((PlayerProduct) player).playerId;
        idRef.setId(id);
        ((PlayerProduct) player).playerClient = idRef;

        match.addPlayer((PlayerProduct) player);
        if (match.players.size() > 1 && match.matchState == MatchState.waiting) {
            match.startNext();
        }

        clientLock.unlock();
        return id;
    }

    public void disconnectPlayer(PlayerClient idRef) {
        clientLock.lock();
        match.disconnectPlayer(idRef);
        clientLock.unlock();
    }

    public void destroyPlayer(Tank tank) {
        match.destroyPlayer(tank);
        if (match.matchState == MatchState.finished) {
            buildNextLevel();
            respawnPlayers();
        }
        match.startNext();
    }

    private void respawnPlayers() {
        match.players.forEach((k, v) -> SEngine.GetInstance().Destroy(v.tank));
        var playersIterator = match.players.entrySet().iterator();

        Server.GetInstance().notifyConnections(conn -> {
            try {
                conn.writeObject(new Payload(Handshake.Method.tagPlayer,
                        spawnPlayer(playersIterator.next().getValue().playerClient)));
            } catch (IOException e) {
            }
        });
    }

    private void buildConsumables() {
        var consumablesBuilder = builders.get(ConsumablesBuilder.class);
        consumablesBuilder.addProduct(builtLevel);
        consumablesBuilder.build();
        builtLevel.spawn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buildConsumables();
    }
}