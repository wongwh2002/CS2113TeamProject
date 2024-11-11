package seedu.commands;

import seedu.classes.Ui;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import static seedu.classes.Constants.UNKNOWN_COMMAND_MESSAGE;

public class UnknownCommand extends Command {
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        Ui.printWithTab(UNKNOWN_COMMAND_MESSAGE);
    }
}
