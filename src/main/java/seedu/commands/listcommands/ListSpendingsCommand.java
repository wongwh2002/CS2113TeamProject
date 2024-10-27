package seedu.commands.listcommands;

import seedu.classes.Ui;
import seedu.commands.Command;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.LIST_COMMAND_FORMAT;
import static seedu.classes.Constants.LIST_TYPE_INDEX;
import static seedu.classes.Constants.LIST_COMPULSORY_ARGUMENTS_LENGTH;

public class ListSpendingsCommand extends Command {

    public static final String COMMAND_WORD = "spendings";
    private final String[] arguments;

    public ListSpendingsCommand(String[] fullCommands) {
        this.arguments = fullCommands;
    }

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        assert arguments[LIST_TYPE_INDEX].equals("spendings") : "command should be to list spendings";
        try {
            commandHandler(spendings);
        } catch (WiagiMissingParamsException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void commandHandler(SpendingList spendings) throws WiagiMissingParamsException {
        if (arguments.length != LIST_COMPULSORY_ARGUMENTS_LENGTH) {
            throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER + LIST_COMMAND_FORMAT);
        }
        boolean isListAllSpendings = Ui.printListOfTimeRange(spendings);
        if (isListAllSpendings) {
            Ui.printStatisticsIfRequired(spendings);
        }
    }
}
