package network.server;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPSender {
    DatagramSocket socket = null;
    private final static UDPSender instance = new UDPSender();

    public static UDPSender getInstance() {
        return instance;
    }

    public UDPSender() {
        try {
            socket = new DatagramSocket(8081);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
    public <T extends Serializable> void Send(InetAddress address, int port, T dataObject) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(byteStream);
            oos.writeObject(dataObject);
            oos.flush();

            byte[] buffer = byteStream.toByteArray();
            int size = buffer.length;

            byte[] data = new byte[4];

            for(int i = 0; i < 4; i++) {
                int shift = i << 3;
                data[3-i] = (byte)((size & (0xff << shift)) >>> shift);
            }

            DatagramPacket packet = new DatagramPacket(data, 4, address, port);
            socket.send(packet);

            packet = new DatagramPacket(buffer, size, address, port);
            socket.send(packet);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }
}