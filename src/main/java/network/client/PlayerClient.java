package network.client;

import network.data.Connection;
import network.data.Handshake;
import network.data.Payload;
import network.server.SEngine;

public class PlayerClient {
    private Integer id;
    private String username = "";
    public Connection connection;

    public PlayerClient(int id) {
        this.id = id;
    }
    public void setId(Integer id) {
        if(id < 0) return;
        this.id = id;
        SEngine.GetInstance().chat.attach(this);
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return this.username;
    }
    public int getId() {
        return id;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void receiveMessage(String msg) {
        if(connection != null) {
            try {
                connection.writeObject(new Payload(Handshake.Method.chat, msg));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
