package seedu.recurrence;

import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.time.LocalDate;

public class DailyRecurrence extends Recurrence{

    @Override
    public void checkIncomeRecurrence(Income recurringIncome, IncomeList incomes) {
        LocalDate lastRecurred = recurringIncome.getLastRecurrence();
        assert lastRecurred != null : "should only be checking entries with recurrence, " +
                "lastRecurred should be initialised";
        if (LocalDate.now().isAfter(lastRecurred)) {
            LocalDate checkDate = lastRecurred.plusDays(1);
            for (; checkDate.isBefore(LocalDate.now().plusDays(1)); checkDate = checkDate.plusDays(1)) {
                Income newEntry = new Income(recurringIncome);
                newEntry.editDateWithLocalDate(checkDate);
                incomes.add(newEntry);
            }
            checkDate = checkDate.minusDays(1);
            assert checkDate.equals(LocalDate.now()) : "last recurred should be today";
            recurringIncome.editLastRecurrence(checkDate);
        }
    }

    @Override
    public void checkSpendingRecurrence(Spending recurringSpending, SpendingList spendings) {
        LocalDate lastRecurred = recurringSpending.getLastRecurrence();
        assert lastRecurred != null : "should only be checking entries with recurrence, " +
                "lastRecurred should be initialised";
        if (LocalDate.now().isAfter(lastRecurred)) {
            LocalDate checkDate = lastRecurred.plusDays(1);
            for (; checkDate.isBefore(LocalDate.now().plusDays(1)); checkDate = checkDate.plusDays(1)) {
                Spending newEntry = new Spending(recurringSpending);
                newEntry.editDateWithLocalDate(checkDate);
                spendings.add(newEntry);
            }
            checkDate = checkDate.minusDays(1);
            assert checkDate.equals(LocalDate.now()) : "last recurred should be today";
            recurringSpending.editLastRecurrence(checkDate);
        }
    }
}
