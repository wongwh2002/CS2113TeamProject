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

    /**
     * Prints the elements of the given ArrayList and calculates the sum of their amounts.
     *
     * @param <T>     The type of elements in the ArrayList, which must extend the Type class.
     * @param arrList The ArrayList containing elements to be printed and summed.
     * @return The sum of the amounts of the elements in the ArrayList as a String.
     */
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
                return;
            }

            Ui.printWithTab(fullCommands[1].substring(0,1).toUpperCase() + fullCommands[1].substring(1));
            if (fullCommands[1].equals("spendings")) {
                Ui.printWithTab("Total " + fullCommands[1] + ": " + print_list(spendings));
            }
            else if (fullCommands[1].equals("incomes")) {
                Ui.printWithTab("Total " + fullCommands[1] + ": " + print_list(incomes));
            }
            else {
                throw new IllegalArgumentException("Invalid input. Please enter in the form: list [spendings/incomes]");
            }
//                Ui.printWithTab("Total " + fullCommands[1] + "s: " +
//                        print_list(fullCommands[1].equals("spending") ? spendings : incomes)); idk why doesnt this work

        } catch (IllegalArgumentException e) {
            Ui.printWithTab(e.getMessage());
        } catch (Exception e) {
            Ui.printWithTab("An error occurred while listing the items.");
        }
    }
}