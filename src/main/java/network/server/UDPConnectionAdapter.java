package network.server;

import network.data.Connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class UDPConnectionAdapter extends Connection {
    InetAddress address = null;
    int port = -1;

    private UDPSender sender;
    public UDPConnectionAdapter(Connection connection) {
        super(null);
        address = connection.getAddress();
        port = connection.getUDPPort();
        sender = new UDPSender();
    }

    public UDPConnectionAdapter(Socket clientSocket) {
        super(clientSocket);
    }
    protected void construct(Socket clientSocket) {

    }

    @Override
    public void writeObject(Object obj) {
        sender.Send(address, port, obj);
    }
}
