package network.observer;

import network.Communicator;
import network.data.Handshake;
import network.data.Payload;

import java.nio.channels.SocketChannel;

public class ObserverServer extends Observer {
    SocketChannel client = null;
    public ObserverServer(SocketChannel client) {
        this.client = client;
    }

    @Override
    void Update() {
        try {
            Communicator.Write(client, new Payload(Handshake.Method.observer));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
