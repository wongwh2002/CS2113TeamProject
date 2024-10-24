package seedu.recurrence;

import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.time.LocalDate;

public class YearlyRecurrence extends Recurrence {

    @Override
    public void checkIncomeRecurrence(Income recurringIncome, IncomeList incomes) {
        LocalDate lastRecurred = recurringIncome.getLastRecurrence();
        Income copyEntry = new Income(recurringIncome);
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
            recurringIncome.editLastRecurrence(date);
        }
    }

    @Override
    public void checkSpendingRecurrence(Spending recurringSpending, SpendingList spendings) {
        LocalDate lastRecurred = recurringSpending.getLastRecurrence();
        Spending copyEntry = new Spending(recurringSpending);
        if (lastRecurred.getYear() < LocalDate.now().getYear()) {
            if (lastRecurred.plusYears(1).isAfter(LocalDate.now())) {
                return;
            }
            LocalDate date = lastRecurred.plusYears(1);
            for (; date.isBefore(LocalDate.now()); date = date.plusYears(1)) {
                copyEntry.editDateWithLocalDate(date);
                Spending newEntry = new Spending(copyEntry);
                spendings.add(newEntry);
            }
            date = date.minusYears(1);
            recurringSpending.editLastRecurrence(date);
        }
    }
}
