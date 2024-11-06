package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import static seedu.classes.Constants.BUDGET_COMMAND_FORMAT;
import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.WHITESPACE;

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
    private static final String DAILY_BUDGET_SUCCESS_MESSAGE = "Successfully set daily budget of: ";
    private static final String MONTHLY_BUDGET_SUCCESS_MESSAGE = "Successfully set monthly budget of: ";
    private static final String YEARLY_BUDGET_SUCCESS_MESSAGE = "Successfully set yearly budget of: ";

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
        double budget = CommandUtils.formatAmount(stringBudget, BUDGET_COMMAND_FORMAT);
        String timeRange = arguments[TIME_RANGE_INDEX].toLowerCase();
        addBudget(spendings, budget, timeRange);
    }

    private String[] extractArguments() throws WiagiMissingParamsException {
        String[] arguments = fullCommand.split(WHITESPACE, BUDGET_COMPULSORY_ARGUMENTS_LENGTH);
        if (arguments.length != BUDGET_COMPULSORY_ARGUMENTS_LENGTH) {
            throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER + BUDGET_COMMAND_FORMAT);
        }
        return arguments;
    }

    private void addBudget(SpendingList spendings, double budget, String timeRange) {
        String formatedBudget = Ui.formatPrintDouble(budget);
        switch (timeRange) {
        case DAILY:
            spendings.setDailyBudget(budget);
            Ui.printWithTab(DAILY_BUDGET_SUCCESS_MESSAGE + formatedBudget);
            break;
        case MONTHLY:
            spendings.setMonthlyBudget(budget);
            Ui.printWithTab(MONTHLY_BUDGET_SUCCESS_MESSAGE + formatedBudget);
            break;
        case YEARLY:
            spendings.setYearlyBudget(budget);
            Ui.printWithTab(YEARLY_BUDGET_SUCCESS_MESSAGE + formatedBudget);
            break;
        default:
            throw new WiagiInvalidInputException(INVALID_CATEGORY + BUDGET_COMMAND_FORMAT);
        }
    }
}

