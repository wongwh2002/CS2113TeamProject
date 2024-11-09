package seedu.commands.listcommands;

import seedu.classes.Ui;
import seedu.commands.Command;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

public class ListTagsCommand extends Command {

    public static final String COMMAND_WORD = "tags";
    private static final int LIST_FIELD_INDEX = 1;
    private static final int LIST_TAG_NAME_INDEX = 2;
    private static final int LIST_SPECIFIC_TAG_LENGTH = 3;
    private final String[] arguments;

    public ListTagsCommand(String[] arguments) {
        this.arguments = arguments;
    }

    //@@author wongwh2002
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        assert arguments[LIST_FIELD_INDEX].equals(COMMAND_WORD) : "command should be to list tags";
        try {
            handleCommand(incomes, spendings);
        } catch (WiagiInvalidInputException | WiagiMissingParamsException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void handleCommand(IncomeList incomes, SpendingList spendings) throws WiagiMissingParamsException {
        if (arguments.length < LIST_SPECIFIC_TAG_LENGTH) {
            Ui.printAllTags(incomes, spendings);
        } else {
            String tagName = arguments[LIST_TAG_NAME_INDEX];
            assert tagName != null : "tag name should not be null";
            Ui.printSpecificTag(incomes, spendings, tagName);
        }
    }
}
