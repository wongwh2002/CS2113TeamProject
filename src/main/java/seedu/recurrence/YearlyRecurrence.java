package seedu.recurrence;

import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.time.LocalDate;

public class YearlyRecurrence extends Recurrence {

    @Override
    public void checkIncomeRecurrence(Income recurrence, IncomeList incomes) {
        LocalDate lastRecurred = recurrence.getLastRecurrence();
        Income copyEntry = new Income(recurrence);
        if (lastRecurred.getYear() < LocalDate.now().getYear()) {
            if (lastRecurred.plusYears(1).isAfter(LocalDate.now())) {
                return;
            }
            LocalDate date = lastRecurred.plusYears(1);
            for (; date.isBefore(LocalDate.now().plusYears(1));
                 date = date.plusYears(1)) {
                copyEntry.editDateWithLocalDate(date);
                Income newEntry = new Income(copyEntry);
                incomes.add(newEntry);
            }
            date = date.minusYears(1);
            recurrence.editLastRecurrence(date);
        }
    }

    @Override
    public void checkSpendingRecurrence(Spending recurrence, SpendingList spendings) {
        LocalDate lastRecurred = recurrence.getLastRecurrence();
        Spending copyEntry = new Spending(recurrence);
        if (lastRecurred.getYear() < LocalDate.now().getYear()) {
            if (lastRecurred.plusYears(1).isAfter(LocalDate.now())) {
                return;
            }
            LocalDate date = lastRecurred.plusYears(1);
            for (; date.isBefore(LocalDate.now());
                 date = date.plusYears(1)) {
                copyEntry.editDateWithLocalDate(date);
                Spending newEntry = new Spending(copyEntry);
                spendings.add(newEntry);
            }
            date = date.minusYears(1);
            recurrence.editLastRecurrence(date);
        }
    }
}
