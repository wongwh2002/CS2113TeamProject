package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiEmptyDescriptionException;
import seedu.exception.WiagiInvalidInputException;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.util.Arrays;

import static seedu.classes.Constants.ADD_COMMAND_INE_SEPARATOR_CORRECT_FORMAT;

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
        String commandWithoutDate = splitByRegex(fullCommand, "/")[0];
        String commandWithoutTag = splitByRegex(commandWithoutDate, "\\*")[0];
        String commandWithoutRecurrence = splitByRegex(commandWithoutTag, "~")[0];
        String[] commandWords = splitByRegex(commandWithoutRecurrence, " ");

        if (!(commandWords[SPENDING_OR_INCOME_INDEX].equals(SPENDING) ||
                commandWords[SPENDING_OR_INCOME_INDEX].equals(INCOME))) {
            throw new WiagiInvalidInputException("No such category to add" +
                    ADD_COMMAND_INE_SEPARATOR_CORRECT_FORMAT);
        }

        if (commandWords.length == 2) {
            throw new WiagiInvalidInputException("Missing parameters" +
                    ADD_COMMAND_INE_SEPARATOR_CORRECT_FORMAT);
        }

        if (!isNumeric(commandWords[AMOUNT_INDEX])) {
            throw new WiagiInvalidInputException("Amount must be an integer" +
                    ADD_COMMAND_INE_SEPARATOR_CORRECT_FORMAT);
        }
        int amount = Integer.parseInt(commandWords[AMOUNT_INDEX]);
        if (amount <= 0) {
            throw new WiagiInvalidInputException("Amount must be greater than zero" +
                    ADD_COMMAND_INE_SEPARATOR_CORRECT_FORMAT);
        }

        String[] splitDescription = Arrays.copyOfRange(commandWords, DESCRIPTION_INDEX, commandWords.length);
        String description = String.join(" ", splitDescription);
        if (splitDescription.length == 0) {
            throw new WiagiEmptyDescriptionException("Cannot find Description" +
                    ADD_COMMAND_INE_SEPARATOR_CORRECT_FORMAT);
        }

        if (commandWords[SPENDING_OR_INCOME_INDEX].equals(SPENDING)) {
            addSpending(spendings, amount, description);
        } else {
            addIncome(incomes, amount, description);
        }
    }

    private void addSpending(SpendingList spendings, int amount, String description) {
        try {
            Spending toAdd = new Spending(fullCommand, amount, description);
            spendings.add(toAdd);
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void addIncome(IncomeList incomes, int amount, String description) {
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
