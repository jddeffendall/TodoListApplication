package UI;

import domain.TodoItem;
import utils.DatabaseUtils;
import utils.HTTPUtils;
import utils.JsonToObjectParser;
import utils.UIUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


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
        panel.setBackground(Color.white);


        HTTPUtils httpUtils = new HTTPUtils();
        UIUtils uiUtils = new UIUtils();
        JsonToObjectParser parser = new JsonToObjectParser();
        var todoManager = new DatabaseUtils();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm");
        String[] columnNames = {"ID", "Title", "Created", "Due", "Completed", "Overdue", "Completed Date"};

        if (httpUtils.checkConnection()) {
            // Get current cloud data and display it in table
            String allUserTodosJson = httpUtils.getAllUserTodosJsonString();
            TodoItem[] allUserTodos = parser.JsonStringToObjectArray(allUserTodosJson);
            String[][] data = uiUtils.formatDataForTable(allUserTodos);

            JTable items = new JTable(data, columnNames);
            JScrollPane jScrollPane = new JScrollPane(items, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jScrollPane.setBounds(0, 0, 600, 800);
            panel.add(jScrollPane);
            items.getColumnModel().getColumn(0).setPreferredWidth(2);
            items.getColumnModel().getColumn(4).setPreferredWidth(50);
            items.getColumnModel().getColumn(5).setPreferredWidth(50);
            items.setBackground(Color.white);
            jScrollPane.setBackground(Color.white);
        } else {
            // If no Internet, display local database items
            List<TodoItem> allUserTodos = todoManager.getAllItems();
            TodoItem[] allUserTodosArray = new TodoItem[allUserTodos.size()];
            for (int i=0; i<allUserTodos.size(); i++) {
                allUserTodosArray[i] = allUserTodos.get(i);
            }

            String[][] data = uiUtils.formatDataForTable(allUserTodosArray);
            JTable items = new JTable(data, columnNames);
            JScrollPane jScrollPane = new JScrollPane(items, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jScrollPane.setBounds(0, 0, 600, 800);
            panel.add(jScrollPane);
            items.getColumnModel().getColumn(0).setPreferredWidth(2);
        }


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

        JLabel dueDateLabel = new JLabel("<HTML>Enter Due Date For Item (mm dd yyyy hh:mm):<HTML>"); //html tags wrap the text and centers it
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
        AddEvent.setFocusPainted(false); // this gets rid of the little border around the text of a button.
        AddEvent.setBounds(800, 0, size.width, size.height);

        AddEvent.addActionListener(e -> {
            String title = titleInput.getText();
            String due = dueDateInput.getText();
            titleInput.setText(null);
            dueDateInput.setText(null);

            try {
                if (httpUtils.checkConnection()) {
                    httpUtils.addTodoItem(title, due);

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
                } else {
                    LocalDateTime now = LocalDateTime.now();
                    String nowString = now.format(formatter);
                    int highestID = todoManager.findHighestID();
                    String newItemID = Integer.toString(highestID + 1);

                    TodoItem newItem = new TodoItem(title, "Team2", nowString, due, "false", "false", newItemID, "Incomplete");
                    todoManager.addItemToDB(newItem);

                    List<TodoItem> addedItems = todoManager.getAllItems();
                    TodoItem[] addedTodosArray = new TodoItem[addedItems.size()];
                    for (int i=0; i<addedItems.size(); i++) {
                        addedTodosArray[i] = addedItems.get(i);
                    }
                    String[][] addedData = uiUtils.formatDataForTable(addedTodosArray);

                    JTable addedTable = new JTable(addedData, columnNames);
                    JScrollPane addedJScrollPane = new JScrollPane(addedTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    addedJScrollPane.setBounds(0, 0, 600, 800);
                    panel.add(addedJScrollPane);
                    addedTable.getColumnModel().getColumn(0).setPreferredWidth(2);
                    addedTable.getColumnModel().getColumn(4).setPreferredWidth(50);
                    addedTable.getColumnModel().getColumn(5).setPreferredWidth(50);
                }
                } catch(IOException ex){
                    ex.printStackTrace();
                }
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

        
        JButton completeEvent = new JButton("<HTML><center>Complete</center><HTML>");
        completeEvent.setPreferredSize(new Dimension(150, 100));
        Dimension completeEventSize = completeEvent.getPreferredSize();
        completeEvent.setBounds(600, 250, completeEventSize.width, completeEventSize.height);
        completeEvent.setFocusPainted(false);
        panel.add(completeEvent);
        completeEvent.addActionListener(e -> {
            String idToComplete = completeEventById.getText();
            completeEventById.setText(null);
            try {
                if (httpUtils.checkConnection()) {
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
                } else {
                    todoManager.completeItem(idToComplete);

                    List<TodoItem> completedItems = todoManager.getAllItems();
                    TodoItem[] completedTodos = new TodoItem[completedItems.size()];
                    for (int i=0; i<completedItems.size(); i++) {
                        completedTodos[i] = completedItems.get(i);
                    }
                    String[][] completedData = uiUtils.formatDataForTable(completedTodos);

                    JTable completedTable = new JTable(completedData, columnNames);
                    JScrollPane completedJScrollPane = new JScrollPane(completedTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    completedJScrollPane.setBounds(0, 0, 600, 800);
                    panel.add(completedJScrollPane);
                    completedTable.getColumnModel().getColumn(0).setPreferredWidth(2);
                    completedTable.getColumnModel().getColumn(4).setPreferredWidth(50);
                    completedTable.getColumnModel().getColumn(5).setPreferredWidth(50);
                }
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
                if (httpUtils.checkConnection()) {
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
                } else {
                    todoManager.snooze(idToSnooze);

                    List<TodoItem> snoozedItems = todoManager.getAllItems();
                    TodoItem[] snoozedTodos = new TodoItem[snoozedItems.size()];
                    for (int i=0; i<snoozedItems.size(); i++) {
                        snoozedTodos[i] = snoozedItems.get(i);
                    }
                    String[][] snoozedData = uiUtils.formatDataForTable(snoozedTodos);

                    JTable snoozedTable = new JTable(snoozedData, columnNames);
                    JScrollPane snoozedJScrollPane = new JScrollPane(snoozedTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    snoozedJScrollPane.setBounds(0, 0, 600, 800);
                    panel.add(snoozedJScrollPane);
                    snoozedTable.getColumnModel().getColumn(0).setPreferredWidth(2);
                    snoozedTable.getColumnModel().getColumn(4).setPreferredWidth(50);
                    snoozedTable.getColumnModel().getColumn(5).setPreferredWidth(50);
                }

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
                if (httpUtils.checkConnection()) {
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
                } else {
                    todoManager.deleteItem(stringId);

                    List<TodoItem> deletedItems = todoManager.getAllItems();
                    TodoItem[] deletedTodos = new TodoItem[deletedItems.size()];
                    for (int i=0; i<deletedItems.size(); i++) {
                        deletedTodos[i] = deletedItems.get(i);
                    }
                    String[][] deletedData = uiUtils.formatDataForTable(deletedTodos);

                    JTable deletedTable = new JTable(deletedData, columnNames);
                    JScrollPane deletedJScrollPane = new JScrollPane(deletedTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    deletedJScrollPane.setBounds(0, 0, 600, 800);
                    panel.add(deletedJScrollPane);
                    deletedTable.getColumnModel().getColumn(0).setPreferredWidth(2);
                    deletedTable.getColumnModel().getColumn(4).setPreferredWidth(50);
                    deletedTable.getColumnModel().getColumn(5).setPreferredWidth(50);
                }
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
                if (httpUtils.checkConnection()) {
                    new chartUI("Todo List Overview");
                } else {
                    new offlineChartUI("Offline Todo List Overview");
                }
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(this, "ERROR: Couldn't display pie chart!");
            }
        });


        JButton backtoMenu = new JButton("Back to Menu");
        backtoMenu.setPreferredSize(new Dimension(225, 100));
        Dimension backtoMenuSize = backtoMenu.getPreferredSize();
        backtoMenu.setBounds(600, 450, backtoMenuSize.width, backtoMenuSize.height);
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
        JButton updateCloud = new JButton("Update Cloud");
        updateCloud.setPreferredSize(new Dimension(225, 100));
        Dimension updateCloudSize = updateCloud.getPreferredSize();
        updateCloud.setBounds(825, 450, updateCloudSize.width, updateCloudSize.height);
        updateCloud.setFocusPainted(false);
        panel.add(updateCloud);
        updateCloud.addActionListener(e -> {
            try {
                if (httpUtils.checkConnection()) {
                    String allJson = httpUtils.getAllUserTodosJsonString();
                    TodoItem[] updatedItems = parser.JsonStringToObjectArray(allJson);
                    for (TodoItem i : updatedItems) {
                        httpUtils.deleteTodoItem(i.getId());
                    }
                    List<TodoItem> offlineItems = todoManager.getAllItems();
                    for (TodoItem i : offlineItems) {
                        httpUtils.addTodoItem(i.getTitle(), i.getDueDate(), i.getCreatedDate(), i.getCompleted(), i.getOverdue(), i.getCompletedDate(), i.getOwner());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You need Internet connection to update cloud!");
                }
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(null, "ERROR: Couldn't update cloud!");
            }
        });

        JButton sync = new JButton("<HTML><center>Sync For Offline</center><HTML>");
        sync.setPreferredSize(new Dimension(225, 100));
        Dimension syncSize = sync.getPreferredSize();
        sync.setFocusPainted(false);
        sync.setBounds(825, 350, syncSize.width, syncSize.height);
        sync.addActionListener(e -> {
            try {
                String allItemsString = httpUtils.getAllUserTodosJsonString();
                TodoItem[] allItems = parser.JsonStringToObjectArray(allItemsString);
                todoManager.updateOfflineTable(allItems);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(this, "ERROR: Couldn't sync data");
            }
        });
        

        panel.add(sync);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}