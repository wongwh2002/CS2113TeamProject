package seedu.storage;

import seedu.classes.Ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LoginStorage {
    private static final String PASSWORD_FILE_PATH = "./password.txt";

    public static int load(Ui ui) {
        try {
            File f = new File(PASSWORD_FILE_PATH);
            boolean isFileCreated = f.exists();
            if (isFileCreated) {
                Scanner scanner = new Scanner(f);
                String passwordHash = scanner.next();
                return Integer.parseInt(passwordHash);
            }
            FileWriter fw = new FileWriter(PASSWORD_FILE_PATH);
            int passwordHash = createNewUser(ui);
            fw.write(Integer.toString(passwordHash));
            fw.close();
            return passwordHash;
        } catch (IOException e) {
            Ui.printWithTab(e.getMessage());
        }
        return 0;
    }

    private static int createNewUser(Ui ui) {
        Ui.printSeparator();
        Ui.printWithTab("Hi! You seem to be new, are you ready?!");
        Ui.printWithTab("Please enter your new account password:");
        String password = ui.readCommand();
        return password.hashCode();
    }
}
