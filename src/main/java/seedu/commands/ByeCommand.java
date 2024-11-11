package seedu.commands;

import seedu.classes.Ui;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import static seedu.classes.Constants.BYE_MESSAGE;

public class ByeCommand extends Command {

    public static final String COMMAND_WORD = "bye";

    /**
     * Executes a bye command with the given arguments
     * @param incomes   list of incomes in the application
     * @param spendings list of spendings in the application
     */
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        Ui.printWithTab(BYE_MESSAGE);
    }
}
