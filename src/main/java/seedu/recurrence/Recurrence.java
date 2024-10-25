package seedu.recurrence;


import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.time.LocalDate;
import java.time.YearMonth;

public abstract class Recurrence {
    public int getLastDayOfMonth(LocalDate newEntry) {
        YearMonth date = YearMonth.of(newEntry.getYear(), newEntry.getMonth());
        return date.atEndOfMonth().getDayOfMonth();
    }

    public abstract void checkIncomeRecurrence(Income recurringIncome, IncomeList incomes);
    public abstract void checkSpendingRecurrence(Spending recurringIncome, SpendingList incomes);
}
