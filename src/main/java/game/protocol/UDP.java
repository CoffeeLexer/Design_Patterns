package game.protocol;

import game.data.Payload;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.function.Consumer;

public class UDP {
    public static class Sender {
        private final DatagramSocket socket;
        public Sender() {
            try {
                socket = new DatagramSocket();
                byteStream = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(byteStream);
            }
            catch (SocketException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * @param receiverAddress address of receiver
         * @param receiverPort port of receiver
         * @param data data to be sent. Must have Serializable implemented.
         */
        public void send(InetAddress receiverAddress, int receiverPort, Payload data) {
            try {
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(byteStream);
                oos.writeObject(data);
                oos.flush();

                byte[] buffer = byteStream.toByteArray();

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, receiverAddress, receiverPort);
                socket.send(packet);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        private ByteArrayOutputStream byteStream;
        private ObjectOutputStream oos;
        public void send(DatagramPacket packet, Payload data) {
            try {
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(byteStream);
                oos.writeObject(data);
                oos.flush();

                byte[] buffer = byteStream.toByteArray();

                packet.setData(buffer);
                packet.setLength(buffer.length);

                socket.send(packet);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static class Receiver implements Runnable{
        private final DatagramSocket socket;
        private Consumer<Payload> autonomousResponse;
        private boolean isListening = false;
        private Thread thread;
        public Receiver() {
            try {
                socket = new DatagramSocket();
            }
            catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }
        public InetAddress getAddress() {
            return socket.getLocalAddress();
        }
        public int getPort() {
            return socket.getLocalPort();
        }
        public Identifier getIdentifier() {
            var identifier = new Identifier();
            identifier.address = getAddress();
            identifier.port = getPort();
            return identifier;
        }

        /**@warning Sometimes UDP packets are lost. If you call this method and no packets are sent, this will cause deadlock!
         * @return Returns raw object. Need to cast to same class which was sent through Sender
         */
        public Payload receive() {
            try {
                int size = socket.getReceiveBufferSize();
                byte[] buffer = new byte[size];
                DatagramPacket packet = new DatagramPacket(buffer, size);
                socket.receive(packet);

                ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
                ObjectInputStream oos = new ObjectInputStream(byteStream);

                return (Payload) oos.readObject();
            } catch (SocketException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        public void autonomousListen(Consumer<Payload> listen) {
            autonomousResponse = listen;
            isListening = true;
            thread = new Thread(this);
            thread.start();
        }
        public void stop() {

        }
        @Override
        public void run() {
            while(isListening) {
                Payload payload = receive();
                autonomousResponse.accept(payload);
            }
        }
    }
    public static class Identifier implements Serializable {
        public InetAddress address;
        public Integer port;

        @Override
        public String toString() {
            return address.toString() + ':' + port.toString();
        }
    }
}
