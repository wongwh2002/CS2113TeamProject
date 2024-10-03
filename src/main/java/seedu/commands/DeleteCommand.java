package seedu.commands;

import seedu.classes.Ui;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

public class DeleteCommand extends Command {
    public DeleteCommand(String fullCommand) {
        super();
    }

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        Ui.printWithTab("delete");
    }
}
