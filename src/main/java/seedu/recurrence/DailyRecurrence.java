package seedu.recurrence;

import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.time.LocalDate;

/**
 * Used to manage recurring entries labelled as daily recurrence in the user's {@code IncomeList} and
 * {@code SpendingList} and adds recurring entries when needed
 */
public class DailyRecurrence extends Recurrence{

    @Override
    public void checkIncomeRecurrence(Income recurringIncome, IncomeList incomes) {
        LocalDate lastRecurred = recurringIncome.getLastRecurrence();
        Income copyEntry = new Income(recurringIncome);
        if (LocalDate.now().isAfter(lastRecurred)) {
            LocalDate date = lastRecurred.plusDays(1);
            for (; date.isBefore(LocalDate.now().plusDays(1)); date = date.plusDays(1)) {
                copyEntry.editDateWithLocalDate(date);
                Income newEntry = new Income(copyEntry);
                incomes.add(newEntry);
            }
            date = date.minusDays(1);
            recurringIncome.editLastRecurrence(date);
        }
    }

    @Override
    public void checkSpendingRecurrence(Spending recurringSpending, SpendingList spendings) {
        LocalDate lastRecurred = recurringSpending.getLastRecurrence();
        Spending copyEntry = new Spending(recurringSpending);
        if (LocalDate.now().isAfter(lastRecurred)) {
            LocalDate date = lastRecurred.plusDays(1);
            for (; date.isBefore(LocalDate.now().plusDays(1)); date = date.plusDays(1)) {
                copyEntry.editDateWithLocalDate(date);
                Spending newEntry = new Spending(copyEntry);
                spendings.add(newEntry);
            }
            date = date.minusDays(1);
            recurringSpending.editLastRecurrence(date);
        }
    }
}
