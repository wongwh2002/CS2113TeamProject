package seedu.commands;

import seedu.classes.Ui;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

/**
 * Represents a command to set a budget.
 */
public class BudgetCommand extends Command {

    private final String fullCommand;

    /**
     * Constructs a BudgetCommand with the specified full command.
     *
     * @param fullCommand The full command string.
     */
    public BudgetCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * Executes the budget command by parsing the user input and setting the budget.
     *
     * @param incomes   The list of incomes.
     * @param spendings The list of spendings.
     */
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        String[] userInputWords = fullCommand.split(" ", 3);
        try {
            if (userInputWords.length != 3) {
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
                Ui.printWithTab("Successfully set daily budget of: " + budget);
                break;
            case "monthly":
                spendings.setMonthlyBudget(budget);
                Ui.printWithTab("Successfully set monthly budget of: " + budget);
                break;
            case "yearly":
                spendings.setYearlyBudget(budget);
                Ui.printWithTab("Successfully set yearly budget of: " + budget);
                break;
            default:
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException e) {
            Ui.printWithTab("Invalid amount. Please try again.");
        }
    }


}
