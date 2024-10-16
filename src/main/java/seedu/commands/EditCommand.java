package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiInvalidInputException;
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
     * @param incomes
     * @param spendings
     */
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        String[] userInputWords = fullCommand.split(" ", 5);
        try {
            if (userInputWords.length < 5) {
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
                    "Please enter in the form: " +
                    "edit [spending/income] [index] [amount/description/date] [new value]...");
        }
    }

    private <T extends ArrayList<? extends Type>> void editList(String[] arguments, T list) {
        try {
            Type toEdit = list.get(getIndex(arguments));
            String newValue = arguments[4];
            switch (arguments[3]) {
            case "amount" -> {
                toEdit.editAmount(newValue);
            }
            case "description" -> {
                toEdit.editDescription(newValue);
            }
            case "date" -> {
                toEdit.editDate(newValue);
            }
            default -> throw new IllegalArgumentException();
            }
            Ui.printWithTab("Edit Successful!");
        } catch (IndexOutOfBoundsException e) {
            Ui.printWithTab("Please enter a valid index.");
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private int getIndex(String[] fullCommandArray) {
        try {
            int index = Integer.parseInt(fullCommandArray[2]);
            return index - 1;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please input an integer as index.");
        }
    }
}
