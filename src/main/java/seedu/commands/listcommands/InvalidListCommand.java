package seedu.commands.listcommands;

import seedu.classes.Ui;
import seedu.commands.Command;
import seedu.exception.WiagiInvalidInputException;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

public class InvalidListCommand extends Command {

    private final WiagiInvalidInputException exception;

    public InvalidListCommand(WiagiInvalidInputException e) {
        this.exception = e;
    }

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        Ui.printWithTab(exception.getMessage());
    }
}
