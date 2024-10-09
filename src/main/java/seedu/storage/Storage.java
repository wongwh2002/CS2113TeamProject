package seedu.classes;

import seedu.type.IncomeList;
import seedu.type.SpendingList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private static final String PASSWORD_FILE_PATH = "./password.txt";
    private int passwordHash = 0;

    public Storage(Ui ui) {
        if (new File(PASSWORD_FILE_PATH).exists()) {
            this.passwordHash = getPasswordHashFromStorage();
            return;
        }
        try {
            new File(PASSWORD_FILE_PATH).createNewFile();
            FileWriter fw = new FileWriter(PASSWORD_FILE_PATH);
            this.passwordHash = new Login().createNewUser(ui);
            fw.write(Integer.toString(passwordHash));
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void load(IncomeList incomes, SpendingList spendings, Login login) {

    }

    private int getPasswordHashFromStorage() {
        int passwordHash = 0;
        try {
            Scanner s = new Scanner(new File(PASSWORD_FILE_PATH));
            passwordHash =  Integer.parseInt(s.next());
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return passwordHash;
    }

    public int getPasswordHash() {
        return passwordHash;
    }
}
