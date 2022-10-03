package client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Assets {
    private static Assets instance = new Assets();
    private Map<String, BufferedImage> files = null;
    private Assets() {
        files = new TreeMap<>();
    }
    public static Assets getInstance() {
        return instance;
    }
    public BufferedImage getFile(String filePath) {
        if(files.containsKey(filePath)) {
            return files.get(filePath);
        }
        else {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            files.put(filePath, img);
            return img;
        }
    }
}
