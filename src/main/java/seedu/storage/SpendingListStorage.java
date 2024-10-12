package seedu.storage;

import seedu.type.SpendingList;
import seedu.classes.Ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SpendingListStorage {
    private static final String SPENDINGS_FILE_PATH = "./spendings.txt";

    static void load() {
        try {
            FileInputStream fis = new FileInputStream(SPENDINGS_FILE_PATH);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Storage.spendings = (SpendingList) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Storage.spendings = new SpendingList();
        }
    }

    static void save(SpendingList spendings) {
        try {
            FileOutputStream fos = new FileOutputStream(SpendingListStorage.SPENDINGS_FILE_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(spendings);
        } catch (IOException e) {
            Ui.printWithTab(e.getMessage());
        }
    }
}
