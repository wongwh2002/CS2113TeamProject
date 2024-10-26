package seedu.type;

import seedu.classes.Parser;
import seedu.classes.Ui;
import seedu.recurrence.Recurrence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Represents a list of spendings with budget settings.
 */
public class SpendingList extends ArrayList<Spending> {
    private int dailyBudget;
    private int monthlyBudget;
    private int yearlyBudget;

    /**
     * Constructs an empty SpendingList with default budget values.
     */
    public SpendingList() {
        super();
        dailyBudget = 0;
        monthlyBudget = 0;
        yearlyBudget = 0;
    }

    /**
     * Constructs a SpendingList initialized with the data from another SpendingList.
     *
     * @param spendings The SpendingList to copy data from.
     */
    public SpendingList(SpendingList spendings) {
        super(spendings);  // Initialise with data in storage
        dailyBudget = 0;
        monthlyBudget = 0;
        yearlyBudget = 0;
    }

    /**
     * Returns the daily budget.
     *
     * @return The daily budget.
     */
    public int getDailyBudget() {
        return dailyBudget;
    }

    /**
     * Returns the monthly budget.
     *
     * @return The monthly budget.
     */
    public int getMonthlyBudget() {
        return monthlyBudget;
    }

    /**
     * Returns the yearly budget.
     *
     * @return The yearly budget.
     */
    public int getYearlyBudget() {
        return yearlyBudget;
    }

    /**
     * Sets the daily budget.
     *
     * @param dailyBudget The daily budget to set.
     */
    public void setDailyBudget(int dailyBudget) {
        this.dailyBudget = dailyBudget;
    }

    /**
     * Sets the monthly budget.
     *
     * @param monthlyBudget The monthly budget to set.
     */
    public void setMonthlyBudget(int monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    /**
     * Sets the yearly budget.
     *
     * @param yearlyBudget The yearly budget to set.
     */
    public void setYearlyBudget(int yearlyBudget) {
        this.yearlyBudget = yearlyBudget;
    }

    /**
     * Calculates the total spending for the current month.
     *
     * @return The total spending for the current month.
     */
    public double getMonthlySpending() {
        double spendingTotal = 0;
        for (Spending spending : this) {
            if (isThisMonth(spending.getDate())) {
                spendingTotal = spendingTotal + spending.getAmount();
            }
        }
        return spendingTotal;
    }

    /**
     * Calculates the total spending for the current day.
     *
     * @return The total spending for the current day.
     */
    public double getDailySpending() {
        double spendingTotal = 0;
        for (Spending spending : this) {
            if (spending.getDate().isEqual(LocalDate.now())) {
                spendingTotal = spendingTotal + spending.getAmount();
            }
        }
        return spendingTotal;
    }

    /**
     * Calculates the total spending for the current year.
     *
     * @return The total spending for the current year.
     */
    public double getYearlySpending() {
        double spendingTotal = 0;
        for (Spending spending : this) {
            if (isThisYear(spending.getDate())) {
                spendingTotal = spendingTotal + spending.getAmount();
            }
        }
        return spendingTotal;
    }

    private boolean isThisYear(LocalDate date) {
        int yearsToSubtract = 1;
        LocalDate oneYearAgo = LocalDate.now().minusYears(yearsToSubtract);
        return date.isAfter(oneYearAgo) && date.isBefore(LocalDate.now().plusDays(1));
    }

    private boolean isThisMonth(LocalDate date) {
        int monthsToSubtract = 1;
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(monthsToSubtract);
        return date.isAfter(oneMonthAgo) && date.isBefore(LocalDate.now().plusDays(1));
    }

    public void updateRecurrence() {
        int size = this.size();
        for (int i = 0; i < size; i++) {
            Spending spending = this.get(i);
            Recurrence recurrence = Parser.parseRecurrence(spending);
            if (recurrence != null) {
                recurrence.checkSpendingRecurrence(spending, this);
            }
        }
        this.sort(Comparator.comparing(Type::getDate));
    }

    public void checkOverspend() {
        double dailyBudgetLeft = getDailyBudget() - getDailySpending();
        double monthlyBudgetLeft = getMonthlyBudget() - getMonthlySpending();
        double yearlyBudgetLeft = getYearlyBudget() - getYearlySpending();
        if (dailyBudgetLeft  < 0) {
            Ui.printOverspendMessage("daily", dailyBudgetLeft);
        }
        if (monthlyBudgetLeft  < 0) {
            Ui.printOverspendMessage("monthly", monthlyBudgetLeft);
        }
        if (yearlyBudgetLeft  < 0) {
            Ui.printOverspendMessage("yearly", yearlyBudgetLeft);
        }
    }
}


