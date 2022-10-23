package network.client;

import network.data.Payload;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class UDPReceiver {
    DatagramSocket socket = null;

    private static final UDPReceiver instance = new UDPReceiver();
    public static UDPReceiver getInstance() {
        return instance;
    }
    private UDPReceiver() {
        for(int port = 10000; port < 10100; port++) {
            try {
                socket = new DatagramSocket(port);
                break;
            } catch (SocketException e) {
                System.out.println(e.getMessage());
                if(e.getMessage().equals("Address already in use")
                    || e.getMessage().equals("Address already in use: bind"))
                    continue;
                throw new RuntimeException(e);
            }
        }
        System.out.printf("UDP Port: %s\n", socket.getLocalPort());
        new Thread(this::Run).start();
    }
    public int getPort() {
        return socket.getLocalPort();
    }
    private Payload Receive() {
        try {
            byte[] data = new byte[4];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            socket.receive(packet);
            int size = 0;

            for (int i = 0; i < 4; ++i) {
                size |= (data[3-i] & 0xff) << (i << 3);
            }

            byte[] buffer = new byte[size];
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
            ObjectInputStream oos = new ObjectInputStream(byteStream);
            return (Payload) oos.readObject();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void Run() {
        while (true) {
            Payload payload = Receive();
            if(payload == null) continue;
            switch (payload.method) {
                case setGameObject -> {
                    CEngine.getInstance().Set(payload.GetData());
                }
            }
        }
    }
}
