package game.protocol;

import game.data.Payload;
import game.server.ClientAttachment;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TCP {
    public static class Client implements Runnable {
        private ObjectInputStream input;
        private ObjectOutputStream output;
        private BiConsumer<Client, Payload> autonomousResponse;
        private Socket socket;
        private boolean isListening = false;
        private Thread thread;
        private InetAddress udpAddress;
        private Integer udpPort;
        private DatagramPacket packet;
        private ClientAttachment attachment;

        public Client(Socket clientSocket) {
            try {
                output = new ObjectOutputStream(clientSocket.getOutputStream());
                input = new ObjectInputStream(clientSocket.getInputStream());
                socket = clientSocket;
                attachment = new ClientAttachment();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public ClientAttachment getAttachment() {return attachment;}
        public String identify() {
            return Objects.requireNonNullElseGet(thread, Thread::currentThread).getName();
        }

        /**
         * TCP protocol notification
         * @param payload data to send using TCP protocol
         */
        public void send(Payload payload) {
            try {
                output.writeObject(payload);
            } catch (SocketException e) {
                if(e.getMessage().equals("Broken pipe")) {
                    return;
                }
                else throw new RuntimeException(e);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public Payload receive() {
            try {
                return (Payload) input.readObject();
            } catch (EOFException e) {
                System.out.printf("%s - client disconnected\n", identify());
                return null;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public void bindUDP(InetAddress address, int port) {
            udpAddress = address;
            udpPort = port;
            packet = new DatagramPacket(new byte[0], 0, 0, address, port);
        }

        public void notify(UDP.Sender udp, Payload payload) {
            if(udpPort != null)
                udp.send(packet, payload);
        }
        public void autonomousListen(BiConsumer<Client, Payload> listen) {
            autonomousResponse = listen;
            isListening = true;
            thread = new Thread(this);
            thread.start();
        }
        @Override
        public void run() {
            System.out.printf("%s - started listening\n", identify());
            while(isListening) {
                Payload payload = receive();
                if(payload == null) {
                    isListening = false;
                }
                else {
                    autonomousResponse.accept(this, payload);
                }
            }
            System.out.printf("%s - stopped listening\n", identify());
        }
    }
    public static class ConnectionListener implements Runnable {
        private ServerSocket serverSocket;
        private Queue<Client> clients;
        private boolean isListening;
        private Thread thread;
        private BiConsumer<Client, Payload> defaultResponse;
        private Consumer<ClientAttachment> attachmentBinder;
        public ConnectionListener() {
            try {
                serverSocket = new ServerSocket(8080);
                clients = new ConcurrentLinkedQueue<>();
                isListening = false;
                defaultResponse = null;
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void setDefaultAutonomousResponse(BiConsumer<Client, Payload> response) {
            defaultResponse = response;
        }
        public void setAttachmentBinder(Consumer<ClientAttachment> binder) {
            attachmentBinder = binder;
        }
        public void listen() {
            isListening = true;
            thread = new Thread(this);
            thread.start();
        }

        public void stop() {
            isListening = false;
            try {
                // Dummy client to close Listener thread in most efficient way
                new Client(new Socket("localhost", 8080));
                thread.join();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            System.out.println("Started listening to new TCP Sockets");
            try {
                while (isListening) {
                    Socket clientSocket = serverSocket.accept();
                    if(isListening) {
                        var client = new Client(clientSocket);
                        if(defaultResponse != null)
                            client.autonomousListen(defaultResponse);
                        attachmentBinder.accept(client.getAttachment());
                        clients.add(client);
                        System.out.println("New Client added!");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Stopped listening to new TCP Sockets");
        }
        /**UDP protocol notification to all clients
         * @param payload object to send using TCP
         */
        public void notifyAll(UDP.Sender udp, Payload payload) {
            for (var client: clients) {
                client.notify(udp, payload);
            }
        }

        /**TCP protocol notification to all clients
         * @param payload object to send using TCP
         */
        public void notifyAll(Payload payload) {
            for (var client: clients) {
                client.send(payload);
            }
        }
    }
}
