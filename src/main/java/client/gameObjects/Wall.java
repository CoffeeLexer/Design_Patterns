package client.gameObjects;

public class Wall extends GameObject {

    public Wall(String imagePath) {
        super(imagePath, 80, 80);
        setPosition(200, 200);
    }
}
