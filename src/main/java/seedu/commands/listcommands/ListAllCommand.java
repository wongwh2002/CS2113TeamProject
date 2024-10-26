package seedu.commands.listcommands;

import seedu.classes.Ui;
import seedu.commands.Command;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

public class ListAllCommand extends Command {

    public static final String COMMAND_WORD = "list";
    private final String[] fullCommands;

    public ListAllCommand(String [] fullCommands) {
        this.fullCommands = fullCommands;
    }

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        assert fullCommands[0].equals("list") : "command should be 'list'";
        Ui.printSpendings(spendings);
        Ui.printIncomes(incomes);
    }
}
