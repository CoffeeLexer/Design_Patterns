package client;

import network.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        JFrame frame = new JFrame("TankumShot 0.1");

        network.Client c = new network.Client();

        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);

        JButton jb1 = new JButton("Start");
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.Message("Start");
            }
        });


        JButton jb2 = new JButton("Quit");

        panel.add(jb1);
        panel.add(jb2);

        frame.add(panel);

        frame.setSize(800, 450);
        frame.setVisible(true);
    }
}
