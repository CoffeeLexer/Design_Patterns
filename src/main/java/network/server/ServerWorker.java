package network.server;

import client.components.tankDecorator.LabelDecorator;
import client.gameObjects.Tank;
import client.utilities.interpreter.Context;
import network.client.ClientId;
import network.data.Connection;
import network.data.Handshake;
import network.data.Payload;
import network.levelManagement.LevelManager;

import java.awt.event.KeyEvent;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ServerWorker implements Runnable {
    Connection connection = null;
    Set<Integer> keysPressed = null;
    ClientId playerID = new ClientId(-1);
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
                Payload payload = (Payload)connection.readObject();
                switch (payload.method) {
                    case interpreter -> {
                        Context ctx = payload.GetData();
                        switch (ctx.method) {
                            case destroyObject -> {
                                int v = -1;
                                try {
                                    v = Integer.parseInt(ctx.value);
                                }
                                catch (Exception ignored) {}
                                if(v == -1) {
                                    ctx.error = "Value is not integer!";
                                }
                                else {
                                    var obj = SEngine.GetInstance().gameObjects.get(v);
                                    if(obj == null) {
                                        ctx.error = "Object not found!";
                                    }
                                    else {
                                        SEngine.GetInstance().Destroy(obj);
                                        ctx.result = "Object destroyed!";
                                    }
                                }
                                connection.writeObject(new Payload(Handshake.Method.interpreter, ctx));
                            }
                            case kick -> {
                                int p = -1;
                                try {
                                    p = Integer.parseInt(ctx.player);
                                }
                                catch (Exception ignored) {}
                                if(p == -1) {
                                    ctx.error = "Player is not id!";
                                }
                                else {
                                    var obj = SEngine.GetInstance().gameObjects.get(p);
                                    if(obj == null || !obj.getClass().equals(Tank.class)) {
                                        ctx.error = "Player not found!";
                                    }
                                    else {
                                        SEngine.GetInstance().Destroy(obj);
                                        ctx.result = "Player Kicked!";
                                    }
                                }
                                connection.writeObject(new Payload(Handshake.Method.interpreter, ctx));
                            }
                            case kill -> {
                                int p = -1;
                                try {
                                    p = Integer.parseInt(ctx.player);
                                }
                                catch (Exception ignored) {}
                                if(p == -1) {
                                    ctx.error = "Player is not id!";
                                }
                                else {
                                    var obj = SEngine.GetInstance().gameObjects.get(p);
                                    if(obj == null || !obj.getClass().equals(Tank.class)) {
                                        ctx.error = "Player not found!";
                                    }
                                    else {
                                        SEngine.GetInstance().Destroy(obj);
                                        ctx.result = "Player Killed!";
                                    }
                                }
                                connection.writeObject(new Payload(Handshake.Method.interpreter, ctx));
                            }
                            case listObjects -> {
                                SEngine.GetInstance().gameObjects.values().forEach(e -> {
                                    ctx.result += "Object " + e.uniqueID + ": ";
                                    ctx.result += e + "\n";
                                });
                                connection.writeObject(new Payload(Handshake.Method.interpreter, ctx));
                            }
                            case listPlayers -> {
                                SEngine.GetInstance().gameObjects.values().forEach(e -> {
                                    if(e.getClass().equals(Tank.class)) {
                                        ctx.result += "Player " + e.uniqueID + ": ";
                                        ctx.result += e + "\n";
                                    }
                                });
                                connection.writeObject(new Payload(Handshake.Method.interpreter, ctx));
                            }
                            case setHealth -> {
                                int p = -1;
                                try {
                                    p = Integer.parseInt(ctx.player);
                                }
                                catch (Exception ignored) {}
                                if(p == -1) {
                                    ctx.error = "Player is not id!";
                                }
                                else {
                                    var v = -1;
                                    try {
                                        v = Integer.parseInt(ctx.value);
                                    }
                                    catch (Exception ignored) {}
                                    if(v < 1) {
                                        ctx.error = "Value is not positive number!";
                                    }
                                    else {
                                        var obj = SEngine.GetInstance().gameObjects.get(p);
                                        if(obj == null || !obj.getClass().equals(Tank.class)) {
                                            ctx.error = "Player not found!";
                                        }
                                        else {
                                            var t = (Tank) obj;
                                            t.setHealth(v);
                                            ctx.result = "Player health updated!";
                                        }
                                    }
                                }
                                connection.writeObject(new Payload(Handshake.Method.interpreter, ctx));
                            }
                        }
                    }
                    case info -> {
                        Server.GetInstance().Info();
                    }
                    case keyReleased -> {
                        int keyCode = payload.GetData();
                        keysPressed.remove(keyCode);
                        Tank tank = (Tank)SEngine.GetInstance().Get(playerID.value);
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
                        Tank tank = (Tank)SEngine.GetInstance().Get(playerID.value);
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
                            case KeyEvent.VK_P -> {
                                KeyboardEvents.InvokeShield(tank);
                            }
                            case KeyEvent.VK_T -> {
                                KeyboardEvents.TimeTravel(tank);
                            }
                            case KeyEvent.VK_C -> {
                                KeyboardEvents.Clone(tank);
                            }
                        }
                    }
                    case joinGame -> {
                        // Tank playerTank = new Tank("images/tank-blue.png");
                        // LabelDecorator labelDecorator = new LabelDecorator(playerTank);

                        // int id = SEngine.GetInstance().Add(playerTank);
                        // playerID = id;
                        // String playerName = "Player: " + Integer.toString(playerID);
                        // labelDecorator.decorate(playerName);

                        SEngine.GetInstance().SyncEngine(connection);
                        connection.writeObject(new Payload(Handshake.Method.tagPlayer, LevelManager.getInstance().spawnPlayer(playerID)));
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
            LevelManager.getInstance().disconnectPlayer(playerID);
        }
        System.out.printf("Stopping to listen! %s\n", Thread.currentThread().getName());
    }
}
