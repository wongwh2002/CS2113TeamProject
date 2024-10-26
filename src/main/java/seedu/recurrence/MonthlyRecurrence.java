package seedu.recurrence;

import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.time.LocalDate;

public class MonthlyRecurrence extends Recurrence {
    @Override
    public void checkIncomeRecurrence(Income recurringIncome, IncomeList incomes) {
        LocalDate lastRecurred = recurringIncome.getLastRecurrence();
        Income copyEntry = new Income(recurringIncome);
        copyEntry.setRecurrenceToNone();
        int currentTotalMonth = LocalDate.now().getYear() * 12 + LocalDate.now().getMonthValue();
        int lastRecurredTotalMonth = lastRecurred.getYear() * 12 + lastRecurred.getMonthValue();
        if (currentTotalMonth > lastRecurredTotalMonth) {
            LocalDate checkDate = lastRecurred.plusMonths(1);
            for (; !checkDate.isAfter(LocalDate.now());
                    checkDate = checkDate.plusMonths(1)) {
                copyEntry.editDateWithLocalDate(checkDate);
                Income newEntry = new Income(copyEntry);
                checkIfDateAltered(newEntry, checkDate, incomes);
            }
            checkDate = checkDate.minusMonths(1);
            recurringIncome.editLastRecurrence(checkDate);
        }
    }

    @Override
    public void checkSpendingRecurrence(Spending recurringSpending, SpendingList spendings) {
        LocalDate lastRecurred = recurringSpending.getLastRecurrence();
        Spending copyEntry = new Spending(recurringSpending);
        copyEntry.setRecurrenceToNone();
        int currentTotalMonth = LocalDate.now().getYear() * 12 + LocalDate.now().getMonthValue();
        int lastRecurredTotalMonth = lastRecurred.getYear() * 12 + lastRecurred.getMonthValue();
        if (currentTotalMonth > lastRecurredTotalMonth) {
            LocalDate checkDate = lastRecurred.plusMonths(1);
            for (; !checkDate.isAfter(LocalDate.now());
                 checkDate = checkDate.plusMonths(1)) {
                copyEntry.editDateWithLocalDate(checkDate);
                Spending newEntry = new Spending(copyEntry);
                checkIfDateAltered(newEntry, checkDate, spendings);
            }
            checkDate = checkDate.minusMonths(1);
            recurringSpending.editLastRecurrence(checkDate);
        }
    }

}
