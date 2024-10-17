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
                throw new IllegalArgumentException("Invalid input. Please enter in the form: list [spendings/incomes]");
            }

            if (commandSize == 1) {
                printSpendings(spendings);
                printIncomes(incomes);
                return;
            }

            if (fullCommands[1].equals("spendings")) {
                printSpendings(spendings);
            } else if (fullCommands[1].equals("incomes")) {
                printIncomes(incomes);
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

    private void printSpendings(SpendingList spendings) {
        Ui.printWithTab("Spendings");
        Ui.printWithTab("Total spendings: " + print_list(spendings));
        Ui.printWithTab("Daily spendings: " + spendings.getDailySpending() + " Monthly spendings: " +
                spendings.getMontlySpending() + " Yearly Spendings: " + spendings.getYearlySpending());
        Ui.printWithTab("Daily Budget: " + spendings.getDailyBudget() + " Monthly Budget: " +
                spendings.getMonthlyBudget() + " Yearly Budget: " + spendings.getYearlyBudget());
    }

    private void printIncomes(IncomeList incomes) {
        Ui.printWithTab("Incomes");
        Ui.printWithTab("Total incomes: " + print_list(incomes));
    }
}
