package seedu.commands.listcommands;

import seedu.classes.Ui;
import seedu.commands.Command;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

public class ListAllCommand extends Command {

    public static final String COMMAND_WORD = "list";
    private final String[] arguments;

    public ListAllCommand(String [] arguments) {
        this.arguments = arguments;
    }

    /**
     * Executes list command with the given arguments
     * @param incomes   list of incomes in the application
     * @param spendings list of spendings in the application
     */
    //@@author rharwo
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        assert arguments[0].equals(COMMAND_WORD) : "command should be 'list'";
        Ui.printListWithTotal(spendings);
        Ui.printListWithTotal(incomes);
    }
}
