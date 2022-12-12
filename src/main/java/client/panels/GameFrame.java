package client.panels;

import javax.swing.*;

import client.controls.Controller;
import network.client.CEngine;
import network.client.Client;
import network.client.UDPReceiver;
import network.data.Handshake;
import network.data.Payload;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameFrame extends JFrame {
    JLayeredPane layeredPane;
    JPanel mainPanel;
    public GameFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layeredPane = new JLayeredPane();
        //layeredPane.setBounds(0, 0, 1920, 1080);
        setSize(1600, 900);

        layeredPane.add(CEngine.getInstance().staticPanel, 1);
        layeredPane.add(CEngine.getInstance().dynamicPanel, 0);

        Client.GetInstance().JoinGame();

        Controller controller = new Controller();
        layeredPane.addKeyListener(controller);
        layeredPane.setFocusable(true);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        //mainPanel.setBounds(0, 0, 1600, 900);

        JPanel chat = new JPanel(new BorderLayout());



        //mainPanel.add(layeredPane, BorderLayout.CENTER);
        mainPanel.add(chat, BorderLayout.PAGE_END);

        JLabel log = new JLabel("<html>Welcome to the Game<br>type /username [Name] to change your name<br>Default name: Tank[id]</html>");
        //log.setMaximumSize(new Dimension(Integer.MAX_VALUE, log.getMinimumSize().height));
        log.setAlignmentY(Component.TOP_ALIGNMENT);

        var textField = new JTextField();

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String text = textField.getText();
                if(!text.equals("")) {
                    Payload payload = new Payload(Handshake.Method.chat, text);
                    Client.GetInstance().Invoke(payload);
                    if(text.substring(0, Math.max(text.indexOf(' '), 0)).equals("/username")) {
                        text = " changed name to " + text.substring(text.indexOf(' ') + 1);
                    }
                    var chatText = log.getText();
                    chatText = chatText.replace("</html>", "");
                    chatText += "<br>Me: " + text;
                    log.setText(chatText + "</html>");
                }
                layeredPane.grabFocus();
            }
        });
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getMinimumSize().height));
        chat.setMaximumSize(new Dimension(Integer.MAX_VALUE, chat.getMinimumSize().height));
        chat.add(textField);

        JPanel logPanel = new JPanel();
        logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));

        logPanel.add(log);
        CEngine.getInstance().log = log;

        JScrollPane scroller = new JScrollPane(logPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setAlignmentY(Component.TOP_ALIGNMENT);
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getMinimumSize().height));


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroller, layeredPane);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        add(mainPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                var d = e.getComponent().getSize();
                CEngine.getInstance().staticPanel.setCenter(d.width / 2 - 25, d.height / 2 - 25);
                CEngine.getInstance().dynamicPanel.setCenter(d.width / 2 - 25, d.height / 2 - 25);
            }
        });

        setVisible(true);
        setLocationRelativeTo(null);

    }
}
