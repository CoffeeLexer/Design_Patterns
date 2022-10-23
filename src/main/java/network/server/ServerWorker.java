package network.server;

import client.gameObjects.Tank;
import network.data.Connection;
import network.data.Handshake;
import network.data.Payload;

import java.awt.event.KeyEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class ServerWorker implements Runnable {
    Connection connection = null;
    Set<Integer> keysPressed = null;
    int playerID = -1;
    public ServerWorker(Connection connection) {
        this.connection = connection;
        this.keysPressed = new HashSet<>();
    }
    @Override
    public void run() {
        try
        {
            boolean active = true;
            while(active)
            {
                Payload payload = (Payload)connection.input.readObject();
                switch (payload.method) {
                    case info -> {
                        Server.GetInstance().Info();
                    }
                    case keyReleased -> {
                        int keyCode = payload.GetData();
                        keysPressed.remove(keyCode);
                        Tank tank = (Tank)SEngine.GetInstance().Get(playerID);
                        switch (keyCode) {
                            case KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> {
                                KeyboardEvents.Rotate(tank, keysPressed);
                            }
                            case KeyEvent.VK_W, KeyEvent.VK_S -> {
                                KeyboardEvents.Drive(tank, keysPressed);
                            }
                        }
                    }
                    case keyPressed -> {
                        int keyCode = payload.GetData();
                        keysPressed.add(keyCode);
                        Tank tank = (Tank)SEngine.GetInstance().Get(playerID);
                        switch (keyCode) {
                            case KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> {
                                KeyboardEvents.Rotate(tank, keysPressed);
                            }
                            case KeyEvent.VK_W, KeyEvent.VK_S -> {
                                KeyboardEvents.Drive(tank, keysPressed);
                            }
                            case KeyEvent.VK_N, KeyEvent.VK_M, KeyEvent.VK_J, KeyEvent.VK_K -> {
                                KeyboardEvents.Shoot(tank, keyCode);
                            }
                        }
                    }
                    case joinGame -> {
                        int id = SEngine.GetInstance().Add(new Tank("images/tank-brown.png"));
                        playerID = id;
                        SEngine.GetInstance().SyncEngine(connection);
                        connection.output.writeObject(new Payload(Handshake.Method.tagPlayer, id));
                    }
                }
            }
        }
        catch (EOFException e)
        {
            Server.GetInstance().CloseConnection(connection);
            System.out.println("Client disconnected!");
        }
        catch (IOException e)
        {
            if(e.getMessage().equals("Connection reset"))
            {
                Server.GetInstance().CloseConnection(connection);
                System.out.println("Client Connection lost!");
            }
            else
            {
                throw new RuntimeException(e);
            }
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        finally {
            SEngine.GetInstance().Destroy(playerID);
        }
        System.out.printf("Stopping to listen! %s\n", Thread.currentThread().getName());
    }
}
