package seedu.recurrence;

import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.time.LocalDate;

/**
 * Used to manage recurring entries labelled as monthly recurrence in the user's {@code IncomeList} and
 * {@code SpendingList} and adds recurring entries when needed
 */
public class MonthlyRecurrence extends Recurrence {
    private static final int MONTHLY_FREQUENCY = 1;

    @Override
    public void checkIncomeRecurrence(Income recurringIncome, IncomeList incomes, boolean isAdding) {
        LocalDate lastRecurred = recurringIncome.getLastRecurrence();
        assert lastRecurred != null : "should only be checking entries with recurrence, " +
                "lastRecurred should be initialised";
        LocalDate checkDate = lastRecurred.plusMonths(MONTHLY_FREQUENCY);
        while (!checkDate.isAfter(LocalDate.now())) {
            Income newEntry = new Income(recurringIncome);
            checkIfDateAltered(newEntry, checkDate, incomes, isAdding);
            checkDate = checkDate.plusMonths(MONTHLY_FREQUENCY);
        }
        checkDate = checkDate.minusMonths(MONTHLY_FREQUENCY);
        assert !checkDate.isAfter(LocalDate.now()) &&
                checkDate.plusMonths(MONTHLY_FREQUENCY).isAfter(LocalDate.now())
                : "last recurrence should be within one month";
        recurringIncome.editLastRecurrence(checkDate);
    }

    @Override
    public void checkSpendingRecurrence(Spending recurringSpending, SpendingList spendings, boolean isAdding) {
        LocalDate lastRecurred = recurringSpending.getLastRecurrence();
        assert lastRecurred != null : "should only be checking entries with recurrence, " +
                "lastRecurred should be initialised";
        LocalDate checkDate = lastRecurred.plusMonths(MONTHLY_FREQUENCY);
        while (!checkDate.isAfter(LocalDate.now())) {
            Spending newEntry = new Spending(recurringSpending);
            checkIfDateAltered(newEntry, checkDate, spendings, isAdding);
            checkDate = checkDate.plusMonths(MONTHLY_FREQUENCY);
        }
        checkDate = checkDate.minusMonths(MONTHLY_FREQUENCY);
        assert !checkDate.isAfter(LocalDate.now()) &&
                checkDate.plusMonths(MONTHLY_FREQUENCY).isAfter(LocalDate.now())
                : "last recurrence should be within one month";
        recurringSpending.editLastRecurrence(checkDate);
    }
}
