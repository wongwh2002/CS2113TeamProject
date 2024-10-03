package seedu.commands;

import seedu.type.IncomeList;
import seedu.type.SpendingList;

public abstract class Command {
    public boolean isExit() {
        return this instanceof ByeCommand;
    }

    public abstract void execute(IncomeList incomes, SpendingList spendings);
}

