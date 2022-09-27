package client;

import client.panels.MainFrame;
import shared.Engine;

public class GameEngine extends Engine {

    private static GameEngine _instance;

    private MainFrame mainFrame;

    public GameEngine() {
        mainFrame = new MainFrame();
    }

    @Override
    protected void initializeMap() {

    }

    @Override
    protected void initializeObjects() {

    }

    @Override
    public void connectPlayer() {

    }

}