package seedu.commands;

import seedu.classes.Ui;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import static seedu.classes.Constants.BYE_MESSAGE;

public class ByeCommand extends Command {

    public static final String COMMAND_WORD = "bye";

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        Ui.printWithTab(BYE_MESSAGE);
    }
}
