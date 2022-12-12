package network.server;

import network.client.PlayerClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Chat {
    private Map<Integer, PlayerClient> players = new HashMap<>();

    public void attach(PlayerClient playerClient) {
        if(!players.containsKey(playerClient.getId())) {
            players.put(playerClient.getId(), playerClient);
        }
    }
    public void send(PlayerClient client, String msg) {
        var containObj = players.get(client.getId());
        var user = !Objects.equals(client.getUsername(), "") ? client.getUsername() : "Tank" + client.getId();
        if(containObj != null) {
            String parsed = user + ": " + msg;
            for (var player: players.values()) {
                if(player.getId() != client.getId()) {
                    player.receiveMessage(parsed);
                }
            }
        }
        else {
            System.out.println("Player is not registered to chat");
        }
    }
}
