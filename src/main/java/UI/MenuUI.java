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
        super.setResizable(false);



        JButton OnlineUI = new JButton("Online App");
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
        OfflineUI.setPreferredSize(new Dimension(200,50));
        OfflineUI.setFont(new Font("Serif", Font.PLAIN, 20));
        Dimension OfflineUISize = OfflineUI.getPreferredSize();
        OfflineUI.setFocusPainted(false);
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

        JButton instructions = new JButton("Instructions/Credits");
        instructions.setPreferredSize(new Dimension(250,50));
        instructions.setFont(new Font("Serif", Font.PLAIN, 20));
        Dimension instructionsSize = instructions.getPreferredSize();
        instructions.setBounds(425, 275, instructionsSize.width, instructionsSize.height);
        instructions.setFocusPainted(false);
        panel.add(instructions);
        instructions.addActionListener(e -> {
            try {
                new instructionsUI();
                setVisible(false);
                dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "ERROR: Couldn't go to instructions!");
            }
        });


        ImageIcon logo = new ImageIcon("TODO LOGO.PNG");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) throws IOException {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                HTTPUtils httpUtils = new HTTPUtils();
                JsonToObjectParser parser = new JsonToObjectParser();

                LocalDateTime later = LocalDateTime.now().plusDays(1);
                while (LocalDateTime.now().isBefore(later)) {
                    try {
                        String todoItemsJson = httpUtils.getAllUserTodosJsonString();
                        TodoItem[] items = parser.JsonStringToObjectArray(todoItemsJson);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm");

                        for (TodoItem i : items) {
                            LocalDateTime due = LocalDateTime.parse(i.getDueDate(), formatter);
                            LocalDateTime now = LocalDateTime.now();
                            if (now.isAfter(due) && i.getOverdue().equals("false")) {
                                httpUtils.setTodoItemOverdue(i);
                                i.setOverdue();
                                JOptionPane.showMessageDialog(null, "Item with ID " + i.getId() + " and Title " + i.getTitle() + " is overdue");
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t = new Thread(r);
        t.start();
        new MenuUI();
    }

}