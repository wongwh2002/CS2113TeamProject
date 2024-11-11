package seedu.recurrence;

import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.time.LocalDate;

import static seedu.classes.Constants.TODAY;

/**
 * Used to manage recurring entries labelled as yearly recurrence in the user's {@code IncomeList} and
 * {@code SpendingList} and adds recurring entries when needed
 */
public class YearlyRecurrence extends Recurrence {
    private static final int YEARLY_FREQUENCY = 1;

    /**
     * Checks if the {@code Income} entry is due for recurrence, updates the {@code lastRecurred} attribute of the entry
     * adds new entries to the {@code IncomeList} if user wants to backlog entries (For Years)
     * @param recurringIncome {@code Income} entry to be checked
     * @param incomes {@code IncomeList} of the user
     * @param isAdding Set to true to allow adding of backlog entries, otherwise to only update {@code lastRecurred}
     *      attribute of entry
     */
    @Override
    public void checkIncomeRecurrence(Income recurringIncome, IncomeList incomes, boolean isAdding) {
        LocalDate lastRecurred = recurringIncome.getLastRecurrence();
        assert lastRecurred != null : "should only be checking entries with recurrence, " +
                "lastRecurred should be initialised";
        LocalDate checkDate = lastRecurred.plusYears(YEARLY_FREQUENCY);
        while (!checkDate.isAfter(TODAY)) {
            Income newEntry = new Income(recurringIncome);
            checkIfDateAltered(newEntry, checkDate, incomes, isAdding);
            checkDate = checkDate.plusYears(YEARLY_FREQUENCY);
        }
        checkDate = checkDate.minusYears(YEARLY_FREQUENCY);
        assert !checkDate.isAfter(TODAY) && checkDate.plusYears(YEARLY_FREQUENCY).isAfter(TODAY)
                : "last recurrence should be within one year";
        recurringIncome.editLastRecurrence(checkDate);
    }

    /**
     * Checks if the {@code Spending} entry is due for recurrence, updates the {@code lastRecurred}
     * attribute of the entry
     * adds new entries to the {@code SpendingList} if user wants to backlog entries (For Years)
     * @param recurringSpending {@code Spending} entry to be checked
     * @param spendings {@code SpendingList} of the user
     * @param isAdding Set to true to allow adding of backlog entries, otherwise to only update {@code lastRecurred}
     *      attribute of entry
     */
    @Override
    public void checkSpendingRecurrence(Spending recurringSpending, SpendingList spendings, boolean isAdding) {
        LocalDate lastRecurred = recurringSpending.getLastRecurrence();
        assert lastRecurred != null : "should only be checking entries with recurrence, " +
                "lastRecurred should be initialised";
        LocalDate checkDate = lastRecurred.plusYears(YEARLY_FREQUENCY);
        while (!checkDate.isAfter(TODAY)) {
            Spending newEntry = new Spending(recurringSpending);
            checkIfDateAltered(newEntry, checkDate, spendings, isAdding);
            checkDate = checkDate.plusYears(YEARLY_FREQUENCY);
        }
        checkDate = checkDate.minusYears(YEARLY_FREQUENCY);
        assert !checkDate.isAfter(TODAY) && checkDate.plusYears(YEARLY_FREQUENCY).isAfter(TODAY)
                : "last recurrence should be within one year";
        recurringSpending.editLastRecurrence(checkDate);
    }
}
