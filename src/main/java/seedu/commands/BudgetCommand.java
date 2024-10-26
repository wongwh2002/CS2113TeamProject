package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import static seedu.classes.Constants.BUDGET_COMMAND_FORMAT;
import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.SPACE_REGEX;

/**
 * Represents a command to set a budget.
 */
public class BudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";
    private static final int BUDGET_COMPULSORY_ARGUMENTS_LENGTH = 3;

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
        String[] arguments = fullCommand.split(SPACE_REGEX, 3);
        try {
            if (arguments.length != BUDGET_COMPULSORY_ARGUMENTS_LENGTH) {
                throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER
                        + BUDGET_COMMAND_FORMAT);
            }
            addBudget(arguments, spendings);
        } catch (WiagiMissingParamsException | WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        } catch (Exception e) {
            Ui.printWithTab("An error occurred. Please try again.");
        }
    }

    private void addBudget(String[] arguments, SpendingList spendings) {
        try {
            int budget = Integer.parseInt(arguments[2]);
            String budgetTimeRange = arguments[1];

            switch (budgetTimeRange) {
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
                throw new WiagiInvalidInputException(INVALID_CATEGORY + BUDGET_COMMAND_FORMAT);
            }
        } catch (NumberFormatException e) {
            Ui.printWithTab("Invalid amount. Please try again.");
        }
    }
}
