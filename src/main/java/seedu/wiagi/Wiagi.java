package seedu.wiagi;

import seedu.classes.Password;
import seedu.classes.Parser;
import seedu.classes.WiagiLogger;
import seedu.storage.Storage;
import seedu.commands.Command;
import seedu.type.IncomeList;
import seedu.classes.Ui;
import seedu.type.SpendingList;

import java.util.NoSuchElementException;
import java.util.logging.Level;

/**
 * Main application class for Wiagi, a financial tracking application.
 * Initializes the application and manages the execution flow.
 */
public class Wiagi {

    private static Storage storage;
    private static IncomeList incomes;
    private static SpendingList spendings;

    private Wiagi() {
        WiagiLogger.initLogger();
        storage = new Storage();
        incomes = Storage.getIncomes();
        spendings = Storage.getSpendings();
        incomes.updateRecurrence();
        spendings.updateRecurrence();
    }

    private void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Ui.printWithTab("Saving data...");
            storage.save(incomes, spendings);
        }));
        Ui.welcome();
        int password = Storage.getPassword();
        boolean isLoginSuccessful = false;
        while (!isLoginSuccessful) {
            Ui.printWithTab("Please Enter Login Credentials:");
            String loginCredentials = Ui.readCommand();
            isLoginSuccessful = Password.validate(password, loginCredentials);
            Ui.printSeparator();
        }

        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = Ui.readCommand();
                Command c = Parser.parseUserInput(fullCommand);
                c.execute(incomes, spendings);
                isExit = c.isExit();
                Ui.printSeparator();
                storage.save(incomes, spendings);
            } catch (NoSuchElementException e) {
                WiagiLogger.logger.log(Level.WARNING, "Nothing to read", e);
            }
        }
    }

    /**
     * Main entry point for the Wiagi application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Wiagi().run();
    }
}

