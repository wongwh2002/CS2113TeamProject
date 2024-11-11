package seedu.commands;
import seedu.classes.Ui;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.recurrence.Recurrence;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.classes.Constants.ADD_COMMAND_FORMAT;
import static seedu.classes.Constants.EMPTY_STRING;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.INVALID_DESCRIPTION_CHARACTERS_IN_ADD;
import static seedu.classes.Constants.OVER_MAX_LIST_AMOUNT_FOR_ADD;
import static seedu.classes.Constants.MAX_LIST_TOTAL_AMOUNT;
import static seedu.classes.Constants.MISSING_AMOUNT;
import static seedu.classes.Constants.MISSING_AMOUNT_AND_DESCRIPTION;
import static seedu.classes.Constants.MISSING_AMOUNT_DESCRIPTION_CATEGORY;
import static seedu.classes.Constants.MISSING_DESCRIPTION;
import static seedu.classes.Constants.RESTRICT_CHARACTER;
import static seedu.classes.Constants.WHITESPACE;
import static seedu.classes.Constants.INCOME;
import static seedu.classes.Constants.SPENDING;

public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    private static final int DESCRIPTION_INDEX = 3;
    private static final int LIST_TYPE_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final int ADD_COMPULSORY_ARGUMENTS_LENGTH = 4;
    private static final String OPTIONAL_ARGUMENTS_REGEX = "^(.*?)([/~*].*)?$";

    private final String fullCommand;
    public AddCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * Executes an add command with the given arguments
     * @param incomes   list of incomes in the application
     * @param spendings list of spendings in the application
     */
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        assert incomes != null;
        assert spendings != null;
        assert fullCommand.startsWith("add");
        try {
            handleCommand(incomes, spendings);
        } catch (WiagiInvalidInputException | WiagiMissingParamsException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void handleCommand(IncomeList incomes, SpendingList spendings)
            throws WiagiInvalidInputException, WiagiMissingParamsException {

        // Split full command into compulsory and optional strings
        String compulsoryString = splitCommand(fullCommand)[0];
        String optionalString = splitCommand(fullCommand)[1].trim().concat(WHITESPACE);

        // Split compulsory arguments into array of max size 4 with whitespace as delimiter
        // [add] [category] [amount] [description...]
        String[] compulsoryArguments = extractArguments(compulsoryString);

        // Check that command length greater than 1
        if (compulsoryArguments.length <= 1) {
            throw new WiagiMissingParamsException(MISSING_AMOUNT_DESCRIPTION_CATEGORY + ADD_COMMAND_FORMAT);
        }
        // Check that category is correct
        String typeOfList = compulsoryArguments[LIST_TYPE_INDEX];
        if (!(typeOfList.equals(SPENDING) || typeOfList.equals(INCOME))) {
            throw new WiagiInvalidInputException(INVALID_CATEGORY + ADD_COMMAND_FORMAT);
        }

        // Check that amount, description are present
        if (compulsoryArguments.length == AMOUNT_INDEX) {
            // Command is "add {$CATEGORY}"
            throw new WiagiMissingParamsException(MISSING_AMOUNT_AND_DESCRIPTION + ADD_COMMAND_FORMAT);
        } else if (compulsoryArguments.length == DESCRIPTION_INDEX) {
            // Either amount or description is missing
            if (isDouble(compulsoryArguments[AMOUNT_INDEX])) {
                throw new WiagiMissingParamsException(MISSING_DESCRIPTION + ADD_COMMAND_FORMAT);
            } else {
                throw new WiagiMissingParamsException(MISSING_AMOUNT + ADD_COMMAND_FORMAT);
            }
        }

        double amount;
        if (isDouble(compulsoryArguments[AMOUNT_INDEX])) {
            amount = CommandUtils.formatAmount(compulsoryArguments[AMOUNT_INDEX], ADD_COMMAND_FORMAT);
        } else if (isDouble(compulsoryArguments[DESCRIPTION_INDEX])) {
            // Wrong format
            throw new WiagiInvalidInputException(ADD_COMMAND_FORMAT);
        } else {
            throw new WiagiMissingParamsException(MISSING_AMOUNT + ADD_COMMAND_FORMAT);
        }

        String description = compulsoryArguments[DESCRIPTION_INDEX];
        if (description.matches(RESTRICT_CHARACTER)) {
            throw new WiagiInvalidInputException(INVALID_DESCRIPTION_CHARACTERS_IN_ADD);
        }

        if (typeOfList.equals(SPENDING)) {
            addSpending(spendings, amount, description, optionalString);
        } else {
            addIncome(incomes, amount, description, optionalString);
        }
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private String[] extractArguments(String commandString) {
        return commandString.split(WHITESPACE, ADD_COMPULSORY_ARGUMENTS_LENGTH);
    }

    private String[] splitCommand (String fullCommand) {
        Pattern pattern = Pattern.compile(OPTIONAL_ARGUMENTS_REGEX);
        Matcher matcher = pattern.matcher(fullCommand);
        if (matcher.find()) {
            String compulsoryString = matcher.group(1).trim();
            String optionalString = matcher.group(2);
            if (optionalString == null) {
                optionalString = EMPTY_STRING;
            } else {
                optionalString = optionalString.trim();
            }
            return new String[]{compulsoryString, optionalString};
        }
        return new String[] {fullCommand.trim(), EMPTY_STRING};
    }

    private void addSpending(SpendingList spendings, double amount, String description, String optionalArguments) {
        try {
            if (amount + spendings.getTotal() > MAX_LIST_TOTAL_AMOUNT) {
                throw new WiagiInvalidInputException(OVER_MAX_LIST_AMOUNT_FOR_ADD);
            }
            Spending toAdd = new Spending(optionalArguments, amount, description);
            spendings.add(toAdd);
            assert spendings.getTotal() < MAX_LIST_TOTAL_AMOUNT;
            Recurrence.checkRecurrenceBackLog(toAdd, spendings);
            spendings.checkOverspend();
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void addIncome(IncomeList incomes, double amount, String description, String optionalArguments) {
        try {
            if (amount + incomes.getTotal() > MAX_LIST_TOTAL_AMOUNT) {
                throw new WiagiInvalidInputException(OVER_MAX_LIST_AMOUNT_FOR_ADD);
            }
            Income toAdd = new Income(optionalArguments, amount, description);
            incomes.add(toAdd);
            assert incomes.getTotal() < MAX_LIST_TOTAL_AMOUNT;
            Recurrence.checkRecurrenceBackLog(toAdd, incomes);
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }
}
