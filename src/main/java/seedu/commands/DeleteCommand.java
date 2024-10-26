package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiInvalidIndexException;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import java.util.ArrayList;

import static seedu.classes.Constants.DELETE_COMMAND_FORMAT;
import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.INDEX_NOT_INTEGER;
import static seedu.classes.Constants.INDEX_OUT_OF_BOUNDS;
import static seedu.classes.Constants.INVALID_CATEGORY;

public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    private final String fullCommand;
    public DeleteCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    // @@author wx-03
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        String[] arguments = fullCommand.split(" ");
        try {
            if (arguments.length < 3) {
                throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER
                        + DELETE_COMMAND_FORMAT);
            }
            if (arguments[1].equalsIgnoreCase("income")) {
                deleteEntry(arguments, incomes);
            } else if (arguments[1].equalsIgnoreCase("spending")) {
                deleteEntry(arguments, spendings);
            } else {
                throw new WiagiInvalidInputException(INVALID_CATEGORY + DELETE_COMMAND_FORMAT);
            }
        } catch (WiagiMissingParamsException | WiagiInvalidInputException | WiagiInvalidIndexException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private <T> boolean isOutOfBounds(int index, ArrayList<T> arrList) {
        return (index >= arrList.size() || index < 0);
    }

    private <T> void deleteEntry(String[] arguments, ArrayList<T> arrList) throws WiagiInvalidIndexException {
        int index = getIndex(arguments);
        if (isOutOfBounds(index, arrList)) {
            throw new WiagiInvalidIndexException(INDEX_OUT_OF_BOUNDS);
        }
        assert arrList.get(index) != null : "entry to delete cannot be null";
        arrList.remove(arrList.get(index));
        Ui.printWithTab("Successfully deleted!");
    }

    private int getIndex(String[] fullCommandArray) {
        try {
            int index = Integer.parseInt(fullCommandArray[2]);
            return index - 1;
        } catch (NumberFormatException e) {
            throw new WiagiInvalidInputException(INDEX_NOT_INTEGER + DELETE_COMMAND_FORMAT);
        }
    }
}
