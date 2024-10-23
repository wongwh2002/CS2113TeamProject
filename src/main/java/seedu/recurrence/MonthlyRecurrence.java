package seedu.recurrence;

import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.time.LocalDate;

public class MonthlyRecurrence extends Recurrence {

    @Override
    public void checkIncomeRecurrence(Income recurrence, IncomeList incomes) {
        LocalDate lastRecurred = recurrence.getLastRecurrence();
        Income copyEntry = new Income(recurrence);
        int currentTotalMonth = LocalDate.now().getYear() * 12 + LocalDate.now().getMonthValue();
        int lastRecurredTotalMonth = lastRecurred.getYear() * 12 + lastRecurred.getMonthValue();
        if (currentTotalMonth > lastRecurredTotalMonth) {
            LocalDate checkDate = lastRecurred.plusMonths(1);
            for (; checkDate.isBefore(LocalDate.now());
                 checkDate = checkDate.plusMonths(1)) {
                copyEntry.editDateWithLocalDate(checkDate);
                Income newEntry = new Income(copyEntry);
                incomes.add(newEntry);
            }
            checkDate = checkDate.minusMonths(1);
            recurrence.editLastRecurrence(checkDate);
        }
    }

    @Override
    public void checkSpendingRecurrence(Spending recurrence, SpendingList spendings) {
        LocalDate lastRecurred = recurrence.getLastRecurrence();
        Spending copyEntry = new Spending(recurrence);
        int currentTotalMonth = LocalDate.now().getYear() * 12 + LocalDate.now().getMonthValue();
        int lastRecurredTotalMonth = lastRecurred.getYear() * 12 + lastRecurred.getMonthValue();
        if (currentTotalMonth > lastRecurredTotalMonth) {
            LocalDate checkDate = lastRecurred.plusMonths(1);
            for (; checkDate.isBefore(LocalDate.now());
                 checkDate = checkDate.plusMonths(1)) {
                copyEntry.editDateWithLocalDate(checkDate);
                Spending newEntry = new Spending(copyEntry);
                spendings.add(newEntry);
            }
            checkDate = checkDate.minusMonths(1);
            recurrence.editLastRecurrence(checkDate);
        }
    }

}
