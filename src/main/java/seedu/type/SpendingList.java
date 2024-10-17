package seedu.type;

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
}


