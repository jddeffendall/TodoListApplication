package UI;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class instructionsUI extends JFrame implements ActionListener {


    public instructionsUI() throws IOException {
        super("Instructions and description");

        UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 25)));

        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(400, 200));


        JLabel instructions = new JLabel();
        instructions.setText("<HTML><Left>Some type of text is going to go here, testing this to see of the wrapping text and left align works</Left><HTML>");
        instructions.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        instructions.setBounds(0, 0, 400, 50);
        panel.add(instructions);

        JButton back = new JButton("Back to menu");
        back.setMargin(new Insets(2, 1, 2, 1));
        back.setPreferredSize(new Dimension(100, 50));
        back.setFocusPainted(false);
        back.setFont(new Font("Serif", Font.PLAIN, 15));
        Dimension backSize = back.getPreferredSize();
        back.setBounds(300, 150, backSize.width, backSize.height);
        panel.add(back);
        back.addActionListener(e -> {
            try {
                new todoUI();
                setVisible(false);
                dispose();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        setVisible(true);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);


    }

    public static void main(String[] args) throws IOException {
        new instructionsUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
