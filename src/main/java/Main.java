import data.Data;
import windows.mains.FirstWindow;

public class Main {

    public static final String trainFilename = "C:\\Users\\Антон\\Desktop\\ЛФБЫ\\джава\\lab5v2\\src\\main\\java\\trainsData.txt";
    public static final String vanFilename = "C:\\Users\\Антон\\Desktop\\ЛФБЫ\\джава\\lab5v2\\src\\main\\java\\vansData.txt";

    static void loadDB(Data db) {
        db.loadFromFile(trainFilename, vanFilename);
    }

    static void start(Data db) {
        loadDB(db);
        FirstWindow fWindow;
        fWindow = new FirstWindow(db);
        fWindow.setVisible(true);
    }


    public static void main(String[] args) {
        Data mainDB = new Data();
        start(mainDB);
    }
}