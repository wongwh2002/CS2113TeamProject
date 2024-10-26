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
import static seedu.classes.Constants.SPACE_REGEX;
import static seedu.classes.Constants.INCOME;
import static seedu.classes.Constants.SPENDING;

public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    private static final int TYPE_INDEX = 1;
    private static final int INDEX_OF_ENTRY_INDEX = 2;
    private static final int CATEGORY_INDEX = 3;
    private static final int NEW_VALUE_INDEX = 4;
    private static final int EDIT_COMPULSORY_ARGUMENTS_LENGTH = 5;

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
        try {
            commandHandler(incomes, spendings);
        } catch (WiagiMissingParamsException | WiagiInvalidInputException | WiagiInvalidIndexException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void commandHandler(IncomeList incomes, SpendingList spendings)
            throws WiagiMissingParamsException, WiagiInvalidIndexException {
        String[] arguments = extractArguments();
        String type = arguments[TYPE_INDEX];
        if (!(type.equals(SPENDING) || type.equals(INCOME))) {
            throw new WiagiInvalidInputException(INVALID_CATEGORY + EDIT_COMMAND_FORMAT);
        }
        switch (type) {
        case INCOME:
            editList(arguments, incomes);
            break;
        case SPENDING:
            editList(arguments, spendings);
            break;
        default:
            throw new WiagiInvalidInputException(INVALID_CATEGORY + EDIT_COMMAND_FORMAT);
        }
    }

    private String[] extractArguments() throws WiagiMissingParamsException {
        String[] arguments = fullCommand.split(SPACE_REGEX, EDIT_COMPULSORY_ARGUMENTS_LENGTH);
        if (arguments.length < EDIT_COMPULSORY_ARGUMENTS_LENGTH) {
            throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER + EDIT_COMMAND_FORMAT);
        }
        return arguments;
    }

    private <T extends ArrayList<? extends Type>> void editList(String[] arguments, T list)
            throws WiagiInvalidIndexException {
        String index = arguments[INDEX_OF_ENTRY_INDEX];
        Type entryToEdit = extractEntry(list, index);
        String newValue = arguments[NEW_VALUE_INDEX];
        String category = arguments[CATEGORY_INDEX];
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
    }

    private <T extends ArrayList<? extends Type>> Type extractEntry(T list, String stringIndex)
            throws WiagiInvalidIndexException {
        try {
            int index = Integer.parseInt(stringIndex) - 1;
            return list.get(index);
        } catch (NumberFormatException e) {
            throw new WiagiInvalidIndexException(INDEX_NOT_INTEGER);
        } catch (IndexOutOfBoundsException e) {
            throw new WiagiInvalidIndexException(INDEX_OUT_OF_BOUNDS);
        }
    }
}
