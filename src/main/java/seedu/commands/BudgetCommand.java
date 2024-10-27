package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import static seedu.classes.Constants.BUDGET_COMMAND_FORMAT;
import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.INVALID_AMOUNT;
import static seedu.classes.Constants.SPACE_REGEX;

/**
 * Represents a command to set a budget.
 */
public class BudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";
    private static final int TIME_RANGE_INDEX = 1;
    private static final int BUDGET_AMOUNT_INDEX = 2;
    private static final int BUDGET_COMPULSORY_ARGUMENTS_LENGTH = 3;
    private static final String DAILY = "daily";
    private static final String MONTHLY = "monthly";
    private static final String YEARLY = "yearly";

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
        try {
            handleCommand(spendings);
        } catch (WiagiMissingParamsException | WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void handleCommand(SpendingList spendings) throws WiagiMissingParamsException {
        String[] arguments = extractArguments();
        String stringBudget = arguments[BUDGET_AMOUNT_INDEX];
        int budget = formatBudget(stringBudget);
        String timeRange = arguments[TIME_RANGE_INDEX].toLowerCase();
        addBudget(spendings, budget, timeRange);
    }

    private String[] extractArguments() throws WiagiMissingParamsException {
        String[] arguments = fullCommand.split(SPACE_REGEX, BUDGET_COMPULSORY_ARGUMENTS_LENGTH);
        if (arguments.length != BUDGET_COMPULSORY_ARGUMENTS_LENGTH) {
            throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER + BUDGET_COMMAND_FORMAT);
        }
        return arguments;
    }

    private int formatBudget(String stringBudget) {
        try {
            return Integer.parseInt(stringBudget);
        } catch (NumberFormatException e) {
            throw new WiagiInvalidInputException(INVALID_AMOUNT + BUDGET_COMMAND_FORMAT);
        }
    }

    private void addBudget(SpendingList spendings, int budget, String timeRange) {
        switch (timeRange) {
        case DAILY:
            spendings.setDailyBudget(budget);
            Ui.printWithTab("Successfully set daily budget of: " + budget);
            break;
        case MONTHLY:
            spendings.setMonthlyBudget(budget);
            Ui.printWithTab("Successfully set monthly budget of: " + budget);
            break;
        case YEARLY:
            spendings.setYearlyBudget(budget);
            Ui.printWithTab("Successfully set yearly budget of: " + budget);
            break;
        default:
            throw new WiagiInvalidInputException(INVALID_CATEGORY + BUDGET_COMMAND_FORMAT);
        }
    }
}

