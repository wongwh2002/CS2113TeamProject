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

    public static IncomeList load() {
        IncomeList incomes;
        try {
            FileInputStream fis = new FileInputStream(INCOME_FILE_PATH);
            ObjectInputStream ois = new ObjectInputStream(fis);
            incomes = (IncomeList) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            incomes = new IncomeList();
        }
        return incomes;
    }

    public static void save(IncomeList incomes) {
        try {
            FileOutputStream fos = new FileOutputStream(INCOME_FILE_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(incomes);
        } catch (IOException e) {
            Ui.printWithTab(e.getMessage());
        }
    }
}
