package seedu.commands;
import seedu.classes.Ui;
import seedu.exception.WiagiEmptyDescriptionException;
import seedu.exception.WiagiInvalidInputException;
import seedu.recurrence.Recurrence;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.classes.Constants.ADD_COMMAND_FORMAT;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.MISSING_AMOUNT;
import static seedu.classes.Constants.MISSING_DESCRIPTION;
import static seedu.classes.Constants.SPACE_REGEX;
import static seedu.classes.Constants.INCOME;
import static seedu.classes.Constants.SPENDING;

public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    private static final int DESCRIPTION_INDEX = 3;
    private static final int LIST_TYPE_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final int ADD_COMPULSORY_ARGUMENTS_LENGTH = 4;
    private static final String OPTIONAL_ARGUMENTS_REGEX = "^(.*?)(?:[/~*].*)?$";

    private final String fullCommand;
    public AddCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        assert incomes != null;
        assert spendings != null;
        try {
            handleCommand(incomes, spendings);
        } catch (WiagiInvalidInputException | WiagiEmptyDescriptionException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void handleCommand(IncomeList incomes, SpendingList spendings)
            throws WiagiInvalidInputException, WiagiEmptyDescriptionException {

        String[] arguments = extractArguments(); // [add] [type] [amount] [others]
        String typeOfList = arguments[LIST_TYPE_INDEX];
        if (!(typeOfList.equals(SPENDING) || typeOfList.equals(INCOME))) {
            throw new WiagiInvalidInputException(INVALID_CATEGORY + ADD_COMMAND_FORMAT);
        }

        String stringAmount = arguments[AMOUNT_INDEX];
        double amount = CommandUtils.formatAmount(stringAmount, ADD_COMMAND_FORMAT);
        assert amount > 0 : "Amount should be greater than zero";

        String descriptionAndOptionalArguments = arguments[DESCRIPTION_INDEX];
        String description = extractDescription(descriptionAndOptionalArguments);
        assert description != null && !description.isEmpty() : "Description should not be null or empty";

        String optionalArguments = descriptionAndOptionalArguments.substring(description.length());

        if (typeOfList.equals(SPENDING)) {
            addSpending(spendings, amount, description, optionalArguments);
        } else {
            addIncome(incomes, amount, description, optionalArguments);
        }
    }

    private String[] extractArguments() {
        String[] arguments = fullCommand.split(SPACE_REGEX, ADD_COMPULSORY_ARGUMENTS_LENGTH);
        if (arguments.length == AMOUNT_INDEX) {
            throw new WiagiInvalidInputException(MISSING_AMOUNT + ADD_COMMAND_FORMAT);
        } else if (arguments.length == DESCRIPTION_INDEX) {
            throw new WiagiInvalidInputException(MISSING_DESCRIPTION + ADD_COMMAND_FORMAT);
        }
        return arguments;
    }

    private String extractDescription(String descriptionAndOptionalArguments) {
        Pattern pattern = Pattern.compile(OPTIONAL_ARGUMENTS_REGEX);
        Matcher matcher = pattern.matcher(descriptionAndOptionalArguments);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return descriptionAndOptionalArguments.trim();  // Return the original if no match is found
    }

    private void addSpending(SpendingList spendings, double amount, String description, String optionalArguments) {
        try {
            Spending toAdd = new Spending(optionalArguments, amount, description);
            spendings.add(toAdd);
            Recurrence.checkRecurrenceBackLog(toAdd, spendings);
            spendings.checkOverspend();
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void addIncome(IncomeList incomes, double amount, String description, String optionalArguments) {
        try {
            Income toAdd = new Income(optionalArguments, amount, description);
            incomes.add(toAdd);
            Recurrence.checkRecurrenceBackLog(toAdd, incomes);
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }
}
