package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiInvalidIndexException;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;
import seedu.type.EntryType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static seedu.classes.Constants.FIND_COMMAND_FORMAT;
import static seedu.classes.Constants.FIND_RANGE_DIVIDER;
import static seedu.classes.Constants.INCOME;
import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.INVALID_AMOUNT_RANGE;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.INVALID_DATE_RANGE;
import static seedu.classes.Constants.INVALID_FIELD;
import static seedu.classes.Constants.INVALID_FIND_RANGE_DIVIDER_FORMAT;
import static seedu.classes.Constants.LONG_FIND_FROM_VALUE;
import static seedu.classes.Constants.LONG_FIND_SPECIFIC_VALUE;
import static seedu.classes.Constants.LONG_FIND_TO_VALUE;
import static seedu.classes.Constants.WHITESPACE;
import static seedu.classes.Constants.SPENDING;

public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    private static final int LIST_TYPE_INDEX = 1;
    private static final int FIELD_INDEX = 2;
    private static final int VALUE_TO_FIND_INDEX = 3;
    private static final int FIND_ARGUMENTS_LENGTH = 4;
    private static final int LENGTH_OF_FIND_RANGE_DIVIDER = 2;
    private static final String AMOUNT_FIELD = "amount";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String DATE_FIELD = "date";

    private final String fullCommand;

    /**
     * Represents a command to find entries in the financial records.
     * This command allows users to search for specific entries in either
     * the IncomeList or SpendingList based on a specified field (amount, description, or date)
     * and a value to find.
     */
    public FindCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * Execute finding with the given arguments
     *
     * @param incomes   list of incomes in the application
     * @param spendings list of spendings in the application
     */
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        assert incomes != null;
        assert spendings != null;
        try {
            handleCommand(incomes, spendings);
        } catch (WiagiMissingParamsException | WiagiInvalidInputException | WiagiInvalidIndexException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void handleCommand(IncomeList incomes, SpendingList spendings)
            throws WiagiMissingParamsException, WiagiInvalidIndexException {
        String[] arguments = extractArguments();
        String typeOfList = arguments[LIST_TYPE_INDEX];
        switch (typeOfList) {
        case INCOME:
            ArrayList<Income> incomeFindResults = findList(arguments, incomes);
            Ui.printFindResults(incomeFindResults, incomes);
            break;
        case SPENDING:
            ArrayList<Spending> spendingFindResults = findList(arguments, spendings);
            Ui.printFindResults(spendingFindResults, spendings);
            break;
        default:
            throw new WiagiInvalidInputException(INVALID_CATEGORY + FIND_COMMAND_FORMAT);
        }
    }

    private String[] extractArguments() throws WiagiMissingParamsException {
        String[] arguments = fullCommand.split(WHITESPACE, FIND_ARGUMENTS_LENGTH);
        if (arguments.length < FIND_ARGUMENTS_LENGTH) {
            throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER + FIND_COMMAND_FORMAT);
        }
        return arguments;
    }

    private <T extends EntryType> ArrayList<T> findList(String[] arguments, ArrayList<T> list) {
        String findValue = arguments[VALUE_TO_FIND_INDEX];
        assert !findValue.isEmpty() : "Find value should not be empty";
        String field = arguments[FIELD_INDEX];
        switch (field) {
        case AMOUNT_FIELD:
            return getMatchingAmount(findValue, list);
        case DESCRIPTION_FIELD:
            return getMatchingDescription(findValue, list);
        case DATE_FIELD:
            return getMatchingDate(findValue, list);
        default:
            throw new WiagiInvalidInputException(INVALID_FIELD + FIND_COMMAND_FORMAT);
        }
    }

    private <T extends EntryType> ArrayList<T> getMatchingAmount(String findValue, ArrayList<T> list) {
        double lower;
        double upper;
        if (findValue.contains(FIND_RANGE_DIVIDER)) { // range
            String[] rangeValues = extractRangeValues(findValue);
            lower = CommandUtils.formatAmount(rangeValues[0], FIND_COMMAND_FORMAT);
            upper = CommandUtils.formatAmount(rangeValues[1], FIND_COMMAND_FORMAT);
            if (upper < lower) {
                throw new WiagiInvalidInputException(INVALID_AMOUNT_RANGE);
            }
        } else { // exact
            if (findValue.contains(WHITESPACE)) {
                throw new WiagiInvalidInputException(LONG_FIND_SPECIFIC_VALUE + FIND_COMMAND_FORMAT);
            }
            lower = upper = CommandUtils.formatAmount(findValue, FIND_COMMAND_FORMAT);
        }
        return list.stream()
                .filter(entry -> entry.getAmount() >= lower && entry.getAmount() <= upper)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    private <T extends EntryType> ArrayList<T> getMatchingDescription(String findValue, ArrayList<T> list) {
        return list.stream()
                .filter(entry -> entry.getDescription().contains(findValue))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private <T extends EntryType> ArrayList<T> getMatchingDate(String findValue, ArrayList<T> list) {
        LocalDate lower;
        LocalDate upper;
        if (findValue.contains(FIND_RANGE_DIVIDER)) {
            String[] rangeValues = extractRangeValues(findValue);
            lower = CommandUtils.formatDate(rangeValues[0], FIND_COMMAND_FORMAT);
            upper = CommandUtils.formatDate(rangeValues[1], FIND_COMMAND_FORMAT);
            if (lower.isAfter(upper)) {
                throw new WiagiInvalidInputException(INVALID_DATE_RANGE);
            }
        } else {
            if (findValue.contains(WHITESPACE)) {
                throw new WiagiInvalidInputException(LONG_FIND_SPECIFIC_VALUE + FIND_COMMAND_FORMAT);
            }
            lower = upper = CommandUtils.formatDate(findValue, FIND_COMMAND_FORMAT);
        }
        return list.stream()
                .filter(entry -> entry.getDate().isAfter(lower.minusDays(1))
                        && entry.getDate().isBefore(upper.plusDays(1)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static String[] extractRangeValues(String findValue) {
        if (!findValue.contains(WHITESPACE + FIND_RANGE_DIVIDER + WHITESPACE)) {
            throw new WiagiInvalidInputException(INVALID_FIND_RANGE_DIVIDER_FORMAT + FIND_COMMAND_FORMAT);
        }
        int indexOfFindRangeDivider = findValue.indexOf(FIND_RANGE_DIVIDER);
        String lowerString = findValue.substring(0, indexOfFindRangeDivider).trim();
        String upperString = findValue.substring(indexOfFindRangeDivider + LENGTH_OF_FIND_RANGE_DIVIDER).trim();
        if (lowerString.contains(WHITESPACE)) {
            throw new WiagiInvalidInputException(LONG_FIND_FROM_VALUE + FIND_COMMAND_FORMAT);
        }
        if (upperString.contains(WHITESPACE)) {
            throw new WiagiInvalidInputException(LONG_FIND_TO_VALUE + FIND_COMMAND_FORMAT);
        }
        return new String[]{lowerString, upperString};
    }
}
