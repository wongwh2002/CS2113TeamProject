package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiInvalidIndexException;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import java.util.ArrayList;

public class DeleteCommand extends Command {

    private final String fullCommand;
    public DeleteCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    // @@author wx-03
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        String[] userInputWords = fullCommand.split(" ");
        try {
            if (userInputWords.length < 3) {
                throw new WiagiMissingParamsException("Invalid input. " +
                        "Please enter in the form: delete [spending/income] [index]");
            }
            if (userInputWords[1].equalsIgnoreCase("income")) {
                deleteEntry(userInputWords, incomes);
            } else if (userInputWords[1].equalsIgnoreCase("spending")) {
                deleteEntry(userInputWords, spendings);
            } else {
                throw new WiagiInvalidInputException("No such category. " +
                        "Please enter in the form: delete [spending/income] [index]");
            }
        } catch (WiagiMissingParamsException | WiagiInvalidInputException | WiagiInvalidIndexException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private <T> boolean isOutOfBounds(int index, ArrayList<T> arrList) {
        return (index >= arrList.size() || index < 0);
    }

    private <T> void deleteEntry(String[] userInputWords, ArrayList<T> arrList) throws WiagiInvalidIndexException {
        int index = getIndex(userInputWords);
        if (isOutOfBounds(index, arrList)) {
            throw new WiagiInvalidIndexException("Invalid index");
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
            throw new WiagiInvalidInputException("Please input an integer as index.");
        }
    }
}
