package windows.mains;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import data.Data;
import windows.additional.AddWindow;

public class StationDirectorWindow extends JFrame implements ActionListener {
    AddWindow aWindow;
    JTable trainTable;
    JScrollPane scrollPane;
    JCheckBox deleteButton;
    Data db;
    public StationDirectorWindow(Data db, FirstWindow fWindow) {
        super("Pocket RW (admin mode)");
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(100, 50, 1200, 500);
        JButton exitButton = new JButton("Exit");
        JButton saveButton = new JButton("Save");
        deleteButton = new JCheckBox("Delete train", false);
        JButton addButton = new JButton("Add");
        trainTable =db.trainTable;
        this.db = db;

        db.isChangeable = true;

        exitButton.addActionListener((arg0) -> {
            try {
                db.update_trains();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            dispose();
            fWindow.setVisible(true);
        });


        deleteButton.addActionListener(this);

        saveButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                db.updateTable();
            }
        });


        addButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                DefaultTableModel model = (DefaultTableModel) db.trainTable.getModel();
                model.insertRow(db.trainTable.getRowCount(), new Object[] {});

            }
        });
        JButton dButton = new JButton("delete");
        dButton.addActionListener((arg0) -> {
            db.delete_train(db.trainTable.getSelectedRow());
            DefaultTableModel model = (DefaultTableModel) db.trainTable.getModel();
            model.removeRow(db.trainTable.getSelectedRow());
        });
        this.scrollPane = new JScrollPane(this.trainTable);
        this.scrollPane.setBounds(120, 30, 1000, 500);
        exitButton.setBounds(this.getX() - 80, 10, 80, 25);
        saveButton.setBounds(this.getX() - 80, 45, 80, 25);
        addButton.setBounds(this.getX() - 80, 80, 80, 25);
        dButton.setBounds(this.getX() - 80, 115, 80, 25);

        this.add(exitButton);
        this.add(saveButton);
        this.add(addButton);
        this.add(dButton);
        this.add(scrollPane);
        //add(updateButton);
        this.validate();
        this.repaint();
        this.validate();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            boolean selected = deleteButton.isSelected();

            db.deleteMode = selected;
            if(selected) {
                JOptionPane.showMessageDialog(null, "Внимание!", "Включен режим удаления", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

}
