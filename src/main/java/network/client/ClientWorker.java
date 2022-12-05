package network.client;

import client.gameObjects.GameObject;
import network.data.Payload;
import network.server.Server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientWorker implements Runnable {
    Socket clientSocket = null;
    ObjectInputStream input = null;
    ObjectOutputStream output = null;
    public ClientWorker(Socket clientSocket, ObjectInputStream input, ObjectOutputStream output) {
        this.clientSocket = clientSocket;
        this.input = input;
        this.output = output;
    }
    @Override
    public void run() {
        try
        {
            boolean active = true;
            while(active)
            {
                Payload payload = (Payload)input.readObject();
                switch (payload.method) {
                    case setGameObject -> {
                        GameObject obj = payload.GetData();
                        CEngine.getInstance().Set(obj);
                    }
                    case syncEngine -> {
                        List<GameObject> list = payload.GetData();
                        for (GameObject obj: list) {
                            CEngine.getInstance().Set(obj);
                        }
                    }
                    case tagPlayer -> {
                        CEngine.getInstance().playerID = payload.GetData();
                    }
                    case removeGameObject -> {
                        CEngine.getInstance().Remove(payload.GetData());
                    }
                    case interpreter -> {
                        Client.GetInstance().blockingQueue.put(payload);
                    }
                }
            }
        }
        catch (EOFException e)
        {
            System.out.println("Client disconnected!");
        }
        catch (IOException e)
        {
            if(e.getMessage().equals("Connection reset"))
            {
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
