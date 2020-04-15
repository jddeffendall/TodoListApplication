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
import java.util.List;

public class todoUI extends JFrame implements ActionListener {


    public todoUI() throws IOException {


        super("Todo Application");
        UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 25)));

        JPanel panel = new JPanel();
        //GridBagLayout gridBagLayout = new GridBagLayout();
        //panel.setLayout(gridBagLayout);
        setContentPane(panel);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(1050,1050));


        HTTPUtils httpUtils = new HTTPUtils();
        UIUtils uiUtils = new UIUtils();
        JsonToObjectParser parser = new JsonToObjectParser();

        String allUserTodosJson = httpUtils.getAllUserTodosJsonString();
        TodoItem[] allUserTodos = parser.JsonStringToObjectArray(allUserTodosJson);

        String[][] data = uiUtils.formatDataForTable(allUserTodos);
        String[] columnNames = {"Created", "Description", "Due", "Completed", "ID"};

        JTable items = new JTable(data, columnNames);
        items.setBounds(0,0, 600, 800);
        //var itemsConstraints = new GridBagConstraints(0, 0, 4, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(items);


/*
        JButton sync = new JButton("Sync");
        var syncConstraints = new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(sync, syncConstraints);
        sync.addActionListener(e->{
        });
*/

        JLabel titlee = new JLabel("Enter Title of Item:");
        titlee.setFont(new Font("Serif", Font.PLAIN, 18));
        //titlee.setVerticalAlignment(SwingConstants.TOP);
        titlee.setBounds(600,0, 200, 50);
        panel.add(titlee);

        JTextField titleInput = new JTextField("");
        titleInput.setBounds(600,50,200,50);
        panel.add(titleInput);

        JLabel dueDateLabel = new JLabel("Enter Due Date For Item:");
        dueDateLabel.setBounds(600,100,200,50);
        dueDateLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        panel.add(dueDateLabel);

        JTextField dueDateInput = new JTextField("");
        dueDateInput.setBounds(800,100,250,50);
        panel.add(dueDateInput);

        JButton AddEvent = new JButton("Add To Schedule");
        AddEvent.setPreferredSize(new Dimension(250,100));
        Dimension size = AddEvent.getPreferredSize();
        AddEvent.setBounds(800,0, size.width, size.height);
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

        JLabel completeEventLabel = new JLabel("<HTML>Enter ID of item to set as complete:<HTML>");
        completeEventLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        completeEventLabel.setBounds(600,100,200,150);
        //var completeEventLabelConstraints = new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(completeEventLabel);

        JTextField completeEventById = new JTextField("");
        //var completeEventByIdConstraints = new GridBagConstraints(3, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        completeEventById.setBounds(600,200, 200, 50);
        panel.add(completeEventById);


        JButton completeEvent = new JButton("Complete Event");
        //var completeEventConstraints = new GridBagConstraints(6, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        completeEvent.setPreferredSize(new Dimension(250,100));
        Dimension completeEventSize = completeEvent.getPreferredSize();
        completeEvent.setBounds(800,150, completeEventSize.width, completeEventSize.height);
        panel.add(completeEvent);
        completeEvent.addActionListener(e -> {
            String idToComplete = completeEventById.getText();
            int id = Integer.parseInt(idToComplete);
            try {
                String itemJson = httpUtils.getTodoItemJsonString(id);
                TodoItem item = parser.JsonStringToOneObject(itemJson);
                boolean completionStatus = httpUtils.completeTodoItem(item);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });


        JLabel deleteItemLabel = new JLabel("Enter Id of Item to Delete:");
        deleteItemLabel.setBounds(600,250, 200,50);
        deleteItemLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        //var deleteItemLabelConstraints = new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(deleteItemLabel);

        JTextField deleteItemByIdinput = new JTextField("");
        deleteItemByIdinput.setBounds(600,300,200,50);
        //var deleteItemByIdConstraints = new GridBagConstraints(3, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(deleteItemByIdinput);

        JButton Cancel = new JButton("Delete Event");
        Cancel.setPreferredSize(new Dimension(250,100));
        Dimension CancelSize = Cancel.getPreferredSize();
        Cancel.setBounds(800,250, CancelSize.width, CancelSize.height);
        //var CancelConstraints = new GridBagConstraints(6, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(Cancel);
        Cancel.addActionListener(e -> {
            String stringId = deleteItemByIdinput.getText();
            int id = Integer.parseInt(stringId);
            try {
                httpUtils.deleteTodoItem(id);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        JLabel refreshLabel = new JLabel();
        refreshLabel.setText("<HTML>Added something? Click here to refresh it!<HTML>");
        refreshLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        refreshLabel.setBounds(600,350,200,100);
        panel.add(refreshLabel);

        JButton refresh = new JButton("Refresh");
        refresh.setPreferredSize(new Dimension(250,100));
        Dimension refreshSize = refresh.getPreferredSize();
        refresh.setBounds(800, 350,refreshSize.width, refreshSize.height);
        panel.add(refresh);
        refresh.addActionListener(e -> {
            try {
                new todoUI();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });



        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new todoUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
