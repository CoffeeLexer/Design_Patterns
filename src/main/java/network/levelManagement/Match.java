package network.levelManagement;

import java.util.HashMap;
import java.util.HashSet;

import client.gameObjects.Tank;
import network.builders.PlayerProduct;
import network.client.PlayerClient;
import network.server.SEngine;

public class Match {
    public int count = 0;
    public MatchState matchState;

    public HashMap<PlayerClient, PlayerProduct> players = new HashMap<>();
    public HashSet<Tank> deadPlayers = new HashSet<>();

    public Match() {
        matchState = MatchState.waiting;
    }

    public void startNext() {
        matchState = MatchState.inProgress;
        deadPlayers = new HashSet<>();
        count++;
        System.out.println("Starting match " + count);
    }

    public void addPlayer(PlayerProduct player) {
        players.put(player.playerClient, player);
    }

    public void disconnectPlayer(PlayerClient idRef) {
        players.remove(idRef);
        SEngine.GetInstance().Destroy(idRef.getId());
        if (players.size() < 2) {
            matchState = MatchState.waiting;
            count = 0;
        }
    }

    public void destroyPlayer(Tank tank) {
        deadPlayers.add(tank);
        SEngine.GetInstance().Destroy(tank);
        if (players.size() - deadPlayers.size() <= 1) {
            matchState = MatchState.finished;
        }
    }
}
