package client.panels;

import client.utilities.interpreter.*;
import network.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

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

        var textField = new JTextField("");

        java.util.List<Expression> list = new LinkedList<>();
        list.add(new ArgumentExpression());
        list.add(new CommandExpression());
        list.add(new PlayerExpression());
        list.add(new ValueExpression());
        list.add(new ServerExpression());

        var resultLabel = new JLabel("Result:");
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Context ctx = new Context();
                ctx.data = textField.getText();
                textField.setText("");
                for (var e: list) {
                    e.Interpret(ctx);
                }
                if(!ctx.error.equals("")) {
                    resultLabel.setText("<html>" + ctx.error.replaceAll("\n", "<br>").replaceAll("\t", "&ensp;") + "</html>");
                }
                else {
                    resultLabel.setText("<html>" + ctx.result.replaceAll("\n", "<br>").replaceAll("\t", "&ensp;") + "</html>");
                }
            }
        });
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getMinimumSize().height));

        panel.add(new JLabel("Interpreter Commands:"));
        panel.add(textField);


        panel.add(resultLabel);

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
