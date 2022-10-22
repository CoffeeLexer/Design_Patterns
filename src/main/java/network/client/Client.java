package network.client;

import network.data.Handshake;
import network.data.Payload;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    Socket clientSocket = null;
    ObjectInputStream input = null;
    ObjectOutputStream output = null;
    Thread listener = null;
    private static final Client instance = new Client();
    public static Client GetInstance() {
        return instance;
    }
    private Client() {
        try
        {
            clientSocket = new Socket("localhost", 8080);
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());

            output.writeObject(new Payload(Handshake.Method.login, UDPReceiver.getInstance().getPort()));

            ClientWorker worker = new ClientWorker(clientSocket, input, output);
            listener = new Thread(worker);
            listener.start();
        }
        catch (UnknownHostException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void KeyTyped(int keyCode) {
        Invoke(new Payload(Handshake.Method.keyTyped, keyCode));
    }
    public void KeyPressed(int keyCode) {
        Invoke(new Payload(Handshake.Method.keyPressed, keyCode));
    }
    public void KeyReleased(int keyCode) {
        Invoke(new Payload(Handshake.Method.keyReleased, keyCode));
    }
    public void JoinGame() {
        Invoke(new Payload(Handshake.Method.joinGame));
    }
    public void Info() {
        Invoke(new Payload(Handshake.Method.info));
    }
    public synchronized void Invoke(Payload payload) {
        try
        {
            output.writeObject(payload);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
