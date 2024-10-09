package seedu.storage;

import seedu.type.Type;
import seedu.classes.Ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ListStorage {
    public static ArrayList<T> load(String filePath, Type type) {
        ArrayList<Type> list;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (ArrayList<Type>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            list = new ArrayList<Type>();
        }
        return list;
    }

    public void save(ArrayList<Type> list, String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        } catch (IOException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

}
