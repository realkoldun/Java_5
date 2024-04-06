package windows.mains;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import data.Data;

public class LoginWindow extends JFrame {

    public LoginWindow(final FirstWindow fw, Data db) {
        super("Pocket RW: login");
        this.setLayout(null);
        this.setBounds(100, 50, 500, 500);
        this.setResizable(false);
        final JTextField loginField = new JTextField("login");
        final JTextField passField = new JTextField("password");
        JButton logButton = new JButton("Login");
        loginField.setBounds(100, 150, 300, 25);
        passField.setBounds(100, 180, 300, 25);
        logButton.setBounds(200, 215, 100, 25);
        logButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                userLogin(loginField, passField, fw, db);
            }
        });
        this.add(passField);
        this.add(loginField);
        this.add(logButton);
    }

    public void userLogin(JTextField f1, JTextField f2, FirstWindow fw, Data db) {
        ArrayList<String[]> bufStr = new ArrayList<>();
        try {
            File file = new File("C:\\Users\\Антон\\Desktop\\ЛФБЫ\\джава\\lab5v2\\src\\main\\java\\usersData.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] bufData = line.split(" ");
                bufStr.add(bufData);
            }
            scanner.close();
            for (String[] strings : bufStr)
                if (f1.getText().equals(strings[0]))
                    if (f2.getText().equals(strings[1])) {
                        switch (strings[2]) {
                            case "director":
                                fw.setVisible(false);
                                fw.dispose();
                                this.setVisible(false);
                                this.dispose();
                                StationDirectorWindow sdWindow = new StationDirectorWindow(db, fw);
                                sdWindow.setVisible(true);
                                break;
                            case "admin":
                                fw.setVisible(false);
                                fw.dispose();
                                this.setVisible(false);
                                this.dispose();
                                AdminWindow aWindow = new AdminWindow(fw);
                                aWindow.setVisible(true);
                                break;
                            case "user":
                                fw.setVisible(false);
                                fw.dispose();
                                this.setVisible(false);
                                this.dispose();
                                UserWindow uWindow = new UserWindow(db, fw);
                                uWindow.setVisible(true);
                                break;
                        }
                    }
        } catch (Exception var11) {
            System.out.println("Произошла какая-то фигня - " + var11.getMessage());
        }
    }
}