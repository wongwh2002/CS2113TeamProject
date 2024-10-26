package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiEmptyDescriptionException;
import seedu.exception.WiagiInvalidInputException;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import static seedu.classes.Constants.ADD_COMMAND_FORMAT;
import static seedu.classes.Constants.AMOUNT_NOT_NUMBER;
import static seedu.classes.Constants.INVALID_AMOUNT;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.MISSING_AMOUNT;
import static seedu.classes.Constants.MISSING_DESCRIPTION;

public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    private static final int DESCRIPTION_INDEX = 3;
    private static final int TYPE_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final int ADD_COMPULSORY_ARGUMENTS_LENGTH = 4;
    private static final String SPENDING = "spending";
    private static final String INCOME = "income";

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

    public void commandHandler(IncomeList incomes, SpendingList spendings) throws
            WiagiInvalidInputException, WiagiEmptyDescriptionException {
        String[] compulsoryAndOptionalArguments = fullCommand.split(" ", ADD_COMPULSORY_ARGUMENTS_LENGTH + 1);
        if (compulsoryAndOptionalArguments.length == AMOUNT_INDEX) {
            throw new WiagiInvalidInputException(MISSING_AMOUNT + ADD_COMMAND_FORMAT);
        } else if (compulsoryAndOptionalArguments.length == DESCRIPTION_INDEX) {
            throw new WiagiInvalidInputException(MISSING_DESCRIPTION + ADD_COMMAND_FORMAT);
        }

        String type = compulsoryAndOptionalArguments[TYPE_INDEX];
        if (!(type.equals(SPENDING) || type.equals(INCOME))) {
            throw new WiagiInvalidInputException(INVALID_CATEGORY + ADD_COMMAND_FORMAT);
        }

        String stringAmount = compulsoryAndOptionalArguments[AMOUNT_INDEX];
        double amount = formatAmount(stringAmount);
        assert amount > 0 : "Amount should be greater than zero";

        String description = compulsoryAndOptionalArguments[DESCRIPTION_INDEX];
        assert description != null && !description.isEmpty() : "Description should not be null or empty";

        String optionalArguments;
        if (compulsoryAndOptionalArguments.length == ADD_COMPULSORY_ARGUMENTS_LENGTH) {
            optionalArguments = "";
        } else {
            optionalArguments = compulsoryAndOptionalArguments[ADD_COMPULSORY_ARGUMENTS_LENGTH];
        }

        if (type.equals(SPENDING)) {
            addSpending(spendings, amount, description, optionalArguments);
        } else {
            addIncome(incomes, amount, description, optionalArguments);
        }
    }

    private void addSpending(SpendingList spendings, double amount, String description, String optionalArguments) {
        try {
            Spending toAdd = new Spending(optionalArguments, amount, description);
            spendings.add(toAdd);
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void addIncome(IncomeList incomes, double amount, String description, String optionalArguments) {
        try {
            Income toAdd = new Income(optionalArguments, amount, description);
            incomes.add(toAdd);
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    //@@author TPH777
    private static double formatAmount(String stringAmount) {
        try {
            double doubleAmount = Double.parseDouble(stringAmount);
            if (doubleAmount <= 0) {
                throw new WiagiInvalidInputException(INVALID_AMOUNT + ADD_COMMAND_FORMAT);
            }
            return Math.round(doubleAmount * 100.0) / 100.0; //round to 2dp
        } catch (NumberFormatException nfe) {
            throw new WiagiInvalidInputException(AMOUNT_NOT_NUMBER + ADD_COMMAND_FORMAT);
        }
    }
}
