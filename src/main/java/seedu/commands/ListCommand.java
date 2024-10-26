package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.enums.TimeRange;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.LIST_COMMAND_FORMAT;
import static seedu.classes.Constants.TIME_RANGE_MESSAGE;

/**
 * Represents a command to list incomes and spendings.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    private final String fullCommand;

    /**
     * Constructs a ListCommand with the specified full command.
     *
     * @param fullCommand The full command string.
     */
    public ListCommand(String fullCommand) {
        this.fullCommand = fullCommand;
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
        boolean isCommandSizeMoreThan2 = commandSize > 2;
        boolean isCommandSizeEqual3 = commandSize == 3;
        boolean isCommandSizeEqual1 = commandSize == 1;
        boolean isCommandSizeEqual0 = commandSize == 0;

        try {
            if (isCommandSizeEqual0) {
                throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER
                        + LIST_COMMAND_FORMAT);
            }

            if (isCommandSizeEqual1) {
                assert fullCommands[0].equals("list") : "command should be 'list'";
                Ui.printSpendings(spendings);
                Ui.printIncomes(incomes);
                return;
            }
            String firstIndex = fullCommands[1];
            switch (firstIndex) {
            case "tags":
                assert firstIndex.equals("tags") : "command should be to list tags";
                if (isCommandSizeEqual3) {
                    assert fullCommands[2] != null : "tag name should not be null";
                    Ui.printSpecificTag(incomes, spendings, fullCommands[2]);
                } else {
                    Ui.printAllTags(incomes, spendings);
                }
                break;
            case "spendings":
                assert firstIndex.equals("spendings") : "command should be to list spendings";
                if (isCommandSizeMoreThan2) {
                    throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER
                            + LIST_COMMAND_FORMAT);
                }
                TimeRange listSpendingsTimeRange = null;
                while (listSpendingsTimeRange == null) {
                    listSpendingsTimeRange = askForTimeRange();
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
                break;
            case "incomes":
                assert firstIndex.equals("incomes") : "command should be to list incomes";
                if (isCommandSizeMoreThan2) {
                    throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER
                            + LIST_COMMAND_FORMAT);
                }
                TimeRange listIncomesTimeRange = null;
                while (listIncomesTimeRange == null) {
                    listIncomesTimeRange = askForTimeRange();
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
                break;
            default:
                throw new WiagiInvalidInputException(INVALID_CATEGORY + LIST_COMMAND_FORMAT);
            }
        } catch (WiagiInvalidInputException | WiagiMissingParamsException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

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

    //@@author wx-03
    private TimeRange askForTimeRange() {
        TimeRange selectedTimeRange = null;
        while (selectedTimeRange == null) {
            Ui.printWithTab(TIME_RANGE_MESSAGE);
            String userInput = Ui.readCommand();
            switch (userInput) {
            case "1":
                selectedTimeRange = TimeRange.ALL;
                break;
            case "2":
                selectedTimeRange = TimeRange.WEEKLY;
                break;
            case "3":
                selectedTimeRange = TimeRange.BIWEEKLY;
                break;
            case "4":
                selectedTimeRange = TimeRange.MONTHLY;
                break;
            default:
                Ui.printWithTab("Invalid input");
            }
        }
        return selectedTimeRange;
    }
}
