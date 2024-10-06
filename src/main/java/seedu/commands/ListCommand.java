package seedu.commands;

import seedu.classes.Ui;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

public class ListCommand extends Command {
    /**
     * Prints all incomes and spendings contained in the given IncomeList and SpendingList.
     *
     * @param incomes   IncomeList containing all incomes in the application.
     * @param spendings SpendingList containing all the spending in the application.
     */
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        Ui.printWithTab("Incomes:");
        for (int i = 0; i < incomes.size(); i++) {
            int oneIndexedI = i + 1;
            Ui.printWithTab(oneIndexedI + ". " + incomes.get(i).toString());
        }
        Ui.printWithTab("Spendings:");
        for (int i = 0; i < spendings.size(); i++) {
            int oneIndexedI = i + 1;
            Ui.printWithTab(oneIndexedI + ". " + spendings.get(i).toString());
        }
    }
}
