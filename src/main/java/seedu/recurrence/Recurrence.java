package seedu.recurrence;

import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

public abstract class Recurrence {

    public abstract void checkIncomeRecurrence(Income recurrence, IncomeList incomes);
    public abstract void checkSpendingRecurrence(Spending recurrence, SpendingList spendings);
}
