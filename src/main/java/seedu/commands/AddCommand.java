package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiEmptyDescriptionException;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.*;

import java.util.ArrayList;
import java.util.Arrays;

public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final int DESCRIPTION_INDEX = 3;
    public static final int SPENDING_OR_INCOME_INDEX = 1;
    public static final String CORRECT_FORMAT = "Invalid input. " +
            "Please enter in the form: add [spending/income] [amount] " +
            "[description] {/YYYY-MM-DD/} {*tag*} {~recurrence~}";
    public static final String LINE_SEPARATOR_CORRECT_FORMAT =
            System.lineSeparator() + CORRECT_FORMAT;
    public static final int AMOUNT_INDEX = 2;

    private final String fullCommand;
    public AddCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        String[] userInputWords = splitByRegex(fullCommand, " ");
        String description = commandHandler(, );
        try {
            if (userInputWords.length < 4) {
                throw new WiagiInvalidInputException("Missing Parameters");
            }
            if (userInputWords[SPENDING_OR_INCOME_INDEX].equals("spending")) {
                addSpending(userInputWords, spendings);
            } else if (userInputWords[SPENDING_OR_INCOME_INDEX].equals("income")) {
                addIncome(userInputWords, incomes);
            } else {
                throw new WiagiInvalidInputException("No such category to add");
            }
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
            Ui.printWithTab(CORRECT_FORMAT);
        }
    }

    private void addSpending(String[] userInputWords, SpendingList spendings) {
        assert userInputWords[SPENDING_OR_INCOME_INDEX].equals("spending") : "command should be to add spending";
        try {
            Spending toAdd = new Spending(userInputWords, fullCommand);
            spendings.add(toAdd);
        } catch (WiagiInvalidInputException | WiagiMissingParamsException | WiagiEmptyDescriptionException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void addIncome(String[] userInputWords, IncomeList incomes) {
        assert userInputWords[SPENDING_OR_INCOME_INDEX].equals("income") : "command should be to add income";
        try {
            Income toAdd = new Income(userInputWords, fullCommand);
            incomes.add(toAdd);
        } catch (WiagiInvalidInputException | WiagiMissingParamsException | WiagiEmptyDescriptionException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    public static String[] splitByRegex(String fullCommand, String regex) {
        return fullCommand.split(regex);
    }

    public void commandHandler(IncomeList incomes, SpendingList spendings) throws WiagiInvalidInputException,
            WiagiEmptyDescriptionException {
        String commandWithoutDate = splitByRegex(fullCommand, "/")[0];
        String commandWithoutTag = splitByRegex(commandWithoutDate, "\\*")[0];
        String commandWithoutRecurrence = splitByRegex(commandWithoutTag, "~")[0];
        String[] commandWords = splitByRegex(commandWithoutRecurrence, " ");
        String[] splitDescription = Arrays.copyOfRange(commandWords, DESCRIPTION_INDEX, commandWords.length);
        String description = String.join(" ", splitDescription);
        if (splitDescription.length == 0) {
            throw new WiagiEmptyDescriptionException("Cannot find Description" +
                    LINE_SEPARATOR_CORRECT_FORMAT);
        }
        if (!isNumeric(commandWords[AMOUNT_INDEX])) {
            throw new WiagiInvalidInputException("Amount must be an integer" +
                    LINE_SEPARATOR_CORRECT_FORMAT);
        }
        if (Double.parseDouble(commandWords[AMOUNT_INDEX]) <= 0) {
            throw new WiagiInvalidInputException("Amount must be greater than zero" +
                    LINE_SEPARATOR_CORRECT_FORMAT);
        }
        if (commandWords[SPENDING_OR_INCOME_INDEX].equals("spending")) {
            addSpending(spendings, commandWords[AMOUNT_INDEX], description);
        } else if (commandWords[SPENDING_OR_INCOME_INDEX].equals("income")) {
            addIncome(incomes, commandWords[AMOUNT_INDEX], description);
        } else {
            throw new WiagiInvalidInputException("No such category to add" +
                    LINE_SEPARATOR_CORRECT_FORMAT);
        }
    }

    private <T extends Type> void addToList(ArrayList<T> arrList, int amount,
                                            String description){
        try {
            T toAdd = (T) new Type(fullCommand, amount, description);
            arrList.add(toAdd);
        }
        catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

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
