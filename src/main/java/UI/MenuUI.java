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
        var Onlineconstraints = new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        OnlineUI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        panel.add(OnlineUI, Onlineconstraints);




        JButton OfflineUI = new JButton("Offline App");
        var Offlineconstraints = new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        OfflineUI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        panel.add(OfflineUI, Offlineconstraints);




        JButton Instructions = new JButton("Instructions");
        var Instructionconstraints = new GridBagConstraints(3, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        Instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        panel.add(Instructions, Instructionconstraints);


        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }













    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) throws IOException {
        new MenuUI();
    }

}