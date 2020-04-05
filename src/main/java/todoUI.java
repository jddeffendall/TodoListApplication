import utils.HTTPUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class todoUI extends JFrame implements ActionListener {


    public todoUI(){
        super("Todo Application");
        UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);

        JButton retrieveAllTodos = new JButton("Get all Todos");
        var retrieveAllTodosConstraints = new GridBagConstraints(0, 0, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(retrieveAllTodos, retrieveAllTodosConstraints);
        retrieveAllTodos.addActionListener(e->{

        });

        JButton sync = new JButton("Sync");
        var syncConstraints = new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(sync, syncConstraints);
        sync.addActionListener(e->{
                });

        JButton snooze = new JButton("Snooze");
        var snoozeConstraints = new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(snooze,snoozeConstraints);
        snooze.addActionListener(e->{


        });






        setPreferredSize(new Dimension(500,500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        /*
        Button to sync
        Button to retrieve all incomplete to do items
        Text boxes to input item description and due date
        Button to snooze to do item
        When item is clicked on, you can complete it and send it to completed area
         */
    }

    public static void main(String[] args) {
        new todoUI();
    }

    public void actionPerformed(ActionEvent e) {

    }
}
