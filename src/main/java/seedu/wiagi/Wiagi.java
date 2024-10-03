package seedu.wiagi;

import seedu.classes.Parser;
import seedu.commands.Command;
import seedu.type.IncomeList;
import seedu.classes.Ui;
import seedu.type.SpendingList;

public class Wiagi {

    private final SpendingList spendings = new SpendingList();
    private final IncomeList incomes =  new IncomeList();
    private final Ui ui = new Ui();

    private void run() {
        Ui.welcome();
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
