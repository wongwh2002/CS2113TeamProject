package seedu.commands;

import seedu.classes.Constants;
import seedu.classes.Ui;
import seedu.exception.WiagiInvalidIndexException;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.IncomeList;
import seedu.type.SpendingList;
import seedu.type.Type;

import java.util.ArrayList;

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
        String[] userInputWords = fullCommand.split(" ", 5);
        try {
            if (userInputWords.length < 5) {
                throw new WiagiMissingParamsException(Constants.INCORRECT_PARAMS_NUMBER
                        + Constants.EDIT_COMMAND_FORMAT);
            }
            if (userInputWords[1].equals("spending")) {
                editList(userInputWords, spendings);
            } else if (userInputWords[1].equals("income")) {
                editList(userInputWords, incomes);
            } else {
                throw new WiagiInvalidInputException(Constants.INVALID_CATEGORY + Constants.EDIT_COMMAND_FORMAT);
            }
        } catch (WiagiMissingParamsException | WiagiInvalidInputException | WiagiInvalidIndexException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private <T extends ArrayList<? extends Type>> void editList(String[] arguments, T list)
            throws WiagiInvalidIndexException {
        try {
            Type toEdit = list.get(getIndex(arguments));
            String newValue = arguments[4];
            switch (arguments[3]) {
            case "amount":
                toEdit.editAmount(newValue);
                break;
            case "description":
                toEdit.editDescription(newValue);
                break;
            case "date":
                toEdit.editDate(newValue);
                break;
            case "tag":
                toEdit.editTag(newValue);
                break;
            default:
                throw new WiagiInvalidInputException(Constants.INVALID_FIELD + Constants.EDIT_COMMAND_FORMAT);
            }
            Ui.printWithTab("Edit Successful!");
        } catch (IndexOutOfBoundsException e) {
            throw new WiagiInvalidIndexException(Constants.INDEX_OUT_OF_BOUNDS);
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private int getIndex(String[] fullCommandArray) throws WiagiInvalidIndexException {
        try {
            int index = Integer.parseInt(fullCommandArray[2]);
            return index - 1;
        } catch (NumberFormatException e) {
            throw new WiagiInvalidIndexException(Constants.INDEX_NOT_INTEGER);
        }
    }
}
