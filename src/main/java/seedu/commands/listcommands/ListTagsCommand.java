package seedu.commands.listcommands;

import seedu.classes.Ui;
import seedu.commands.Command;
import seedu.exception.WiagiInvalidInputException;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

public class ListTagsCommand extends Command {

    public static final String COMMAND_WORD = "tags";
    private static final int LIST_SPECIFIC_TAG_LENGTH = 3;
    private final String[] fullCommands;
    private final int commandSize;

    public ListTagsCommand(String[] fullCommands) {
        this.fullCommands = fullCommands;
        this.commandSize = fullCommands.length;
    }

    //@@author wongwh2002
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        assert fullCommands[1].equals("tags") : "command should be to list tags";
        try {
            if (commandSize == LIST_SPECIFIC_TAG_LENGTH) {
                assert fullCommands[2] != null : "tag name should not be null";
                Ui.printSpecificTag(incomes, spendings, fullCommands[2]);
            } else {
                Ui.printAllTags(incomes, spendings);
            }
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
        }
    }
}
