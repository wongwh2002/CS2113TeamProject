package seedu.commands.listcommands;

import seedu.classes.Ui;
import seedu.commands.Command;
import seedu.enums.TimeRange;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.LIST_COMMAND_FORMAT;
import static seedu.classes.Constants.LIST_INCOMES_SPENDINGS_MAX_LENGTH;

public class ListIncomesCommand extends Command {

    public static final String COMMAND_WORD = "incomes";

    private final String[] fullCommands;

    public ListIncomesCommand(String[] fullCommands) {
        this.fullCommands = fullCommands;
    }

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        assert fullCommands[1].equals("incomes") : "command should be to list incomes";
        try {
            if (fullCommands.length > LIST_INCOMES_SPENDINGS_MAX_LENGTH) {
                throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER
                        + LIST_COMMAND_FORMAT);
            }
            TimeRange listIncomesTimeRange = null;
            while (listIncomesTimeRange == null) {
                listIncomesTimeRange = Ui.askForTimeRange();
            }
            assert listIncomesTimeRange != null : "time range cannot be null";
            if (listIncomesTimeRange == TimeRange.ALL) {
                Ui.printIncomes(incomes);
            } else if (listIncomesTimeRange == TimeRange.WEEKLY) {
                Ui.printWeekly(incomes);
            } else if (listIncomesTimeRange == TimeRange.BIWEEKLY) {
                Ui.printBiweekly(incomes);
            } else if (listIncomesTimeRange == TimeRange.MONTHLY) {
                Ui.printMonthly(incomes);
            }
        } catch (WiagiMissingParamsException e) {
            Ui.printWithTab(e.getMessage());
        }

    }
}
