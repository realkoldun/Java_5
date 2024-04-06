package windows.additional;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import data.Data;

public class AddWindow extends JFrame {
    final private JTextField trainField;
    final private JTextField vanField;

    public AddWindow(Data db) {
        super("Window Example");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));


        trainField = new JTextField();
        vanField = new JTextField();
        panel.add(trainField);
        panel.add(vanField);

        // Кнопка
        JButton button = new JButton("Click me");
        panel.add(button);

        add(panel);

        ActionListener listener = e -> {
            boolean key = false;
            String trainText = trainField.getText();
            if (!trainText.isEmpty()) {
                String[] bufT = trainText.split(" ");
                db.addTrain(bufT);
                key = true;
            }
            String vanText = vanField.getText();
            if (!vanText.isEmpty()) {
                String[] bufV1 = vanText.split(";");
                for (String i : bufV1) db.addVan(i);
                key = true;
            }
            if (key) {
                db.updateTable();
                db.resetTable();
            }
        };
        button.addActionListener(listener);
    }

    public AddWindow(ArrayList<String[]> bufData, String filename) {
        super("Window Example");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));


        trainField = new JTextField();
        vanField = new JTextField();
        panel.add(trainField);

        JButton button = new JButton("Click me");
        panel.add(button);

        add(panel);

        button.addActionListener((arg0) -> {
            String trainText = trainField.getText();
            if (!trainText.isEmpty()) {
                String[] bufUser = trainText.split(" ");
                bufData.add(bufUser);
                System.out.print("saving info");
                File saveUsersData = new File(filename);
                if (saveUsersData.exists()) {
                    try {
                        FileOutputStream outputStream = new FileOutputStream(saveUsersData);
                        StringBuilder buf = new StringBuilder();
                        for (String[] bufDatum : bufData) {
                            for (int j = 0; j < 3; j++) {
                                buf.append(bufDatum[j]);
                                buf.append(' ');
                            }
                            buf.append('\n');
                        }
                        outputStream.write(buf.toString().getBytes());
                        outputStream.close();
                        System.out.println("Users load to file successful");
                    } catch (FileNotFoundException var5) {
                        System.out.println("Произошла какая-то фигня - " + var5.getMessage());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
}

