package UI;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class instructionsUI extends JFrame {
    static JLabel descriptionlabel;
    static JButton description;


    public instructionsUI() throws IOException {
        super("Instructions and Credits");

        UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 25)));

        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(600, 400));
        super.setResizable(false);

        descriptionlabel = new JLabel("");
        description = new JButton("Credits");


        JLabel instructions = new JLabel();
        instructions.setText("<HTML><Left><ul><li>In order to add an event to your schedule, you must input the name of the event and the due date of the event.</li><li>When the event is added, an ID will be automatically assigned.</li><li>This ID can be used to delete the event, mark it as complete, or snooze the due date by 15 minutes.</li><li>If you press the pie chart button, a chart displays.</li><li>If you press the Sync for offline button, it will back up the database and allow you to continue your day planning.</li><li>If you press the sync for online button it will push your offline events to the cloud and allow you to continue your day planning online.</li></ul></Left><HTML>");
        instructions.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        instructions.setPreferredSize(new Dimension(500, 350));
        Dimension instructionsSize = instructions.getPreferredSize();
        instructions.setBounds(0, 0, instructionsSize.width, instructionsSize.height + 15);
        instructions.setVisible(false);
        panel.add(instructions);

        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.setMargin(new Insets(2, 1, 2, 1));
        instructionsButton.setPreferredSize(new Dimension(100, 50));
        instructionsButton.setFocusPainted(false);
        instructionsButton.setFont(new Font("Serif", Font.PLAIN, 15));
        Dimension instructionsSize1 = instructionsButton.getPreferredSize();
        instructionsButton.setBounds(0, 0, instructionsSize1.width, instructionsSize1.height);
        panel.add(instructionsButton);
        instructionsButton.addActionListener(e -> {
            instructionsButton.setVisible(false);
            instructions.setVisible(true);
            descriptionlabel.setVisible(false);
            description.setVisible(false);

        });


        // JLabel descriptionLabel = new JLabel();
        descriptionlabel.setText("<HTML><Left>Created By: Jacob Deffendall, Payton Lowry, Ross Effinger, Haden Foster</Left><HTML>");
        descriptionlabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        descriptionlabel.setPreferredSize(new Dimension(500, 50));
        Dimension descriptionLabelSize = descriptionlabel.getPreferredSize();
        descriptionlabel.setBounds(0, 0, descriptionLabelSize.width, descriptionLabelSize.height + 15);
        descriptionlabel.setVisible(false);
        panel.add(descriptionlabel);


        //JButton description = new JButton("Description");
        description.setMargin(new Insets(2, 1, 2, 1));
        description.setPreferredSize(new Dimension(100, 50));
        description.setFocusPainted(false);
        description.setFont(new Font("Serif", Font.PLAIN, 15));
        Dimension descriptionSize = description.getPreferredSize();
        description.setBounds(100, 0, descriptionSize.width, descriptionSize.height);
        panel.add(description);
        description.addActionListener(e -> {
            descriptionlabel.setVisible(true);
            description.setVisible(false);
            instructions.setVisible(false);
            instructionsButton.setVisible(false);
        });

        JButton back = new JButton("Back");
        back.setMargin(new Insets(2, 1, 2, 1));
        back.setPreferredSize(new Dimension(100, 50));
        back.setFocusPainted(false);
        back.setFont(new Font("Serif", Font.PLAIN, 15));
        Dimension backSize = back.getPreferredSize();
        back.setBounds(500, 0, backSize.width, backSize.height);
        panel.add(back);
        back.addActionListener(e -> {
            instructions.setVisible(false);
            instructionsButton.setVisible(true);
            description.setVisible(true);
            descriptionlabel.setVisible(false);
        });


        JButton backMenu = new JButton("Back to menu");
        backMenu.setMargin(new Insets(2, 1, 2, 1));
        backMenu.setPreferredSize(new Dimension(100, 50));
        backMenu.setFocusPainted(false);
        backMenu.setFont(new Font("Serif", Font.PLAIN, 15));
        Dimension backMenuSize = back.getPreferredSize();
        backMenu.setBounds(500, 350, backMenuSize.width, backMenuSize.height);
        panel.add(backMenu);
        backMenu.addActionListener(e -> {
            try {
                new MenuUI();
                setVisible(false);
                dispose();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "ERROR: Couldn't go back to menu!");
            }
        });


        setVisible(true);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);


    }
}
