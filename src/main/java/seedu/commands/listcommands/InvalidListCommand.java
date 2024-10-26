package seedu.commands.listcommands;

import seedu.classes.Ui;
import seedu.commands.Command;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

public class InvalidListCommand extends Command {

    private final Exception exception;

    public InvalidListCommand(Exception e) {
        this.exception = e;
    }

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        Ui.printWithTab(exception.getMessage());
    }
}
