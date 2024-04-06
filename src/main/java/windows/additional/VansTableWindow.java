package windows.additional;

import data.Data;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class VansTableWindow extends JFrame{
    JCheckBox deleteButton;
    public VansTableWindow(Data db) {
        super("Vans table");
        this.setLayout(null);
        this.setBounds(100, 50, 1200, 500);
        JScrollPane scrollPane = new JScrollPane(db.vansTable);

        JButton addUser = new JButton("Add user");
        addUser.addActionListener((arg0) -> {
            DefaultTableModel model = (DefaultTableModel) db.vansTable.getModel();
            model.insertRow(db.vansTable.getRowCount(), new Object[] {});
        });
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener((arg0) -> {
            try {
                db.update_vans();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            dispose();
        });
        JButton dButton = new JButton("delete");
        dButton.addActionListener((arg0) -> {
            db.vansData.remove(db.vansTable.getSelectedRow());
            DefaultTableModel model = (DefaultTableModel) db.vansTable.getModel();
            model.removeRow(db.vansTable.getSelectedRow());
        });

        scrollPane.setBounds(120, 30, 1000, 500);
        addUser.setBounds(this.getX() - 80, 10, 80, 25);
        exitButton.setBounds(this.getX() - 80, 40, 80, 25);
        dButton.setBounds(this.getX() - 80, 70, 80, 25);
        this.add(scrollPane);
        add(addUser);
        add(exitButton);
        add(dButton);
        this.validate();
        this.repaint();
        this.validate();
    }
}
