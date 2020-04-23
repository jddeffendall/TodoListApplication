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

public class offlineUI extends JFrame {

    public offlineUI() throws IOException {
        super("Offline Todo List");

        UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 25)));

        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(1050, 550));
        super.setResizable(false);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm");
        var todoManager = new DatabaseUtils();
        UIUtils uiUtils = new UIUtils();
        JsonToObjectParser parser = new JsonToObjectParser();

        List<TodoItem> allUserTodos = todoManager.getAllItems();
        TodoItem[] allUserTodosArray = new TodoItem[allUserTodos.size()];

        for (int i=0; i<allUserTodos.size(); i++) {
            allUserTodosArray[i] = allUserTodos.get(i);
        }

        String[][] data = uiUtils.formatDataForTable(allUserTodosArray);
        String[] columnNames = {"ID", "Title", "Created", "Due", "Completed", "Overdue", "Completed Date"};

        JTable items = new JTable(data, columnNames);

        JScrollPane jScrollPane = new JScrollPane(items, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setBounds(0, 0, 600, 800);
        panel.add(jScrollPane);
        items.getColumnModel().getColumn(0).setPreferredWidth(2);


        JLabel titlee = new JLabel("Enter Title of Item:");
        titlee.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        titlee.setHorizontalAlignment(SwingConstants.LEFT);
        titlee.setVerticalAlignment(SwingConstants.CENTER);
        titlee.setBounds(600, 0, 200, 50);
        panel.add(titlee);

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
            String due = dueDateInput.getText();
            String title = titleInput.getText();
            dueDateInput.setText(null);
            titleInput.setText(null);

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

        });

        panel.add(AddEvent);

        JLabel completeEventLabel = new JLabel("<HTML>Enter ID of item for one of these actions: <HTML>");
        completeEventLabel.setHorizontalAlignment(SwingConstants.LEFT);
        completeEventLabel.setVerticalAlignment(SwingConstants.CENTER);
        completeEventLabel.setBounds(600,200,200,50);
        completeEventLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        panel.add(completeEventLabel);

        JTextField completeEventById = new JTextField("");
        completeEventById.setBounds(800,200,250,50);
        completeEventById.setBackground(Color.lightGray);
        panel.add(completeEventById);




        JButton completeEvent = new JButton("<HTML><center>Complete</center><HTML>"); //html tags wrap the text and centers it
        completeEvent.setPreferredSize(new Dimension(150,100));
        Dimension completeEventSize = completeEvent.getPreferredSize();
        completeEvent.setFocusPainted(false);
        completeEvent.setBounds(600,250, completeEventSize.width,completeEventSize.height);
        panel.add(completeEvent);
        completeEvent.addActionListener(e ->{
            String idToComplete = completeEventById.getText();
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
        });

        JButton snooze = new JButton("<HTML><center>Snooze</center><HTML>"); // centers the text HTML tags necessary for center tags to work
        snooze.setPreferredSize(new Dimension(150,100));
        Dimension snoozeSize = snooze.getPreferredSize();
        snooze.setFocusPainted(false);
        snooze.setBounds(900, 250, snoozeSize.width, snoozeSize.height);
        panel.add(snooze);
        snooze.addActionListener(e -> {
            String idToSnooze = completeEventById.getText();
            todoManager.snooze(idToSnooze);
            completeEventById.setText(null);

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
        });

        JButton Cancel = new JButton("<HTML><center>Delete</center><HTML>");
        Cancel.setPreferredSize(new Dimension(150, 100));
        Dimension CancelSize = Cancel.getPreferredSize();
        Cancel.setFocusPainted(false);
        Cancel.setBounds(750, 250, CancelSize.width, CancelSize.height);
        panel.add(Cancel);
        Cancel.addActionListener(e -> {
            String idToDelete = completeEventById.getText();
            completeEventById.setText(null);
            todoManager.deleteItem(idToDelete);

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
        });



        JButton pieChart = new JButton("PieChart");
        pieChart.setPreferredSize(new Dimension(225, 100));
        Dimension pieChartSize = pieChart.getPreferredSize();
        pieChart.setFocusPainted(false);
        pieChart.setBounds(600, 350, pieChartSize.width, pieChartSize.height);
        panel.add(pieChart);
        pieChart.addActionListener(e -> {
            new offlineChartUI("Offline Todo List Overview");
        });



        JButton backtoMenu = new JButton("Back to Main Menu");
        backtoMenu.setPreferredSize(new Dimension(450, 100));
        Dimension refreshSize = backtoMenu.getPreferredSize();
        backtoMenu.setFocusPainted(false);
        backtoMenu.setBounds(600, 450, refreshSize.width, refreshSize.height);
        panel.add(backtoMenu);
        backtoMenu.addActionListener(e -> {
            try {
                setVisible(false); //you can't see me!
                dispose();
                new MenuUI();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "ERROR: Couldn't go back to menu!");
            }
        });


        JButton sync = new JButton("<HTML><center>Go back online!</center><HTML>");
        sync.setPreferredSize(new Dimension(225,100));
        Dimension syncSize = sync.getPreferredSize();
        sync.setBounds(825, 350, syncSize.width, syncSize.height);
        sync.setFocusPainted(false);
        panel.add(sync);
        sync.addActionListener(e -> {
            try {

                HTTPUtils httpUtils = new HTTPUtils();
                String allJson = httpUtils.getAllUserTodosJsonString();
                TodoItem[] updatedItems = parser.JsonStringToObjectArray(allJson);

                for (TodoItem i : updatedItems) {
                    httpUtils.deleteTodoItem(i.getId());
                }

                List<TodoItem> offlineItems = todoManager.getAllItems();

                for (TodoItem i : offlineItems) {
                    httpUtils.addTodoItem(i.getTitle(), i.getDueDate(), i.getCreatedDate(), i.getCompleted(), i.getOverdue(), i.getCompletedDate(), i.getOwner());
                }

                setVisible(false);
                dispose();
                new todoUI();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        todoManager.disposeResources();
    }
}
