package network.builders;

import client.components.tankDecorator.LabelDecorator;
import client.gameObjects.Tank;
import network.client.ClientId;
import network.server.SEngine;

public class PlayerProduct implements Product {

    public ClientId clientId;
    public Integer playerId;
    public Tank tank;

    @Override
    public void spawn() {
        LabelDecorator labelDecorator = new LabelDecorator(tank);
        playerId = SEngine.GetInstance().Add(tank);

        String playerName = "Player: " + Integer.toString(playerId);
        labelDecorator.decorate(playerName);
    }

    @Override
    public void destroy() {
        SEngine.GetInstance().Destroy(tank);
    }
    
}
