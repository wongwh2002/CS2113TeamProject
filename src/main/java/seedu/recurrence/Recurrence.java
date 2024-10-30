package seedu.recurrence;

import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;
import seedu.type.EntryType;

import java.time.LocalDate;
import java.time.YearMonth;

public abstract class Recurrence {
    protected int getLastDayOfMonth(LocalDate newEntry) {
        YearMonth date = YearMonth.of(newEntry.getYear(), newEntry.getMonth());
        return date.atEndOfMonth().getDayOfMonth();
    }

    protected <T extends EntryType> void checkIfDateAltered(T newEntry, LocalDate checkDate) {
        int dayOfSupposedRecurrence = newEntry.getDayOfRecurrence();
        int lastDayOfNewEntryMonth = getLastDayOfMonth(newEntry.getDate());
        newEntry.editDateWithLocalDate(checkDate.withDayOfMonth(Math.min(dayOfSupposedRecurrence,
                lastDayOfNewEntryMonth)));
    }

    public abstract void checkIncomeRecurrence(Income recurringIncome, IncomeList incomes);
    public abstract void checkSpendingRecurrence(Spending recurringSpending, SpendingList spendings);
}
