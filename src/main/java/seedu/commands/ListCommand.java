package seedu.commands;

import seedu.classes.Ui;
import seedu.type.IncomeList;
import seedu.type.SpendingList;
import seedu.type.Type;

import java.util.ArrayList;

public class ListCommand extends Command {

    private final String fullCommand;

    public ListCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    public <T> String print_list(ArrayList<T> arrList) {
        int sum = 0;
        for (int i = 0; i < arrList.size(); i++) {
            int oneIndexedI = i + 1;
            sum += ((Type) arrList.get(i)).getAmount();
            Ui.printWithTab(oneIndexedI + ". " + arrList.get(i));
        }
        return String.valueOf(sum);
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
                throw new IllegalArgumentException("Invalid input. Please enter in the form: list [spending/income]");
            }

            if (commandSize == 1) {
                Ui.printWithTab("Spendings ");
                Ui.printWithTab("Total spendings: " + print_list(spendings));
                Ui.printWithTab("Incomes ");
                Ui.printWithTab("Total incomes: " + print_list(incomes));
            }
            else {
                Ui.printWithTab(fullCommands[1]);
                Ui.printWithTab("Total " + fullCommands[1] + "s: " + print_list(incomes));
            }
        } catch (IllegalArgumentException e) {
            Ui.printWithTab(e.getMessage());
        } catch (Exception e) {
            Ui.printWithTab("An error occurred while listing the items.");
        }
    }
}