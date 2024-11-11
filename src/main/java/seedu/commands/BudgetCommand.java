package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.IncomeList;
import seedu.type.SpendingList;
import seedu.classes.WiagiLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.classes.Constants.BUDGET_COMMAND_FORMAT;
import static seedu.classes.Constants.DAILY_BUDGET_SUCCESS_MESSAGE;
import static seedu.classes.Constants.ENTER_BUDGET_MESSAGE;
import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.INVALID_AMOUNT;
import static seedu.classes.Constants.MONTHLY_BUDGET_SUCCESS_MESSAGE;
import static seedu.classes.Constants.OVER_MAX_BUDGET_AMOUNT;
import static seedu.classes.Constants.INVALID_FIELD;
import static seedu.classes.Constants.MAX_LIST_TOTAL_AMOUNT;
import static seedu.classes.Constants.WHITESPACE;
import static seedu.classes.Constants.YEARLY_BUDGET_SUCCESS_MESSAGE;

/**
 * Represents a command to set a budget.
 */
public class BudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";
    private static final Logger LOGGER = WiagiLogger.logger;
    private static final int TIME_RANGE_INDEX = 1;
    private static final int BUDGET_AMOUNT_INDEX = 2;
    private static final int BUDGET_COMPULSORY_ARGUMENTS_LENGTH = 3;
    private static final String DAILY = "daily";
    private static final String MONTHLY = "monthly";
    private static final String YEARLY = "yearly";
    private static final String DAILY_BUDGET = "daily budget";
    private static final String MONTHLY_BUDGET = "monthly budget";
    private static final String YEARLY_BUDGET = "yearly budget";
    private static final String DUMMY_STRING = "";
    private static final double DUMMY_AMOUNT = 0.0;
    private static final double UNINITIALISED_BUDGET = 0.0;
    private static final double MINIMUM_AMOUNT_ENTERED = 0.0;


    private final String fullCommand;


    /**
     * Constructs a BudgetCommand with the specified full command.
     *
     * @param fullCommand The full command string.
     */
    public BudgetCommand(String fullCommand) {
        assert fullCommand != null : "Full command cannot be null";
        assert !fullCommand.trim().isEmpty() : "Full command cannot be empty";
        LOGGER.log(Level.INFO, "Creating new BudgetCommand with command: {0}", fullCommand);
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
        assert incomes != null : "Income list cannot be null";
        assert spendings != null : "Spending list cannot be null";
        LOGGER.log(Level.INFO, "Executing budget command");
        try {
            handleCommand(spendings);
            LOGGER.log(Level.INFO, "Budget command executed successfully");
        } catch (WiagiMissingParamsException e) {
            LOGGER.log(Level.WARNING, "Missing parameters in budget command", e);
            Ui.printWithTab(e.getMessage());
        } catch (WiagiInvalidInputException e) {
            LOGGER.log(Level.WARNING, "Invalid input in budget command", e);
            Ui.printWithTab(e.getMessage());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid number format in budget command", e);
            Ui.printWithTab(e.getMessage());
        }
    }

    /**
     * Initialises the daily, monthly and yearly budgets.
     * @param spendings
     */
    public static void initialiseBudget(SpendingList spendings) {
        initialiseDailyBudget(spendings);
        initialiseMonthlyBudget(spendings);
        initialiseYearlyBudget(spendings);
    }

    private void handleCommand(SpendingList spendings) throws WiagiMissingParamsException {
        String[] arguments = extractArguments();
        String stringBudget = arguments[BUDGET_AMOUNT_INDEX];
        double budget = CommandUtils.roundAmount(stringBudget, BUDGET_COMMAND_FORMAT);
        String timeRange = arguments[TIME_RANGE_INDEX].toLowerCase();

        LOGGER.log(Level.INFO, "Setting {0} budget to {1}", new Object[]{timeRange, budget});
        setBudget(spendings, budget, timeRange);
    }

    private String[] extractArguments() throws WiagiMissingParamsException {
        LOGGER.log(Level.FINE, "Extracting arguments from command: {0}", fullCommand);
        String[] arguments = fullCommand.split(WHITESPACE, BUDGET_COMPULSORY_ARGUMENTS_LENGTH);
        if (arguments.length != BUDGET_COMPULSORY_ARGUMENTS_LENGTH) {
            throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER + BUDGET_COMMAND_FORMAT);
        }
        return arguments;
    }

    private void setBudget(SpendingList spendings, double budget, String timeRange) {
        String formatedBudget = Ui.formatPrintDouble(budget);
        switch (timeRange) {
        case DAILY:
            checkUserBudgetEntered(DUMMY_AMOUNT, budget, DAILY_BUDGET, MONTHLY_BUDGET);
            checkUserBudgetEntered(budget, spendings.getMonthlyBudget(), DAILY_BUDGET, MONTHLY_BUDGET);
            spendings.setDailyBudget(budget);
            Ui.printWithTab(DAILY_BUDGET_SUCCESS_MESSAGE + formatedBudget);
            break;
        case MONTHLY:
            checkUserBudgetEntered(budget, spendings.getYearlyBudget(), MONTHLY_BUDGET,YEARLY_BUDGET);
            checkUserBudgetEntered(spendings.getDailyBudget(), budget, DAILY_BUDGET,MONTHLY_BUDGET);
            spendings.setMonthlyBudget(budget);
            Ui.printWithTab(MONTHLY_BUDGET_SUCCESS_MESSAGE + formatedBudget);
            break;
        case YEARLY:
            checkUserBudgetEntered(spendings.getMonthlyBudget(), budget, MONTHLY_BUDGET, YEARLY_BUDGET);
            spendings.setYearlyBudget(budget);
            Ui.printWithTab(YEARLY_BUDGET_SUCCESS_MESSAGE + formatedBudget);
            break;
        default:
            throw new WiagiInvalidInputException(INVALID_FIELD + BUDGET_COMMAND_FORMAT);
        }
    }

    private static void checkUserBudgetEntered(double smallerBudget, double biggerBudget, String smallerBudgetType,
            String biggerBudgetType) {
        if (biggerBudget <= MINIMUM_AMOUNT_ENTERED) {
            throw new WiagiInvalidInputException(INVALID_AMOUNT + ENTER_BUDGET_MESSAGE);
        }
        if (biggerBudget > MAX_LIST_TOTAL_AMOUNT) {
            throw new WiagiInvalidInputException(OVER_MAX_BUDGET_AMOUNT + ENTER_BUDGET_MESSAGE);
        }
        if (biggerBudget < smallerBudget) {
            throw new WiagiInvalidInputException("Your " + smallerBudgetType + " should not be larger than " +
                    biggerBudgetType + "! " + ENTER_BUDGET_MESSAGE);
        }
    }

    private static void initialiseDailyBudget(SpendingList spendings) {
        Ui.initialiseDailyBudgetMessage();
        while (spendings.getDailyBudget() == UNINITIALISED_BUDGET) {
            try {
                String userDailyBudget = Ui.readCommand();
                double amount = CommandUtils.roundAmount(userDailyBudget, DUMMY_STRING);
                checkUserBudgetEntered(DUMMY_AMOUNT, amount, DUMMY_STRING, DAILY_BUDGET);
                spendings.setDailyBudget(amount);
            } catch (WiagiInvalidInputException e) {
                Ui.printWithTab(e.getMessage());
            }
        }
    }

    private static void initialiseMonthlyBudget(SpendingList spendings) {
        Ui.initialiseMonthlyBudgetMessage();
        while (spendings.getMonthlyBudget() == UNINITIALISED_BUDGET) {
            try {
                String userMonthlyBudget = Ui.readCommand();
                double amount = CommandUtils.roundAmount(userMonthlyBudget, DUMMY_STRING);
                checkUserBudgetEntered(spendings.getDailyBudget(), amount, DAILY_BUDGET, MONTHLY_BUDGET);
                spendings.setMonthlyBudget(amount);
            } catch (WiagiInvalidInputException e){
                Ui.printWithTab(e.getMessage());
            }
        }
    }

    private static void initialiseYearlyBudget(SpendingList spendings) {
        Ui.initialiseYearlyBudgetMessage();
        while (spendings.getYearlyBudget() == UNINITIALISED_BUDGET) {
            try {
                String userYearlyBudget = Ui.readCommand();
                double amount = CommandUtils.roundAmount(userYearlyBudget, DUMMY_STRING);
                checkUserBudgetEntered(spendings.getMonthlyBudget(), amount, MONTHLY_BUDGET, YEARLY_BUDGET);
                spendings.setYearlyBudget(amount);
            } catch (WiagiInvalidInputException e) {
                Ui.printWithTab(e.getMessage());
            }
        }
    }
}

