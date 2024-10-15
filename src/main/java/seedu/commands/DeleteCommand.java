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

            if (fullCommandArray[1].equalsIgnoreCase("income")) {
                int idx = getIdx(fullCommandArray);
                if (isOutOfBounds(idx, incomes)) {
                    throw new IllegalArgumentException("Invalid index");
                }
                incomes.remove(incomes.get(idx));
                Ui.printWithTab("Successfully deleted!");
            } else if (fullCommandArray[1].equalsIgnoreCase("spending")) {
                int idx = getIdx(fullCommandArray);
                if (isOutOfBounds(idx, spendings)) {
                    throw new IllegalArgumentException("Invalid index");
                }
                spendings.remove(spendings.get(idx));
                Ui.printWithTab("Successfully deleted!");
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
