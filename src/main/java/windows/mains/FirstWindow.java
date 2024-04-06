package windows.mains;

import data.Data;
import javax.swing.*;

public class FirstWindow extends JFrame {
    private LoginWindow lWindow;
    public FirstWindow(Data db) {
        super("Pocket RW");
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(100, 50, 500, 500);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener((arg0) -> {
            this.lWindow = new LoginWindow(this, db);
            lWindow.setVisible(true);
        });
        loginButton.setBounds(this.getX() - 80, 10, 80, 25);
        this.add(loginButton);
        this.validate();
        this.repaint();
        this.validate();
    }
}