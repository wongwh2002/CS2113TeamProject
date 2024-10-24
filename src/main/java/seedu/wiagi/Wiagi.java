package seedu.wiagi;

import seedu.classes.Password;
import seedu.classes.Parser;
import seedu.storage.Storage;
import seedu.commands.Command;
import seedu.type.IncomeList;
import seedu.classes.Ui;
import seedu.type.SpendingList;

public class Wiagi {

    private static Ui ui;
    private static Storage storage;
    private static IncomeList incomes;
    private static SpendingList spendings;

    private Wiagi() {
        ui = new Ui();
        storage = new Storage(ui);
        incomes = Storage.getIncomes();
        spendings = Storage.getSpendings();
    }

    private void run() {
        Ui.welcome();
        int password = Storage.getPassword();
        boolean isLoginSuccessful = false;
        while (!isLoginSuccessful) {
            Ui.printWithTab("Please Enter Login Credentials:");
            String loginCredentials = ui.readCommand();
            isLoginSuccessful = Password.validate(password, loginCredentials);
            Ui.printSeparator();
        }

        boolean isExit = false;
        while (!isExit) {
            String fullCommand = ui.readCommand();
            Command c = Parser.parse(fullCommand);
            c.execute(incomes, spendings);
            isExit = c.isExit();
            Ui.printSeparator();
        }
        storage.save(incomes, spendings);
    }

    public static void main(String[] args) {
        new Wiagi().run();
    }
}

