import domain.TodoItem;
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

public class todoUI extends JFrame implements ActionListener {


    public todoUI() throws IOException {


        super("Todo List Application");
        UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 25)));

        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(1050, 550));


        HTTPUtils httpUtils = new HTTPUtils();
        UIUtils uiUtils = new UIUtils();
        JsonToObjectParser parser = new JsonToObjectParser();

        String allUserTodosJson = httpUtils.getAllUserTodosJsonString();
        TodoItem[] allUserTodos = parser.JsonStringToObjectArray(allUserTodosJson);

        String[][] data = uiUtils.formatDataForTable(allUserTodos);
        String[] columnNames = {"ID", "Title", "Created", "Due", "Completed", "Overdue"};

        JTable items = new JTable(data, columnNames);

        JScrollPane jScrollPane = new JScrollPane(items, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setBounds(0, 0, 600, 800);
        panel.add(jScrollPane);




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
        AddEvent.setBounds(800, 0, size.width, size.height);
        AddEvent.addActionListener(e -> {
            String title = titleInput.getText();
            String due = dueDateInput.getText();
            try {
                String resultJson = httpUtils.addTodoItem(title, due);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
        completeEvent.setBounds(600,250, completeEventSize.width,completeEventSize.height);
        panel.add(completeEvent);
        completeEvent.addActionListener(e ->{
            String idToComplete = completeEventById.getText();
            try {
                String itemJson = httpUtils.getTodoItemJsonString(idToComplete);
                TodoItem item = parser.JsonStringToOneObject(itemJson);
                boolean completionStatus = httpUtils.completeTodoItem(item);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });
        JButton snooze = new JButton("<HTML><center>Snooze</center><HTML>"); // centers the text HTML tags necessary for center tags to work
        snooze.setPreferredSize(new Dimension(150,100));
        Dimension snoozeSize = snooze.getPreferredSize();
        snooze.setBounds(900, 250, snoozeSize.width, snoozeSize.height);
        panel.add(snooze);
        snooze.addActionListener(e -> {
            String idToSnooze = completeEventById.getText();
            try {
                String todoItemToSnoozeJson = httpUtils.getTodoItemJsonString(idToSnooze);
                TodoItem itemToSnooze = parser.JsonStringToOneObject(todoItemToSnoozeJson);
                boolean snoozed = httpUtils.snooze(itemToSnooze);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        JButton Cancel = new JButton("<HTML><center>Delete</center><HTML>");
        Cancel.setPreferredSize(new Dimension(150, 100));
        Dimension CancelSize = Cancel.getPreferredSize();
        Cancel.setBounds(750, 250, CancelSize.width, CancelSize.height);
        panel.add(Cancel);
        Cancel.addActionListener(e -> {
            String stringId = completeEventById.getText();
            try {
                httpUtils.deleteTodoItem(stringId);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });



        JButton pieChart = new JButton("PieChart");
        pieChart.setPreferredSize(new Dimension(225, 100));
        Dimension pieChartSize = pieChart.getPreferredSize();
        pieChart.setBounds(600, 350, pieChartSize.width, pieChartSize.height);
        panel.add(pieChart);
        pieChart.addActionListener(e -> {
            new chartUI("Todo List Overview");
        });
        


        JButton refresh = new JButton("Refresh");
        refresh.setPreferredSize(new Dimension(225, 100));
        Dimension refreshSize = refresh.getPreferredSize();
        refresh.setBounds(825, 350, refreshSize.width, refreshSize.height);
        panel.add(refresh);
        refresh.addActionListener(e -> {
            try {
                setVisible(false); //you can't see me!
                dispose();
                new todoUI();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        JButton sync = new JButton("Sync For Offline");
        sync.setPreferredSize(new Dimension(450,100));
        Dimension syncSize = sync.getPreferredSize();
        sync.setBounds(600, 450, syncSize.width, syncSize.height);
        sync.addActionListener(e -> {

        });
        panel.add(sync);
        
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
                try {
                    String todoItemsJson = httpUtils.getAllUserTodosJsonString();
                    TodoItem[] items = parser.JsonStringToObjectArray(todoItemsJson);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm");
                    LocalDateTime now = LocalDateTime.now();

                    for (TodoItem e : items) {
                        LocalDateTime dueDate = LocalDateTime.parse(e.getDueDate(), formatter);

                        if (now.isAfter(dueDate) && e.getCompleted().equals("false")) {
                            e.setOverdue();
                            httpUtils.setTodoItemOverdue(e);
                            JOptionPane.showMessageDialog(null, "Todo item with ID " + e.getId() + " is overdue");
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t = new Thread(r);
        t.start();

        new todoUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}