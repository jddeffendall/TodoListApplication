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

public class todoUI extends JFrame {


    public todoUI() throws IOException {
        super("Day Planner");
        UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);


        HTTPUtils httpUtils = new HTTPUtils();
        UIUtils uiUtils = new UIUtils();
        JsonToObjectParser parser = new JsonToObjectParser();

        String allUserTodosJson = httpUtils.getAllUserTodosJsonString();
        TodoItem[] allUserTodos = parser.JsonStringToObjects(allUserTodosJson);

        String[][] data = uiUtils.formatDataForTable(allUserTodos);
        String[] columnNames = { "Created", "Description", "Due", "ID"};

        JTable items = new JTable(data, columnNames);
        var itemsConstraints = new GridBagConstraints(0,0,3,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH, new Insets(1,1,1,1),0,0);
        panel.add(items, itemsConstraints);

        JButton sync = new JButton("Sync");
        var syncConstraints = new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(sync, syncConstraints);
        sync.addActionListener(e->{
        });

        JButton Cancel = new JButton("Cancel Event");
        var CancelConstraints = new GridBagConstraints(2, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(Cancel, CancelConstraints);
        Cancel.addActionListener(e->{







        });


        JTextField titleInput = new JTextField("Enter title of Todo item");
        var titleInputConstraints = new GridBagConstraints(0,1,1,1,1,1,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0,0);
        panel.add(titleInput, titleInputConstraints);

        JTextField creationDateInput = new JTextField("Enter current date");
        var creationDateInputConstraints = new GridBagConstraints(1,1,1,1,1,1,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0,0);
        panel.add(creationDateInput, creationDateInputConstraints);

        JTextField dueDateInput = new JTextField("Enter due date for item");
        var dueDateInputConstraints = new GridBagConstraints(2,1,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0, 0);
        panel.add(dueDateInput, dueDateInputConstraints);


        JButton AddEvent = new JButton("Add To Schedule");
        var AddEventConstraints = new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
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
        panel.add(AddEvent, AddEventConstraints);


        setPreferredSize(new Dimension(800,1200));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new todoUI();
    }
}
