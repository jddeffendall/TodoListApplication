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

public class todoUI extends JFrame{


    public todoUI() throws IOException {


        super("Todo Application");
        UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 25)));

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);


        HTTPUtils httpUtils = new HTTPUtils();
        UIUtils uiUtils = new UIUtils();
        JsonToObjectParser parser = new JsonToObjectParser();

        String allUserTodosJson = httpUtils.getAllUserTodosJsonString();
        TodoItem[] allUserTodos = parser.JsonStringToObjectArray(allUserTodosJson);

        String[][] data = uiUtils.formatDataForTable(allUserTodos);
        String[] columnNames = { "Created", "Description", "Due", "Completed", "ID"};

        JTable items = new JTable(data, columnNames);
        var itemsConstraints = new GridBagConstraints(0,0,4,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(1,1,1,1),0,0);
        panel.add(items, itemsConstraints);

/*
        JButton sync = new JButton("Sync");
        var syncConstraints = new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(sync, syncConstraints);
        sync.addActionListener(e->{
        });
*/

        JButton refresh = new JButton("Refresh");
        var refreshConstraints = new GridBagConstraints(6, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(refresh, refreshConstraints);
        refresh.addActionListener(e->{
            try {
                new todoUI();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });





        JLabel titlee = new JLabel("Enter Title of Item:");
        var titleeConstraints = new GridBagConstraints(0, 4, 1, 1, 1, 1,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(titlee, titleeConstraints);

        JTextField titleInput = new JTextField("");
        var titleInputConstraints = new GridBagConstraints(1,4,1,1,1,1,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0,0);
        panel.add(titleInput, titleInputConstraints);



        JLabel dueDateLabel = new JLabel("Enter Due Date For Item:");
        var dueDateLabelConstraints = new GridBagConstraints(2,4,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0, 0);
        panel.add(dueDateLabel, dueDateLabelConstraints);

        JTextField dueDateInput = new JTextField("");
        var dueDateInputConstraints = new GridBagConstraints(3,4,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0, 0);
        panel.add(dueDateInput, dueDateInputConstraints);



        JLabel deleteItemLabel = new JLabel("Enter Id of Item to Delete:");
        var deleteItemLabelConstraints = new GridBagConstraints(2,3,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0,0);
        panel.add(deleteItemLabel, deleteItemLabelConstraints);

        JTextField deleteItemByIdinput = new JTextField("");
        var deleteItemByIdConstraints = new GridBagConstraints(3,3,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0,0);
        panel.add(deleteItemByIdinput, deleteItemByIdConstraints);




        JButton AddEvent = new JButton("Add To Schedule");
        var AddEventConstraints = new GridBagConstraints(6, 4, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        AddEvent.addActionListener(e ->{
            String title = titleInput.getText();
            String due = dueDateInput.getText();
            try {
                String resultJson = httpUtils.addTodoItem(title, due);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        panel.add(AddEvent, AddEventConstraints);

        JButton Cancel = new JButton("Cancel Event");
        var CancelConstraints = new GridBagConstraints(6, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(Cancel, CancelConstraints);
        Cancel.addActionListener(e->{
            String stringId = deleteItemByIdinput.getText();
            int id = Integer.parseInt(stringId);
            try {
                httpUtils.deleteTodoItem(id);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });





        JLabel completeEventLabel = new JLabel("Enter ID of item to set as complete");
        var completeEventLabelConstraints = new GridBagConstraints(2,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0,0);
        panel.add(completeEventLabel, completeEventLabelConstraints);

        JTextField completeEventById = new JTextField("");
        var completeEventByIdConstraints = new GridBagConstraints(3,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0,0);
        panel.add(completeEventById, completeEventByIdConstraints);







        JButton completeEvent = new JButton("Complete Event");
        var completeEventConstraints = new GridBagConstraints(6,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0,0);
        panel.add(completeEvent, completeEventConstraints);
        completeEvent.addActionListener(e ->{
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

        setPreferredSize(new Dimension(1200,1200));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new todoUI();
    }
}
