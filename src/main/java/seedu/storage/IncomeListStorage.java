package seedu.storage;

import seedu.type.IncomeList;
import seedu.classes.Ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IncomeListStorage {
    private static final String INCOME_FILE_PATH = "./incomes.txt";

    static void load() {
        try {
            FileInputStream fis = new FileInputStream(INCOME_FILE_PATH);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Storage.incomes = (IncomeList) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Storage.incomes = new IncomeList();
        }
    }

    static void save(IncomeList incomes) {
        try {
            FileOutputStream fos = new FileOutputStream(INCOME_FILE_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(incomes);
        } catch (IOException e) {
            Ui.printWithTab(e.getMessage());
        }
    }
}
