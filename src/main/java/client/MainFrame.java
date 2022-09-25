package client;

import javax.swing.*;
import client.controls.ControlInput;
import java.awt.*;
import java.awt.geom.*;

public class MainFrame extends JFrame {

    public MainPanel panel;

    public MainFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new MainPanel();

        // add instructions text
        JLabel instructionsText = new JLabel("Shoot = Spacebar");
        instructionsText.setFont(new Font("Calibri", Font.BOLD, 26));
        panel.add(instructionsText);

        addKeyListener(ControlInput.getInstance());

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
