package windows.mains;

import data.Data;
import windows.additional.AddWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AdminWindow extends JFrame /*implements ActionListener*/ {
    JTable usersTable;
    String filename = "C:\\Users\\Антон\\Desktop\\ЛФБЫ\\джава\\lab5v2\\src\\main\\java\\usersData.txt";
    String[] columnNames = {"Login", "Password", "Type of account"};
    ArrayList<String[]> bufData;
    String[][] users;
    boolean isChangeable = true;
    JCheckBox deleteButton;
    boolean deleteMode = false;


    String[][] loadData() {
        ArrayList<String[]> usersData = new ArrayList<>();
        try {
            Reader userReader = new FileReader(filename);
            BufferedReader buffReader = new BufferedReader(userReader);
            String line;
            while ((line = buffReader.readLine()) != null) {
                String[] buf = line.split(" ");
                usersData.add(buf);
            }
        } catch (Exception e) {
            System.out.println("Произошла какая-то фигня - " + e.getMessage());
        }
        bufData = usersData;
        String[][] buf = new String[usersData.size()][3];
        for(int i = 0; i < usersData.size(); i++)
            buf[i] = usersData.get(i);
        return buf;
    }
    void delete_user(int index) {
        bufData.remove(index);
        update_data();
    }
    void save_data() {
        DefaultTableModel model = (DefaultTableModel)usersTable.getModel();
        String[][] bufF = new String[model.getRowCount()][model.getColumnCount()];
        for(int i = 0; i < model.getRowCount(); i++)
            for(int j = 0; j < model.getColumnCount(); j++) bufF[i][j] = model.getValueAt(i, j).toString();
        bufData.clear();
        Collections.addAll(bufData, bufF);
    }
    void update_data() {
        save_data();
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

    public AdminWindow(FirstWindow fWindow) {
     super("Users list");
        this.setLayout(null);
        this.setBounds(100, 50, 1200, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        users = loadData();

        DefaultTableModel trainsModel = new DefaultTableModel(users, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return isChangeable;
            }
        };
        usersTable = new JTable(trainsModel);
        usersTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if(usersTable.columnAtPoint(evt.getPoint()) == 0 && deleteMode)
                    delete_user(usersTable.rowAtPoint(evt.getPoint()));
            }
        });

        JButton addUser = new JButton("Add user");
        addUser.addActionListener((arg0) -> {
            DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
            model.insertRow(usersTable.getRowCount(), new Object[] {});
        });
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener((arg0) -> {
            update_data();
            dispose();
            fWindow.setVisible(true);
        });

        RowSorter<TableModel> trainSorter = new TableRowSorter<>(trainsModel);
        usersTable.setRowSorter(trainSorter);
        JScrollPane scrollPane = new JScrollPane(usersTable);
        JButton dButton = new JButton("delete");
        dButton.addActionListener((arg0) -> {
            bufData.remove(usersTable.getSelectedRow());
            DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
            model.removeRow(usersTable.getSelectedRow());
        });
        scrollPane.setBounds(120, 30, 1000, 500);
        addUser.setBounds(this.getX() - 80, 10, 80, 25);
        exitButton.setBounds(this.getX() - 80, 40, 80, 25);
        dButton.setBounds(this.getX() - 80, 115, 80, 25);
        add(scrollPane);
        add(addUser);
        add(exitButton);
        add(dButton);
    }
}
