package client;

import javax.imageio.ImageIO;
import javax.swing.*;

import client.gameObjects.*;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.geom.*;

public class MainPanel extends JPanel implements ActionListener {

    ArrayList<GameObject> gameObjects;

    Timer timer = new Timer(1000 / 60, this);

    public MainPanel() {
        setPreferredSize(new Dimension(1920, 1080));
        setOpaque(true);
        gameObjects = new ArrayList<GameObject>();
        gameObjects.add(new Tank("thebible2.jpg"));
        gameObjects.add(new Wall("wall.jpg"));
        // gameObjects.add(new GameObject("wall.jpg"));
        timer.start();
    }

    private void updateObjects(Graphics2D g2d) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject.enabled) {
                gameObject.update();

                BufferedImage resized = gameObject.getImage();

                // int widthOfImage = resized.getWidth();
                // int heightOfImage = resized.getHeight();

                Point2D.Float goPosition = gameObject.getPosition();

                // g2d.rotate(Math.toRadians(gameObject.getAngle()), widthOfImage / 2 +
                // goPosition.getX(),
                // heightOfImage / 2 + goPosition.getY());

                g2d.drawImage(resized, null, (int) Math.round(goPosition.getX()), (int) Math.round(goPosition.getY()));
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.WHITE);

        updateObjects(g2d);

        // BufferedImage resized = jesus.getImage();

        // int widthOfImage = resized.getWidth();
        // int heightOfImage = resized.getHeight();

        // g2d.rotate(Math.toRadians(jesus.getAngle()), widthOfImage / 2 + jesus.getX(),
        // heightOfImage / 2 + jesus.getY());
        // g2d.drawImage(resized, null, jesus.getX(), jesus.getY());

        // g2d.drawImage(newImageFromBuffer, null, x, y);
        getToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}

// int w = img.getWidth(null);
// int h = img.getHeight(null);
// if(!lineDrawn){
// g2d.setPaint(Color.BLUE);
// g2d.setStroke(new BasicStroke(5));
// g2d.drawLine(0, 0, 1920, 1080);
// lineDrawn = false;
// }

// g2d.drawRect(x, y, 100, 100);

// AffineTransform at = new AffineTransform();
// at.rotate(imgAngle, 0, 0);
// at.scale(0.1, 0.1);
// // at.translate(x, y);
// g2d.transform(at);
// g2d.drawImage(img, x, 0, null);

// g2d.setColor(Color.red);
// g2d.setStroke(new BasicStroke(20));
// g2d.drawLine(x, y, x, y);

// private BufferedImage resizeImage(BufferedImage imageToRotate) {
// int widthOfImage = imageToRotate.getWidth();
// int heightOfImage = imageToRotate.getHeight();
// int typeOfImage = BufferedImage.TYPE_INT_ARGB;

// BufferedImage newImageFromBuffer = new BufferedImage((int)(widthOfImage *
// scale), (int)(heightOfImage * scale), typeOfImage);

// Graphics2D graphics2D = newImageFromBuffer.createGraphics();
// graphics2D.drawImage(img, 0, 0, (int)(widthOfImage * scale),
// (int)(heightOfImage * scale), null);
// graphics2D.dispose();

// return newImageFromBuffer;
// }