package seedu.type;

import java.time.LocalDate;
import java.util.ArrayList;

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
    public int getMonthlySpending() {
        int spendingTotal = 0;
        for (Spending spending : this) {
            if (isThisMonth(spending.getDate())) {
                spendingTotal = spendingTotal + spending.getAmount();
            }
        }
        return spendingTotal;
    }

    private boolean isThisMonth(LocalDate date) {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        return date.isAfter(oneMonthAgo) && date.isBefore(LocalDate.now().plusDays(1));
    }

    /**
     * Calculates the total spending for the current day.
     *
     * @return The total spending for the current day.
     */
    public int getDailySpending() {
        int spendingTotal = 0;
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
    public int getYearlySpending() {
        int spendingTotal = 0;
        for (Spending spending : this) {
            if (isThisYear(spending.getDate())) {
                spendingTotal = spendingTotal + spending.getAmount();
            }
        }
        return spendingTotal;
    }

    private boolean isThisYear(LocalDate date) {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        return date.isAfter(oneYearAgo) && date.isBefore(LocalDate.now().plusDays(1));
    }

}


