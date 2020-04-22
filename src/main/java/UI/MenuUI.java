package UI;


import domain.TodoItem;
import utils.HTTPUtils;
import utils.JsonToObjectParser;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MenuUI extends JFrame implements ActionListener {

    public MenuUI() throws IOException {

        super("Main Menu TODO Application");
        UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 25)));

        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(1050, 550));



        JButton OnlineUI = new JButton("Online App");
        //var Onlineconstraints = new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        OnlineUI.setPreferredSize(new Dimension(200,50));
        OnlineUI.setFont(new Font("Serif", Font.PLAIN, 20));
        Dimension OnlineUISize = OnlineUI.getPreferredSize();
        OnlineUI.setBounds(350, 225, OnlineUISize.width, OnlineUISize.height);
        OnlineUI.setFocusPainted(false);
        OnlineUI.addActionListener(e -> {
            try {
                new todoUI();
                setVisible(false);
                dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        panel.add(OnlineUI);




        JButton OfflineUI = new JButton("Offline App");
        //var Offlineconstraints = new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        OfflineUI.setPreferredSize(new Dimension(200,50));
        OfflineUI.setFont(new Font("Serif", Font.PLAIN, 20));
        Dimension OfflineUISize = OfflineUI.getPreferredSize();
        OfflineUI.setBounds(550, 225, OfflineUISize.width, OfflineUISize.height);
        OfflineUI.addActionListener(e -> {
            try {
                new offlineUI();
                setVisible(false);
                dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        panel.add(OfflineUI);

        JButton instructions = new JButton("Instructions/Description");
        instructions.setPreferredSize(new Dimension(250,50));
        instructions.setFont(new Font("Serif", Font.PLAIN, 20));
        Dimension instructionsSize = instructions.getPreferredSize();
        instructions.setBounds(425, 275, instructionsSize.width, instructionsSize.height);
        panel.add(instructions);
        instructions.addActionListener(e -> {
            try {
                new instructionsUI();
                setVisible(false);
                dispose();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);




        JButton Instructions = new JButton("Instructions");
        var Instructionconstraints = new GridBagConstraints(3, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        Instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        panel.add(Instructions, Instructionconstraints);
    }







    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) throws IOException {
        new MenuUI();
    }

}