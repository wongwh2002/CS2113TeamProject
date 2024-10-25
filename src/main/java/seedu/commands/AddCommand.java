package seedu.commands;

import seedu.classes.Constants;
import seedu.classes.Ui;
import seedu.exception.WiagiEmptyDescriptionException;
import seedu.exception.WiagiInvalidInputException;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.util.Arrays;

public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final int DESCRIPTION_INDEX = 3;
    public static final int SPENDING_OR_INCOME_INDEX = 1;
    public static final int AMOUNT_INDEX = 2;
    public static final String SPENDING = "spending";
    public static final String INCOME = "income";

    private final String fullCommand;
    public AddCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        try {
            commandHandler(incomes, spendings);
        } catch (WiagiInvalidInputException | WiagiEmptyDescriptionException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    //@@author wongwh2002
    public static String[] splitByRegex(String fullCommand, String regex) {
        return fullCommand.split(regex);
    }

    //@@author wongwh2002
    public void commandHandler(IncomeList incomes, SpendingList spendings) throws
            WiagiInvalidInputException, WiagiEmptyDescriptionException {

        assert fullCommand != null : "fullCommand should not be null";
        String commandWithoutDate = splitByRegex(fullCommand, "/")[0];
        String commandWithoutTag = splitByRegex(commandWithoutDate, "\\*")[0];
        String commandWithoutRecurrence = splitByRegex(commandWithoutTag, "~")[0];
        String[] commandWords = splitByRegex(commandWithoutRecurrence, " ");

        if (!(commandWords[SPENDING_OR_INCOME_INDEX].equals(SPENDING) ||
                commandWords[SPENDING_OR_INCOME_INDEX].equals(INCOME))) {
            throw new WiagiInvalidInputException(Constants.INVALID_CATEGORY + Constants.ADD_COMMAND_FORMAT);
        }

        if (commandWords.length == 2) {
            throw new WiagiInvalidInputException(Constants.MISSING_AMOUNT + Constants.ADD_COMMAND_FORMAT);
        }

        if (!isNumeric(commandWords[AMOUNT_INDEX])) {
            throw new WiagiInvalidInputException(Constants.INDEX_NOT_INTEGER + Constants.ADD_COMMAND_FORMAT);
        }
        double amount = Double.parseDouble(commandWords[AMOUNT_INDEX]);
        amount = Math.round(amount * 100.0) / 100.0; //round to 2dp
        if (amount <= 0) {
            throw new WiagiInvalidInputException(Constants.INVALID_AMOUNT + Constants.ADD_COMMAND_FORMAT);
        }
        assert amount > 0 : "Amount should be greater than zero";

        String[] splitDescription = Arrays.copyOfRange(commandWords, DESCRIPTION_INDEX, commandWords.length);
        String description = String.join(" ", splitDescription);
        if (splitDescription.length == 0) {
            throw new WiagiEmptyDescriptionException(Constants.MISSING_DESCRIPTION + Constants.ADD_COMMAND_FORMAT);
        }
        assert description != null && !description.isEmpty() : "Description should not be null or empty";

        if (commandWords[SPENDING_OR_INCOME_INDEX].equals(SPENDING)) {
            addSpending(spendings, amount, description);
        } else {
            addIncome(incomes, amount, description);
        }
    }

    private void addSpending(SpendingList spendings, double amount, String description) {
        try {
            Spending toAdd = new Spending(fullCommand, amount, description);
            spendings.add(toAdd);
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void addIncome(IncomeList incomes, double amount, String description) {
        try {
            Income toAdd = new Income(fullCommand, amount, description);
            incomes.add(toAdd);
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    //@@author wongwh2002
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
