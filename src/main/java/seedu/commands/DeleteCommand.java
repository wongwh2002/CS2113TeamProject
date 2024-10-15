package seedu.commands;

import seedu.classes.Ui;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import java.util.ArrayList;

public class DeleteCommand extends Command {

    private final String fullCommand;
    public DeleteCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        String[] userInputWords = fullCommand.split(" ");
        try {
            if (userInputWords.length < 3) {
                throw new IllegalArgumentException("Invalid input. " +
                        "Please enter in the form: delete [spending/income] [index]");
            }

            if (userInputWords[1].equalsIgnoreCase("income")) {
                deleteEntry(userInputWords, incomes);
            } else if (userInputWords[1].equalsIgnoreCase("spending")) {
                deleteEntry(userInputWords, spendings);
            } else {
                throw new IllegalArgumentException("Invalid input. " +
                        "Please enter in the form: delete [spending/income] [index]");
            }
        } catch (IllegalArgumentException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private <T> boolean isOutOfBounds(int index, ArrayList<T> arrList) {
        return (index >= arrList.size() || index < 0);
    }

    private <T> void deleteEntry(String[] userInputWords, ArrayList<T> arrList) {
        int idx = getIdx(userInputWords);
        if (isOutOfBounds(idx, arrList)) {
            throw new IllegalArgumentException("Invalid index");
        }
        arrList.remove(arrList.get(idx));
        Ui.printWithTab("Successfully deleted!");
    }

    private int getIdx(String[] fullCommandArray) {
        int idx;
        try {
            idx = Integer.parseInt(fullCommandArray[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please input an integer as index.");
        }
        return idx - 1;
    }
}
