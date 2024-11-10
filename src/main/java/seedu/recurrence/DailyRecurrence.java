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
    private static final int DAILY_FREQUENCY = 1;

    @Override
    public void checkIncomeRecurrence(Income recurringIncome, IncomeList incomes, boolean isAdding) {
        LocalDate lastRecurred = recurringIncome.getLastRecurrence();
        assert lastRecurred != null : "should only be checking entries with recurrence, " +
                "lastRecurred should be initialised";
        LocalDate checkDate = lastRecurred.plusDays(DAILY_FREQUENCY);
        while (!checkDate.isAfter(LocalDate.now())) {
            Income newEntry = new Income(recurringIncome);
            newEntry.editDateWithLocalDate(checkDate);
            if (isAdding) {
                incomes.add(newEntry);
            }
            checkDate = checkDate.plusDays(DAILY_FREQUENCY);
        }
        checkDate = checkDate.minusDays(DAILY_FREQUENCY);
        assert checkDate.equals(LocalDate.now()) : "last recurred should be today";
        recurringIncome.editLastRecurrence(checkDate);
    }

    @Override
    public void checkSpendingRecurrence(Spending recurringSpending, SpendingList spendings, boolean isAdding) {
        LocalDate lastRecurred = recurringSpending.getLastRecurrence();
        assert lastRecurred != null : "should only be checking entries with recurrence, " +
                "lastRecurred should be initialised";
        LocalDate checkDate = lastRecurred.plusDays(DAILY_FREQUENCY);
        while (!checkDate.isAfter(LocalDate.now())) {
            Spending newEntry = new Spending(recurringSpending);
            newEntry.editDateWithLocalDate(checkDate);
            if (isAdding) {
                spendings.add(newEntry);
            }
            checkDate = checkDate.plusDays(DAILY_FREQUENCY);
        }
        checkDate = checkDate.minusDays(DAILY_FREQUENCY);
        recurringSpending.editLastRecurrence(checkDate);
    }
}
