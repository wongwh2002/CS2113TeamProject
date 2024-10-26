package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiInvalidIndexException;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.IncomeList;
import seedu.type.SpendingList;
import seedu.type.Type;

import java.util.ArrayList;

import static seedu.classes.Constants.EDIT_COMMAND_FORMAT;
import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.INDEX_NOT_INTEGER;
import static seedu.classes.Constants.INDEX_OUT_OF_BOUNDS;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.INVALID_FIELD;

public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    private final String fullCommand;

    public EditCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * Execute editing with the given arguments
     *
     * @param incomes   list of incomes in the application
     * @param spendings list of spendings in the application
     */
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        assert incomes != null;
        assert spendings != null;
        String[] arguments = fullCommand.split(" ", 5);
        try {
            if (arguments.length < 5) {
                throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER
                        + EDIT_COMMAND_FORMAT);
            }
            String entryType = arguments[1];
            if (entryType.equals("spending")) {
                editList(arguments, spendings);
            } else if (entryType.equals("income")) {
                editList(arguments, incomes);
            } else {
                throw new WiagiInvalidInputException(INVALID_CATEGORY + EDIT_COMMAND_FORMAT);
            }
        } catch (WiagiMissingParamsException | WiagiInvalidInputException | WiagiInvalidIndexException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private <T extends ArrayList<? extends Type>> void editList(String[] arguments, T list)
            throws WiagiInvalidIndexException {
        try {
            int indexOfEntryToEdit = getIndex(arguments);
            Type entryToEdit = list.get(indexOfEntryToEdit);
            String newValue = arguments[4];
            String category = arguments[3];
            switch (category) {
            case "amount":
                entryToEdit.editAmount(newValue);
                break;
            case "description":
                entryToEdit.editDescription(newValue);
                break;
            case "date":
                entryToEdit.editDate(newValue);
                break;
            case "tag":
                entryToEdit.editTag(newValue);
                break;
            default:
                throw new WiagiInvalidInputException(INVALID_FIELD + EDIT_COMMAND_FORMAT);
            }
            Ui.printWithTab("Edit Successful!");
        } catch (IndexOutOfBoundsException e) {
            throw new WiagiInvalidIndexException(INDEX_OUT_OF_BOUNDS);
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private int getIndex(String[] fullCommandArray) throws WiagiInvalidIndexException {
        try {
            int index = Integer.parseInt(fullCommandArray[2]);
            return index - 1;
        } catch (NumberFormatException e) {
            throw new WiagiInvalidIndexException(INDEX_NOT_INTEGER);
        }
    }
}
