package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiEmptyDescriptionException;
import seedu.exception.WiagiInvalidInputException;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

public class BudgetCommand extends Command {

    private final String fullCommand;

    public BudgetCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        String[] userInputWords = fullCommand.split(" ", 3);
        try {
            if (userInputWords.length < 3) {
                throw new IllegalArgumentException();
            }
            addBudget(userInputWords, spendings);
        } catch (IllegalArgumentException e) {
            Ui.printWithTab("Invalid input. " +
                    "Please enter in the form: budget [daily/monthly/yearly] [amount]");
        } catch (Exception e) {
            Ui.printWithTab("An error occurred. Please try again.");
        }
    }

    private void addBudget(String[] userInputWords, SpendingList spendings) {
        try {
            int budget = Integer.parseInt(userInputWords[2]);

            switch (userInputWords[1]) {
            case "daily":
                spendings.setDailyBudget(budget);
                break;
            case "monthly":
                spendings.setMonthlyBudget(budget);
                break;
            case "yearly":
                spendings.setYearlyBudget(budget);
                break;
            default:
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException e) {
            Ui.printWithTab("Invalid amount. Please try again.");
        }
    }


}
