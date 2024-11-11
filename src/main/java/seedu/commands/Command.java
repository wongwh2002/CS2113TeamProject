package seedu.commands;

import seedu.type.IncomeList;
import seedu.type.SpendingList;

public abstract class Command {
    public boolean isExit() {
        return this instanceof ByeCommand;
    }

    /**
     * Executes a command with the given arguments
     * @param incomes   list of incomes in the application
     * @param spendings list of spendings in the application
     */
    public abstract void execute(IncomeList incomes, SpendingList spendings);
}

