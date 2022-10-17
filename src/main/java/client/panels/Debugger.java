package client.panels;

import network.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Debugger {
    public static void main(String[] args) {
        // TODO: Fix double client/server listeners problem

        JFrame frame = new JFrame("Family friendly title 0.2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);

        panel.add(parseButton("Join Game", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Client.GetInstance().JoinGame();
            }
        }));

        panel.add(parseButton("Info", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Client.GetInstance().Info();
            }
        }));

        frame.add(panel);
        frame.setSize(800, 450);
        frame.setVisible(true);
    }
    private static JButton parseButton(String name, ActionListener action) {
        JButton jb = new JButton(name);
        jb.addActionListener(action);
        jb.setMaximumSize(new Dimension(Integer.MAX_VALUE, jb.getMinimumSize().height));
        return jb;
    }
}
