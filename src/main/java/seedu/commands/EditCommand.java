package seedu.commands;

import seedu.classes.Ui;
import seedu.type.IncomeList;
import seedu.type.SpendingList;
import seedu.type.Type;

import java.util.ArrayList;

public class EditCommand extends Command {

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
        String[] userInputWords = fullCommand.split(" ");
        try {
            if (userInputWords.length < 4) {
                throw new IllegalArgumentException();
            }
            if (userInputWords[1].equals("spending")) {
                editList(userInputWords, spendings);
            } else if (userInputWords[1].equals("income")) {
                editList(userInputWords, incomes);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            Ui.printWithTab("Invalid input. " +
                    "Please enter in the form: edit [spending/income] [index] [amount/description] [new value]...");
        }
    }

    private <T extends ArrayList<? extends Type>> void editList(String[] arguments, T list) {
        try {
            Type toEdit = list.get(getIdx(arguments));
            if (arguments[3].equals("amount")) {
                int newAmount = Integer.parseInt(arguments[4]);
                toEdit.editAmount(newAmount);
            } else if (arguments[3].equals("description")) {
                String newDescription = arguments[4];
                toEdit.editDescription(newDescription);
            } else {
                throw new IllegalArgumentException();
            }
            Ui.printWithTab("Edit Successful!");
        } catch (IndexOutOfBoundsException e) {
            Ui.printWithTab("Please enter a valid index.");
        }

    }

    private int getIdx(String[] fullCommandArray) {
        int idx;
        try {
            idx = Integer.parseInt(fullCommandArray[2]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please input an integer as index.");
        }
        return idx - 1;
    }
}
