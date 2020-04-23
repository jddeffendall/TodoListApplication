package UI;

import domain.TodoItem;
import utils.DatabaseUtils;
import utils.HTTPUtils;
import utils.JsonToObjectParser;
import utils.UIUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class todoUI extends JFrame {


    public todoUI() throws IOException {


        super("Todo List Application");
        UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 25)));

        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(1050, 550));
        super.setResizable(false);


        HTTPUtils httpUtils = new HTTPUtils();
        UIUtils uiUtils = new UIUtils();
        JsonToObjectParser parser = new JsonToObjectParser();

        // Get current cloud data and display it in table
        String allUserTodosJson = httpUtils.getAllUserTodosJsonString();
        TodoItem[] allUserTodos = parser.JsonStringToObjectArray(allUserTodosJson);
        String[][] data = uiUtils.formatDataForTable(allUserTodos);
        String[] columnNames = {"ID", "Title", "Created", "Due", "Completed", "Overdue", "Completed Date"};

        JTable items = new JTable(data, columnNames);
        JScrollPane jScrollPane = new JScrollPane(items, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setBounds(0, 0, 600, 800);
        panel.add(jScrollPane);
        items.getColumnModel().getColumn(0).setPreferredWidth(2);
        items.getColumnModel().getColumn(4).setPreferredWidth(50);
        items.getColumnModel().getColumn(5).setPreferredWidth(50);


        JLabel titleLabel = new JLabel("Enter Title of Item:");
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(600, 0, 200, 50);
        panel.add(titleLabel);

        JTextField titleInput = new JTextField("");
        titleInput.setBounds(600, 50, 200, 50);
        titleInput.setBackground(Color.lightGray);
        panel.add(titleInput);

        JLabel dueDateLabel = new JLabel("<HTML>Enter Due Date For Item (mm dd yyyy hh:mm):<HTML>");
        dueDateLabel.setBounds(600, 100, 200, 50);
        dueDateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        dueDateLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dueDateLabel.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(dueDateLabel);

        JTextField dueDateInput = new JTextField("");
        dueDateInput.setBounds(600, 150, 200, 50);
        dueDateInput.setBackground(Color.lightGray);
        panel.add(dueDateInput);



        JButton AddEvent = new JButton("Add To Schedule");
        AddEvent.setPreferredSize(new Dimension(250, 200));
        Dimension size = AddEvent.getPreferredSize();
        AddEvent.setFocusPainted(false);
        AddEvent.setBounds(800, 0, size.width, size.height);
        AddEvent.addActionListener(e -> {
            String title = titleInput.getText();
            String due = dueDateInput.getText();
            titleInput.setText(null);
            dueDateInput.setText(null);
            try {
                String resultJson = httpUtils.addTodoItem(title, due);

                String updatedUserTodos = httpUtils.getAllUserTodosJsonString();
                TodoItem[] updatedItems = parser.JsonStringToObjectArray(updatedUserTodos);
                String[][] updatedData = uiUtils.formatDataForTable(updatedItems);

                JTable updatedTable = new JTable(updatedData, columnNames);
                JScrollPane updatedJScrollPane = new JScrollPane(updatedTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                updatedJScrollPane.setBounds(0, 0, 600, 800);
                panel.add(updatedJScrollPane);
                updatedTable.getColumnModel().getColumn(0).setPreferredWidth(2);
                updatedTable.getColumnModel().getColumn(4).setPreferredWidth(50);
                updatedTable.getColumnModel().getColumn(5).setPreferredWidth(50);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            items.repaint();
        });

        panel.add(AddEvent);

        JLabel completeEventLabel = new JLabel("<HTML>Enter ID of item for one of these actions: <HTML>");
        completeEventLabel.setHorizontalAlignment(SwingConstants.LEFT);
        completeEventLabel.setVerticalAlignment(SwingConstants.CENTER);
        completeEventLabel.setBounds(600, 200, 200, 50);
        completeEventLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        panel.add(completeEventLabel);

        JTextField completeEventById = new JTextField("");
        completeEventById.setBounds(800, 200, 250, 50);
        completeEventById.setBackground(Color.lightGray);
        panel.add(completeEventById);


        JButton completeEvent = new JButton("<HTML><center>Complete</center><HTML>"); //html tags wrap the text and centers it
        completeEvent.setPreferredSize(new Dimension(150, 100));
        Dimension completeEventSize = completeEvent.getPreferredSize();
        completeEvent.setBounds(600, 250, completeEventSize.width, completeEventSize.height);
        completeEvent.setFocusPainted(false);
        panel.add(completeEvent);
        completeEvent.addActionListener(e -> {
            String idToComplete = completeEventById.getText();
            completeEventById.setText(null);
            try {
                String itemJson = httpUtils.getTodoItemJsonString(idToComplete);
                TodoItem item = parser.JsonStringToOneObject(itemJson);
                httpUtils.completeTodoItem(item);

                String completedUserTodos = httpUtils.getAllUserTodosJsonString();
                TodoItem[] completedItems = parser.JsonStringToObjectArray(completedUserTodos);
                String[][] completedData = uiUtils.formatDataForTable(completedItems);

                JTable completedTable = new JTable(completedData, columnNames);
                JScrollPane completedJScrollPane = new JScrollPane(completedTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                completedJScrollPane.setBounds(0, 0, 600, 800);
                panel.add(completedJScrollPane);
                completedTable.getColumnModel().getColumn(0).setPreferredWidth(2);
                completedTable.getColumnModel().getColumn(4).setPreferredWidth(50);
                completedTable.getColumnModel().getColumn(5).setPreferredWidth(50);

            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(this, "ERROR: Couldn't complete item!");
            }
        });

        JButton snooze = new JButton("<HTML><center>Snooze</center><HTML>"); // centers the text HTML tags necessary for center tags to work
        snooze.setPreferredSize(new Dimension(150, 100));
        Dimension snoozeSize = snooze.getPreferredSize();
        snooze.setBounds(900, 250, snoozeSize.width, snoozeSize.height);
        snooze.setFocusPainted(false);
        panel.add(snooze);
        snooze.addActionListener(e -> {
            String idToSnooze = completeEventById.getText();
            completeEventById.setText(null);
            try {
                String todoItemToSnoozeJson = httpUtils.getTodoItemJsonString(idToSnooze);
                TodoItem itemToSnooze = parser.JsonStringToOneObject(todoItemToSnoozeJson);
                httpUtils.snooze(itemToSnooze);

                String snoozedUserTodos = httpUtils.getAllUserTodosJsonString();
                TodoItem[] snoozedItems = parser.JsonStringToObjectArray(snoozedUserTodos);
                String[][] snoozedData = uiUtils.formatDataForTable(snoozedItems);

                JTable snoozedTable = new JTable(snoozedData, columnNames);
                JScrollPane snoozedJScrollPane = new JScrollPane(snoozedTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                snoozedJScrollPane.setBounds(0, 0, 600, 800);
                panel.add(snoozedJScrollPane);
                snoozedTable.getColumnModel().getColumn(0).setPreferredWidth(2);
                snoozedTable.getColumnModel().getColumn(4).setPreferredWidth(50);
                snoozedTable.getColumnModel().getColumn(5).setPreferredWidth(50);

            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(this, "ERROR: Couldn't snooze item!");
            }
        });

        JButton Cancel = new JButton("<HTML><center>Delete</center><HTML>");
        Cancel.setPreferredSize(new Dimension(150, 100));
        Dimension CancelSize = Cancel.getPreferredSize();
        Cancel.setBounds(750, 250, CancelSize.width, CancelSize.height);
        Cancel.setFocusPainted(false);
        panel.add(Cancel);
        Cancel.addActionListener(e -> {
            String stringId = completeEventById.getText();
            completeEventById.setText(null);
            try {
                httpUtils.deleteTodoItem(stringId);

                String deletedUserTodos = httpUtils.getAllUserTodosJsonString();
                TodoItem[] deletedItems = parser.JsonStringToObjectArray(deletedUserTodos);
                String[][] deletedData = uiUtils.formatDataForTable(deletedItems);

                JTable deletedTable = new JTable(deletedData, columnNames);
                JScrollPane deletedJScrollPane = new JScrollPane(deletedTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                deletedJScrollPane.setBounds(0, 0, 600, 800);
                panel.add(deletedJScrollPane);
                deletedTable.getColumnModel().getColumn(0).setPreferredWidth(2);
                deletedTable.getColumnModel().getColumn(4).setPreferredWidth(50);
                deletedTable.getColumnModel().getColumn(5).setPreferredWidth(50);

            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(this, "ERROR: Couldn't cancel item!");
            }
        });


        JButton pieChart = new JButton("PieChart");
        pieChart.setPreferredSize(new Dimension(225, 100));
        Dimension pieChartSize = pieChart.getPreferredSize();
        pieChart.setBounds(600, 350, pieChartSize.width, pieChartSize.height);
        pieChart.setFocusPainted(false);
        panel.add(pieChart);
        pieChart.addActionListener(e -> {
            try {
                new chartUI("Todo List Overview");
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(this, "ERROR: Couldn't display pie chart!");
            }
        });


        JButton backtoMenu = new JButton("Back to Main Menu");
        backtoMenu.setPreferredSize(new Dimension(450, 100));
        Dimension refreshSize = backtoMenu.getPreferredSize();
        backtoMenu.setBounds(600, 450, refreshSize.width, refreshSize.height);
        backtoMenu.setFocusPainted(false);
        panel.add(backtoMenu);
        backtoMenu.addActionListener(e -> {
            try {
                setVisible(false); //you can't see me!
                dispose();
                new MenuUI();
            } catch (IOException ex) {

            }
        });

        JButton sync = new JButton("<HTML><center>Sync For Offline</center><HTML>");
        sync.setPreferredSize(new Dimension(225, 100));
        Dimension syncSize = sync.getPreferredSize();
        sync.setFocusPainted(false);
        sync.setBounds(825, 350, syncSize.width, syncSize.height);
        sync.addActionListener(e -> {
            try {
                var todoManager = new DatabaseUtils();

                String allItemsString = httpUtils.getAllUserTodosJsonString();
                TodoItem[] allItems = parser.JsonStringToObjectArray(allItemsString);
                todoManager.updateOfflineTable(allItems);

                setVisible(false);
                dispose();
                new offlineUI();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        panel.add(sync);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}