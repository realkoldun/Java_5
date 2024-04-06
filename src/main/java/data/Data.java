package data;

import windows.additional.VansTableWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Data {
    public final Integer trainDataCount = 10;
    public final Integer vanDataCount = 3;
    private ArrayList<ArrayList<String>> trainsData;
    Integer trainCount;
    public ArrayList<ArrayList<String>> vansData;
    Integer vansCount;
    public JTable trainTable;
    public JTable vansTable;
    public Boolean isChangeable;
    public Boolean deleteMode = false;
    String[][] columnNames = new String[][]{
            {"Number", "departure Station Name",
            "c Station Name", "departure date", "departure time", "destination date", "destination time",
            "van Count", "total Number Of Seats", "total Free NOS"},
            {"Number of train", "Number of van", "Type of vans"}
    };
    String trainsFilename;
    String vansFilename;

    private ArrayList<Integer> tempChangedRows;
    VansTableWindow vtWindow;
    boolean isVTWindowOpened;

    public Data() {
        trainsData = new ArrayList<>();
        vansData = new ArrayList<>();
        trainCount = 0;
        vansCount = 0;
    }

    public void loadFromFile(String trainsFilename, String vansFilename) {
        try {
            this.trainsFilename = trainsFilename;
            this.vansFilename = vansFilename;
            Reader trainReader = new FileReader(this.trainsFilename);
            BufferedReader buffReader = new BufferedReader(trainReader);
            String line;
            while ((line = buffReader.readLine()) != null) {
                String[] buf = line.split(" ");
                ArrayList<String> bufStr = new ArrayList<>(Arrays.asList(buf)); //круто!!!!!!!!!!
                trainsData.add(bufStr);
            }
            buffReader.close();
            trainReader.close();
            trainCount = trainsData.size();
            Reader vansReader = new FileReader(this.vansFilename);
            buffReader = new BufferedReader(vansReader);
            while ((line = buffReader.readLine()) != null) {
                String[] buf = line.split(" ");
                ArrayList<String> bufStr = new ArrayList<>();
                Collections.addAll(bufStr, buf);
                vansData.add(bufStr);
            }
            buffReader.close();
            vansReader.close();
            vansCount = vansData.size();
            createTable();
            System.out.println("data.Data loaded successfully");
        } catch (Exception e) {
            System.out.println("Произошла какая-то фигня - " + e.getMessage());
        }
    }
    public void resetTable() {
        try {
            Reader trainReader = new FileReader(this.trainsFilename);
            BufferedReader buffReader = new BufferedReader(trainReader);
            String line;
            while ((line = buffReader.readLine()) != null) {
                String[] buf = line.split(" ");
                ArrayList<String> bufStr = new ArrayList<>(Arrays.asList(buf)); //круто!!!!!!!!!!
                trainsData.add(bufStr);
            }
            buffReader.close();
            trainReader.close();
            trainCount = trainsData.size();
            Reader vansReader = new FileReader(this.vansFilename);
            buffReader = new BufferedReader(vansReader);
            while ((line = buffReader.readLine()) != null) {
                String[] buf = line.split(" ");
                ArrayList<String> bufStr = new ArrayList<>();
                Collections.addAll(bufStr, buf);
                vansData.add(bufStr);
            }
            buffReader.close();
            vansReader.close();
            vansCount = vansData.size();
            trainTable = null;
            createTable();
            System.out.println("data.Data loaded successfully");
        } catch (Exception e) {
            System.out.println("Произошла какая-то фигня - " + e.getMessage());
        }
    }
    public void loadInFile() throws IOException {
        System.out.print("saving info");
        File saveTrainsFile = new File(trainsFilename);
        File saveVansFile = new File(vansFilename);
        if (saveTrainsFile.exists()) {
            try {
                FileOutputStream outputStream = new FileOutputStream(saveTrainsFile);
                String[][] bufTrain = get_trainsData();
                StringBuilder buf = new StringBuilder();
                for (int i = 0; i < trainCount; ++i) {
                    for(int j = 0; j < trainDataCount; j++) {
                        buf.append(bufTrain[i][j]);
                        buf.append(' ');
                    }
                    buf.append('\n');
                }
                outputStream.write(buf.toString().getBytes());
                outputStream.close();
                System.out.println("Trains load to file successful");
                outputStream = new FileOutputStream(saveVansFile);
                String[][] bufVan = get_vansData();
                buf = new StringBuilder();
                for (int i = 0; i < vansCount; ++i) {
                    for(int j = 0; j < vanDataCount; j++) {
                        buf.append(bufVan[i][j]);
                        buf.append(' ');
                    }
                    buf.append('\n');
                }
                outputStream.write(buf.toString().getBytes());
                outputStream.close();
                System.out.println("Vans load to file successful");
            } catch (FileNotFoundException var5) {
                System.out.println("Произошла какая-то фигня - " + var5.getMessage());
            }
        }
    }
    public String[][] get_trainsData() {
        String[][] buf = new String[trainCount][trainDataCount];
        for(int i = 0; i < trainCount; i++)
            for(int j = 0; j < trainDataCount; j++)  buf[i][j] = trainsData.get(i).get(j);
        return buf;
    }
    public String[][] get_vansData() {
        String[][] buf = new String[vansCount][vanDataCount];
        for(int i = 0; i < vansCount; i++)
            for(int j = 0; j < vanDataCount; j++)  buf[i][j] = vansData.get(i).get(j);
        return buf;
    }
    public void addTrain(String[] newTrain) {
        ArrayList<String> temp = new ArrayList<>();
        trainsData.add(temp);
        trainCount = trainsData.size();
        temp.clear();
        for(String i : newTrain) trainsData.get(trainCount - 1).add(i);
        System.out.println("Train " + newTrain[0] + " was got");
    }
    public void addVan(String newVan) {
        String[] buf = newVan.split(" ");
        ArrayList<String> temp = new ArrayList<>();
        vansData.add(temp);
        vansCount = vansData.size();
        temp.clear();
        for(String i : buf) vansData.get(vansCount - 1).add(i);
        System.out.println("Van " + buf[0] + " was got");
    }
    public String[] get_Train(int index) {
        String[] buf = new String[trainDataCount * 2];
        for(int i = 0; i < trainDataCount; i++) buf[i] = trainsData.get(index).get(i) + " ";
        return buf;
    }
    public String[][] get_Van(Integer index) {
        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        int key = 0;
        temp.add(new ArrayList<>());
        for(int i = 0; i < vansCount; i++) {
            if(vansData.get(i).get(0).equals(index.toString())) {
                temp.get(key).add(vansData.get(i).get(0));
                temp.get(key).add(vansData.get(i).get(1));
                temp.get(key).add(vansData.get(i).get(2));
                key++;
                temp.add(new ArrayList<>());
            }
        }
        temp.remove(temp.size() - 1);
        String[][] buf = new String[temp.size()][vanDataCount];
        for(int i = 0; i < temp.size(); i++)
            for(int j = 0; j < vanDataCount; j++) buf[i][j] = temp.get(i).get(j);
        return buf;
    }
    public void print_trainData() {
        for(int i = 0; i < trainCount; i++) {
            for(int j = 0; j < trainDataCount; j++) System.out.print(get_trainsData()[i][j]);
            System.out.println();
        }
    }
    public void print_vansData() {
        for(int i = 0; i < vansCount; i++) {
            for(int j = 0; j < vanDataCount; j++) System.out.print(get_vansData()[i][j]);
            System.out.println();
        }
    }
    public void update_trains() throws IOException {
        DefaultTableModel model = (DefaultTableModel)trainTable.getModel();
        String[][] bufF = new String[model.getRowCount()][model.getColumnCount()];
        for(int i = 0; i < model.getRowCount(); i++)
            for(int j = 0; j < model.getColumnCount(); j++) bufF[i][j] = model.getValueAt(i, j).toString();
        trainsData.clear();
        trainsData = new ArrayList<>();
        for (String[] strings : bufF) {
            ArrayList<String> buf = new ArrayList<>(Arrays.asList(strings).subList(0, trainDataCount));
            trainsData.add(buf);
        }
        trainCount = trainsData.size();
        loadInFile();
    }
    public void update_vans() throws IOException {
        DefaultTableModel model = (DefaultTableModel)vansTable.getModel();
        String[][] bufF = new String[model.getRowCount()][model.getColumnCount()];
        for(int i = 0; i < model.getRowCount(); i++)
            for(int j = 0; j < model.getColumnCount(); j++) bufF[i][j] = model.getValueAt(i, j).toString();
        vansData = new ArrayList<>();
        for (String[] strings : bufF) {
            ArrayList<String> buf = new ArrayList<>(Arrays.asList(strings).subList(0, vanDataCount));
            vansData.add(buf);
        }
        vansCount = vansData.size();
        loadInFile();
    }
    public void train_change(String[] newTrain, int index) {
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(newTrain).subList(0, trainDataCount));
        trainsData.remove(index);
        trainsData.add(index, temp);
    }
    public void van_change(String[] newVan, int index) {
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(newVan).subList(0, vanDataCount)); //?????
        vansData.remove(index);
        vansData.add(index, temp);
    }
    public void delete_train(int index) {
        trainsData.remove(index);
        delete_van(index + 1);
        trainCount = trainsData.size();
        vansCount = vansData.size();
        updateTable();
    }
    public void delete_van(Integer index) {
        ArrayList<Integer> temp = new ArrayList<>();
        int count = 0;
        for(int i = 0; i < vansCount; i++) {
            if(vansData.get(i).get(0).equals(index.toString())) {
                temp.add(i);
                count++;
            }
        }
        int buf = temp.get(0);
        for (int i = 0; i < count; i++) vansData.remove(buf);
    }
    public void createTable() {
        DefaultTableModel trainsModel = new DefaultTableModel(get_trainsData(), columnNames[0]){
            @Override
            public boolean isCellEditable(int row, int column) {
                return isChangeable;
            }
        };
       DefaultTableModel vansModel = new DefaultTableModel(get_vansData(), columnNames[1]){
            @Override
            public boolean isCellEditable(int row, int column) {
                return isChangeable;
            }
        };

        trainTable = new JTable(trainsModel);
        vansTable = new JTable(vansModel);

        RowSorter<TableModel> trainSorter = new TableRowSorter<>(trainsModel);
        trainTable.setRowSorter(trainSorter);
        tempChangedRows = new ArrayList<>();
        RowSorter<TableModel> vanSorter = new TableRowSorter<>(vansModel);
        vansTable.setRowSorter(vanSorter);



        trainTable.getModel().addTableModelListener(evt -> tempChangedRows.add(trainTable.getEditingRow()));
        Data db = this;
        trainTable.addMouseListener (new MouseAdapter () {
            @Override
            public void mouseClicked (MouseEvent e) {
                if (e.getButton () == 3) {
                    if (trainTable.columnAtPoint(e.getPoint()) == 0 && !deleteMode) {
                        vtWindow = new VansTableWindow(db);
                        vtWindow.setVisible(true);
                        isVTWindowOpened = true;
                    } else {
                        int row = trainTable.rowAtPoint(e.getPoint());
                        delete_train(row);
                    }
                }
            }
        });

    }
    public void updateTable() {
        for (Integer tempChangedRow : tempChangedRows) {
            String[] bufArray = new String[trainDataCount];
            for (int count = 0; count < trainTable.getColumnCount(); count++)
                bufArray[count] = trainTable.getValueAt(tempChangedRow, count).toString();
            train_change(bufArray, tempChangedRow);
        }
        tempChangedRows.clear();
        try {
            loadInFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
