package seedu.type;

import java.time.LocalDate;
import java.util.ArrayList;

public class SpendingList extends ArrayList<Spending> {
    private int dailyBudget;
    private int monthlyBudget;
    private int yearlyBudget;

    public SpendingList() {
        super();
    }

    public SpendingList(SpendingList spendings) {
        super(spendings);  // Initialise with data in storage
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


    public int getMontlySpending() {
        int spendingTotal = 0;
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        for (Spending spending : this) {
            if (spending.getDate().isAfter(oneMonthAgo) && spending.getDate().isBefore(LocalDate.now().plusDays(1))) {
                spendingTotal++;
            }
        }
        return spendingTotal;
    }

    public int getDailySpending() {
        int spendingTotal = 0;
        for (Spending spending : this) {
            if (spending.getDate().isEqual(LocalDate.now())) {
                spendingTotal++;
            }
        }
        return spendingTotal;
    }

    public int getYearlySpending() {
        int spendingTotal = 0;
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        for (Spending spending : this) {
            if (spending.getDate().isAfter(oneYearAgo) && spending.getDate().isBefore(LocalDate.now().plusDays(1))) {
                spendingTotal++;
            }
        }
        return spendingTotal;
    }

}


