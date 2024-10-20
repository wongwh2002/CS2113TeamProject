package seedu.type;

import java.time.LocalDate;
import java.util.ArrayList;

public class SpendingList extends ArrayList<Spending> {
    private int dailyBudget;
    private int monthlyBudget;
    private int yearlyBudget;

    public SpendingList() {
        super();
        dailyBudget = 0;
        monthlyBudget = 0;
        yearlyBudget = 0;
    }

    public SpendingList(SpendingList spendings) {
        super(spendings);  // Initialise with data in storage
        dailyBudget = 0;
        monthlyBudget = 0;
        yearlyBudget = 0;
    }

    public int getDailyBudget() {
        return dailyBudget;
    }

    public int getMonthlyBudget() {
        return monthlyBudget;
    }

    public int getYearlyBudget() {
        return yearlyBudget;
    }

    public void setDailyBudget(int dailyBudget) {
        this.dailyBudget = dailyBudget;
    }

    public void setMonthlyBudget(int monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    public void setYearlyBudget(int yearlyBudget) {
        this.yearlyBudget = yearlyBudget;
    }


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

    public int getDailySpending() {
        int spendingTotal = 0;
        for (Spending spending : this) {
            if (spending.getDate().isEqual(LocalDate.now())) {
                spendingTotal = spendingTotal + spending.getAmount();
            }
        }
        return spendingTotal;
    }

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


