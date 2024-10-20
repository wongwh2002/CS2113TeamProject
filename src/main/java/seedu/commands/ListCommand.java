package seedu.commands;

import seedu.classes.Ui;
import seedu.type.IncomeList;
import seedu.type.SpendingList;
import seedu.type.Type;

import java.util.ArrayList;

/**
 * Represents a command to list incomes and spendings.
 */
public class ListCommand extends Command {

    private final String fullCommand;

    /**
     * Constructs a ListCommand with the specified full command.
     *
     * @param fullCommand The full command string.
     */
    public ListCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * Prints all incomes and spendings contained in the given IncomeList and SpendingList.
     *
     * @param incomes   IncomeList containing all incomes in the application.
     * @param spendings SpendingList containing all the spending in the application.
     */
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        String[] fullCommands = this.fullCommand.split(" ");
        int commandSize = fullCommands.length;
        try {
            if (commandSize == 0 || commandSize > 2) {
                throw new IllegalArgumentException("Invalid input. Please enter in the form: list [spendings/incomes]");
            }

            if (commandSize == 1) {
                Ui.printSpendings(spendings);
                Ui.printIncomes(incomes);
                return;
            }

            if (fullCommands[1].equals("spendings")) {
                Ui.printSpendings(spendings);
            } else if (fullCommands[1].equals("incomes")) {
                Ui.printIncomes(incomes);
            } else {
                throw new IllegalArgumentException("Invalid input. " +
                        "Please enter in the form: list [spendings/incomes]");
            }
        } catch (IllegalArgumentException e) {
            Ui.printWithTab(e.getMessage());
        } catch (Exception e) {
            Ui.printWithTab("An error occurred while listing the items.");
        }
    }
}
