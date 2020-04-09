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
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);

        /*JButton retrieveAllTodos = new JButton("Get all Todos");
        var retrieveAllTodosConstraints = new GridBagConstraints(0, 0, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(retrieveAllTodos, retrieveAllTodosConstraints);
        retrieveAllTodos.addActionListener(e->{
            HTTPUtils httpUtils = new HTTPUtils();
            JsonToObjectParser parser = new JsonToObjectParser();
            try {
                String allUserTodosJsonString = httpUtils.getAllUserTodosJsonString();
                List<TodoItem> allUserTodosObjects = parser.JsonStringToObjects(allUserTodosJsonString);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });*/

        HTTPUtils httpUtils = new HTTPUtils();
        UIUtils uiUtils = new UIUtils();
        JsonToObjectParser parser = new JsonToObjectParser();

        String allUserTodosJson = httpUtils.getAllUserTodosJsonString();
        TodoItem[] allUserTodos = parser.JsonStringToObjects(allUserTodosJson);
        String[][] data = uiUtils.formatDataForTable(allUserTodos);

        //String[][] data = {{"12/20/2020 2:34 AM", "Kill Santa", "12/25/2020 5:30 AM"}};

        String[] columnNames = { "Created", "Description", "Due"};

        JTable items = new JTable(data, columnNames);
        var itemsConstraints = new GridBagConstraints(0,0,0,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(1,1,1,1),0,0);
        panel.add(items, itemsConstraints);

        JButton sync = new JButton("Sync");
        var syncConstraints = new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(sync, syncConstraints);
        sync.addActionListener(e->{

        });


/*


        JButton Cancel = new JButton("Cancel Event");
        var CancelConstraints = new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(Cancel, CancelConstraints);
        Cancel.addActionListener(e->{

        });
*/

/*
        JButton snooze = new JButton("Snooze");
        var snoozeConstraints = new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(snooze,snoozeConstraints);
        snooze.addActionListener(e->{

        });
*/

        JTextField titleInput = new JTextField("Enter title of Todo item");
        JTextField creationDateInput = new JTextField("Enter current date");
        JTextField dueDateInput = new JTextField("Enter due date for item");

        JButton AddEvent = new JButton("Add To Schedule");
        var AddEventConstraints = new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(AddEvent, AddEventConstraints);
        AddEvent.addActionListener(e ->{
            String title = titleInput.getText();
            String creation = creationDateInput.getText();
            String due = dueDateInput.getText();
            try {
                int resultID = httpUtils.addTodoItem(title, due, creation);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });




        setPreferredSize(new Dimension(800,800));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        /*
        Text boxes to input item description and due date
        When item is clicked on, you can complete it and send it to completed area
         */
    }

    public static void main(String[] args) throws IOException {
        new todoUI();
    }

    public void actionPerformed(ActionEvent e) {

    }
}
