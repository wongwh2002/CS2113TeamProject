package seedu.recurrence;

import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.time.LocalDate;

/**
 * Used to manage recurring entries labelled as yearly recurrence in the user's {@code IncomeList} and
 * {@code SpendingList} and adds recurring entries when needed
 */
public class YearlyRecurrence extends Recurrence {

    @Override
    public void checkIncomeRecurrence(Income recurringIncome, IncomeList incomes) {
        LocalDate lastRecurred = recurringIncome.getLastRecurrence();
        assert lastRecurred != null : "should only be checking entries with recurrence, " +
                "lastRecurred should be initialised";
        if (lastRecurred.getYear() < LocalDate.now().getYear()) {
            LocalDate checkDate = lastRecurred.plusYears(1);
            for (; !checkDate.isAfter(LocalDate.now()); checkDate = checkDate.plusYears(1)) {
                Income newEntry = new Income(recurringIncome);
                checkIfDateAltered(newEntry, checkDate, incomes);
            }
            checkDate = checkDate.minusYears(1);
            assert !checkDate.isAfter(LocalDate.now()) && checkDate.plusYears(1).isAfter(LocalDate.now())
                    : "last recurrence should be within one year";
            recurringIncome.editLastRecurrence(checkDate);
        }
    }

    @Override
    public void checkSpendingRecurrence(Spending recurringSpending, SpendingList spendings) {
        LocalDate lastRecurred = recurringSpending.getLastRecurrence();
        assert lastRecurred != null : "should only be checking entries with recurrence, " +
                "lastRecurred should be initialised";
        if (lastRecurred.getYear() < LocalDate.now().getYear()) {
            LocalDate checkDate = lastRecurred.plusYears(1);
            for (; !checkDate.isAfter(LocalDate.now()); checkDate = checkDate.plusYears(1)) {
                Spending newEntry = new Spending(recurringSpending);
                checkIfDateAltered(newEntry, checkDate, spendings);
            }
            checkDate = checkDate.minusYears(1);
            assert !checkDate.isAfter(LocalDate.now()) && checkDate.plusYears(1).isAfter(LocalDate.now())
                    : "last recurrence should be within one year";
            recurringSpending.editLastRecurrence(checkDate);
        }
    }
}
