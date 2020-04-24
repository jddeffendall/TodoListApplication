package UI;


import domain.TodoItem;
import utils.DatabaseUtils;
import utils.HTTPUtils;
import utils.JsonToObjectParser;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MenuUI extends JFrame {

    public MenuUI() throws IOException {

        super("Main Menu TODO Application");
        UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 25)));

        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(1050, 550));
        super.setResizable(false);
        panel.setBackground(Color.white);


        JButton OnlineUI = new JButton("Launch Application");
        OnlineUI.setPreferredSize(new Dimension(200,50));
        OnlineUI.setFont(new Font("Serif", Font.PLAIN, 20));
        Dimension OnlineUISize = OnlineUI.getPreferredSize();
        OnlineUI.setBounds(350, 275, OnlineUISize.width, OnlineUISize.height);
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


        JButton instructions = new JButton("Instructions/Credits");
        instructions.setPreferredSize(new Dimension(200,50));
        instructions.setFont(new Font("Serif", Font.PLAIN, 20));
        Dimension instructionsSize = instructions.getPreferredSize();
        instructions.setBounds(550, 275, instructionsSize.width, instructionsSize.height);
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


        ImageIcon image = new ImageIcon("src/rsz_todo_logo.png");
        JLabel sickAssLogo = new JLabel(image);

        sickAssLogo.setBounds(319,0, 456,284);
        sickAssLogo.setVisible(true);

        panel.add(sickAssLogo);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        
    }

    public static void main(String[] args) throws IOException {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                HTTPUtils httpUtils = new HTTPUtils();
                JsonToObjectParser parser = new JsonToObjectParser();
                var todoManager = new DatabaseUtils();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm");

                LocalDateTime later = LocalDateTime.now().plusDays(1);
                if (httpUtils.checkConnection()) {
                    // If online, constantly check cloud to see if any item has become overdue, and sends alert
                    while (LocalDateTime.now().isBefore(later)) {
                        try {
                            String todoItemsJson = httpUtils.getAllUserTodosJsonString();
                            TodoItem[] items = parser.JsonStringToObjectArray(todoItemsJson);

                            for (TodoItem i : items) {
                                LocalDateTime due = LocalDateTime.parse(i.getDueDate(), formatter);
                                LocalDateTime now = LocalDateTime.now();

                                if (now.isAfter(due) && i.getOverdue().equals("false") && i.getCompleted().equals("false")) {
                                    httpUtils.setTodoItemOverdue(i);
                                    i.setOverdue();
                                    JOptionPane.showMessageDialog(null, "Item with ID " + i.getId() + " and Title " + i.getTitle() + " is overdue");
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    // If offline, check local database for any overdue items, then sends alert
                    while (LocalDateTime.now().isBefore(later)) {
                            List<TodoItem> allDatabaseItems = todoManager.getAllItems();
                            for (TodoItem i : allDatabaseItems) {
                                LocalDateTime now = LocalDateTime.now();
                                LocalDateTime due = LocalDateTime.parse(i.getDueDate(), formatter);

                                if (now.isAfter(due) && i.getOverdue().equals("false") && i.getCompleted().equals("false")) {
                                    todoManager.setOverdue(i.getId());
                                    i.setOverdue();
                                    JOptionPane.showMessageDialog(null, "Item with ID " + i.getId() + " and Title " + i.getTitle() + " is overdue");
                                }
                            }
                        }
                    }
            }
        };

        Thread t = new Thread(r);
        t.start();
        new MenuUI();
    }
}