package seedu.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private int passwordHash = 0;
    public static final String PASSWORD_FILE_PATH = "./password.txt";

    public Storage(Ui ui) {
        try {
            if (!new File(PASSWORD_FILE_PATH).exists()) {
                new File(PASSWORD_FILE_PATH).createNewFile();
                FileWriter fw = new FileWriter(PASSWORD_FILE_PATH);
                fw.write(new Login().createNewUser(ui));
                fw.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        this.passwordHash = getPasswordHashFromStorage();
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
