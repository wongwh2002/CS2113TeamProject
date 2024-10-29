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
        copyEntry.setRecurrenceToNone();
        if (lastRecurred.plusYears(1).isAfter(LocalDate.now())) {
            return;
        }
        if (lastRecurred.getYear() < LocalDate.now().getYear()) {
            LocalDate checkDate = lastRecurred.plusYears(1);
            for (; !checkDate.isAfter(LocalDate.now()); checkDate = checkDate.plusYears(1)) {
                copyEntry.editDateWithLocalDate(checkDate);
                Income newEntry = new Income(copyEntry);
                checkIfDateAltered(newEntry, checkDate, incomes);
            }
            checkDate = checkDate.minusYears(1);
            recurringIncome.editLastRecurrence(checkDate);
        }
    }

    @Override
    public void checkSpendingRecurrence(Spending recurringSpending, SpendingList spendings) {
        LocalDate lastRecurred = recurringSpending.getLastRecurrence();
        Spending copyEntry = new Spending(recurringSpending);
        copyEntry.setRecurrenceToNone();
        if (lastRecurred.plusYears(1).isAfter(LocalDate.now())) {
            return;
        }
        if (lastRecurred.getYear() < LocalDate.now().getYear()) {
            LocalDate checkDate = lastRecurred.plusYears(1);
            for (; !checkDate.isAfter(LocalDate.now()); checkDate = checkDate.plusYears(1)) {
                copyEntry.editDateWithLocalDate(checkDate);
                Spending newEntry = new Spending(copyEntry);
                checkIfDateAltered(newEntry, checkDate, spendings);
            }
            checkDate = checkDate.minusYears(1);
            recurringSpending.editLastRecurrence(checkDate);
        }
    }
}
