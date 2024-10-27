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

    //@@author rharwo
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        assert arguments[0].equals(COMMAND_WORD) : "command should be 'list'";
        Ui.printArrList(spendings);
        Ui.printArrList(incomes);
    }
}
