package seedu.recurrence;

import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.time.LocalDate;

public class DailyRecurrence extends Recurrence{

    @Override
    public void checkIncomeRecurrence(Income recurrence, IncomeList incomes) {
        LocalDate lastRecurred = recurrence.getLastRecurrence();
        Income copyEntry = new Income(recurrence);
        if (LocalDate.now().isAfter(lastRecurred)) {
            LocalDate date = lastRecurred.plusDays(1);
            for (; date.isBefore(LocalDate.now().plusDays(1)); date = date.plusDays(1)) {
                copyEntry.editDateWithLocalDate(date);
                Income newEntry = new Income(copyEntry);
                incomes.add(newEntry);
            }
            date = date.minusDays(1);
            recurrence.editLastRecurrence(date);
        }
    }

    @Override
    public void checkSpendingRecurrence(Spending recurrence, SpendingList spendings) {
        LocalDate lastRecurred = recurrence.getLastRecurrence();
        Spending copyEntry = new Spending(recurrence);
        if (LocalDate.now().isAfter(lastRecurred)) {
            LocalDate date = lastRecurred.plusDays(1);
            for (; date.isBefore(LocalDate.now().plusDays(1)); date = date.plusDays(1)) {
                copyEntry.editDateWithLocalDate(date);
                Spending newEntry = new Spending(copyEntry);
                spendings.add(newEntry);
            }
            date = date.minusDays(1);
            recurrence.editLastRecurrence(date);
        }
    }
}
