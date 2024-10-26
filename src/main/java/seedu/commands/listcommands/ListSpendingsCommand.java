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
import static seedu.classes.Constants.SECOND_WORD_INDEX;

public class ListSpendingsCommand extends Command {

    public static final String COMMAND_WORD = "spendings";

    private final String[] fullCommands;

    public ListSpendingsCommand(String[] fullCommands) {
        this.fullCommands = fullCommands;
    }

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        assert fullCommands[SECOND_WORD_INDEX].equals("spendings") : "command should be to list spendings";

        try {
            if (fullCommands.length > LIST_INCOMES_SPENDINGS_MAX_LENGTH) {
                throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER
                        + LIST_COMMAND_FORMAT);
            }
            TimeRange listSpendingsTimeRange = null;
            while (listSpendingsTimeRange == null) {
                listSpendingsTimeRange = Ui.askForTimeRange();
            }
            assert listSpendingsTimeRange != null : "time range cannot be null";
            if (listSpendingsTimeRange == TimeRange.ALL) {
                while (!listSpendingStatistics(spendings)) {
                    Ui.printWithTab("Please enter Y/N");
                }
            } else if (listSpendingsTimeRange == TimeRange.WEEKLY) {
                Ui.printWeekly(spendings);
            } else if (listSpendingsTimeRange == TimeRange.BIWEEKLY) {
                Ui.printBiweekly(spendings);
            } else if (listSpendingsTimeRange == TimeRange.MONTHLY) {
                Ui.printMonthly(spendings);
            }
        } catch (WiagiMissingParamsException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    //@@author NigelYeoTW
    private boolean listSpendingStatistics(SpendingList spendings) {
        Ui.printWithTab("List all statistics? [Y/N]:");
        String userInput = Ui.readCommand();
        if (userInput.equals("y") || userInput.equals("Y")) {
            Ui.printSpendings(spendings);
            Ui.printSpendingStatistics(spendings);
            return true;
        } else if (userInput.equals("n") || userInput.equals("N")) {
            Ui.printSpendings(spendings);
            return true;
        }
        return false;
    }
}
