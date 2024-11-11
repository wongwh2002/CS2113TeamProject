package seedu.storage;

import seedu.classes.Ui;
import seedu.classes.WiagiLogger;
import seedu.commands.BudgetCommand;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;

import static seedu.storage.IncomeListStorage.INCOMES_FILE_PATH;
import static seedu.storage.SpendingListStorage.SPENDINGS_FILE_PATH;

/**
 * Handles the retrieval and storage of user password data
 */
public class LoginStorage {
    static final String PASSWORD_FILE_PATH = "./password.txt";

    /**
     * Retrieves the data of the user password from its data file into the program
     */
    static void load() {
        WiagiLogger.logger.log(Level.INFO, "Starting to load password file...");
        try {
            File passwordFile = new File(PASSWORD_FILE_PATH);
            if (passwordFile.exists()) {
                Scanner scanner = new Scanner(passwordFile);
                String passwordHash = scanner.next();
                Storage.password = Integer.parseInt(passwordHash);
            } else {
                createNewUser();
            }
        } catch (IOException e) {
            WiagiLogger.logger.log(Level.WARNING, "Unable to open password file", e);
            Ui.printWithTab(e.getMessage());
        } catch (NoSuchElementException | NumberFormatException e) {
            WiagiLogger.logger.log(Level.WARNING, "Password file was empty", e);
            Ui.errorLoadingPasswordMessage();
            createNewUser();
        }
        WiagiLogger.logger.log(Level.INFO, "Finish loading password file.");
    }

    private static void resetAllData() {
        new File(PASSWORD_FILE_PATH).delete();
        new File(SPENDINGS_FILE_PATH).delete();
        new File(INCOMES_FILE_PATH).delete();
        Storage.spendings = new SpendingList();
        Storage.incomes = new IncomeList();
    }

    private static void createNewUser() {
        resetAllData();
        WiagiLogger.logger.log(Level.INFO, "Creating new user...");
        try {
            FileWriter fw = new FileWriter(PASSWORD_FILE_PATH);
            int passwordHash = getNewUserPassword();
            fw.write(Integer.toString(passwordHash));
            fw.close();
            Storage.password = passwordHash;
            Ui.newUserBudgetMessage();
            BudgetCommand.initialiseBudget(Storage.spendings);
        } catch (IOException e) {
            WiagiLogger.logger.log(Level.WARNING, "Unable to open password file", e);
            Ui.printWithTab(e.getMessage());
        }
        WiagiLogger.logger.log(Level.INFO, "Finish creating new user");
    }

    private static int getNewUserPassword() {
        Ui.printSeparator();
        Ui.printWithTab("Hi! You seem to be new, are you ready?!");
        Ui.printWithTab("Please enter your new account password:");
        String password = Ui.readUserPassword();
        return password.hashCode();
    }
}
