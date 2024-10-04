package seedu.wiagi;

import seedu.classes.Login;
import seedu.classes.Parser;
import seedu.classes.Storage;
import seedu.commands.Command;
import seedu.type.IncomeList;
import seedu.classes.Ui;
import seedu.type.SpendingList;

public class Wiagi {

    private final SpendingList spendings = new SpendingList();
    private final IncomeList incomes =  new IncomeList();
    private final Ui ui = new Ui();
    private final Storage storage = new Storage();
    private final Login login = new Login();
    private boolean isLoginSuccessful = false;

    private void run() {
        Ui.welcome();
        while (!isLoginSuccessful) {
            Ui.printSeparator();
            Ui.printWithTab("Please Enter Login Credentials: ");
            String loginCredentials = ui.readCommand();
            login.validateLoginCredentials(loginCredentials, storage);
            isLoginSuccessful = login.getLoginSuccess();
            Ui.printSeparator();
        }
        boolean isExit = false;
        while (!isExit) {
            String fullCommand = ui.readCommand();
            Ui.printSeparator();
            Command c = Parser.parse(fullCommand);
            c.execute(incomes, spendings);
            isExit = c.isExit();
            Ui.printSeparator();
        }
    }

    public static void main(String[] args) {
        new Wiagi().run();
    }
}
