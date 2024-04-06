package windows.mains;

import javax.swing.*;
import data.Data;
import windows.mains.FirstWindow;

public class UserWindow extends JFrame {
    public UserWindow(Data db, FirstWindow fWindow) {
        super("Pocket RW");
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(100, 50, 1200, 500);



        db.isChangeable = false;

        JTable trainTable = db.trainTable;

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener((arg0) -> {
            dispose();
            fWindow.setVisible(true);
        });
        JScrollPane scrollPane = new JScrollPane(trainTable);
        scrollPane.setBounds(120, 30, 1000, 500);
        exitButton.setBounds(this.getX() - 80, 10, 80, 25);
        this.add(exitButton);
        this.add(scrollPane);
        this.validate();
        this.repaint();
        this.validate();
    }

}