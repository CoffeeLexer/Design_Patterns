package client.gameObjects;

public class Wall extends GameObject {

    public Wall(String imagePath) {
        super(imagePath, 100, 100);
        setPosition(200, 200);
    }
}
