package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.timeRange;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

/**
 * Represents a command to list incomes and spendings.
 */
public class ListCommand extends Command {

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
            String correctFormatString = "Please enter in the form: list [spendings/incomes/tags]";
            if (isCommandSizeEqual0) {
                throw new WiagiMissingParamsException("Missing parameters. " +
                        correctFormatString);
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
                    throw new WiagiInvalidInputException("Too many arguments. " +
                            correctFormatString);
                }
                timeRange listSpendingsTimeRange = null;
                while (listSpendingsTimeRange == null) {
                    listSpendingsTimeRange = askForTimeRange();
                }
                assert listSpendingsTimeRange != null : "time range cannot be null";
                if (listSpendingsTimeRange == timeRange.ALL) {
                    while (!listSpendingStatistics(spendings)) {
                        Ui.printWithTab("Please enter Y/N");
                    }
                } else if (listSpendingsTimeRange == timeRange.WEEKLY) {
                    Ui.printWeekly(spendings);
                } else if (listSpendingsTimeRange == timeRange.BIWEEKLY) {
                    Ui.printBiweekly(spendings);
                } else if (listSpendingsTimeRange == timeRange.MONTHLY) {
                    Ui.printMonthly(spendings);
                }
                break;
            case "incomes":
                assert firstIndex.equals("incomes") : "command should be to list incomes";
                if (isCommandSizeMoreThan2) {
                    throw new WiagiInvalidInputException("Too many arguments. " +
                            correctFormatString);
                }
                timeRange listIncomesTimeRange = null;
                while (listIncomesTimeRange == null) {
                    listIncomesTimeRange = askForTimeRange();
                }
                assert listIncomesTimeRange != null : "time range cannot be null";
                if (listIncomesTimeRange == timeRange.ALL) {
                    Ui.printIncomes(incomes);
                } else if (listIncomesTimeRange == timeRange.WEEKLY) {
                    Ui.printWeekly(incomes);
                } else if (listIncomesTimeRange == timeRange.BIWEEKLY) {
                    Ui.printBiweekly(incomes);
                } else if (listIncomesTimeRange == timeRange.MONTHLY) {
                    Ui.printMonthly(incomes);
                }
                break;
            default:
                throw new WiagiInvalidInputException("Invalid input. " +
                        "Please enter in the form: list [spendings/incomes/{tags TAG_NAME}]");
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
    private timeRange askForTimeRange() {
        Ui.printWithTab("Select time range:\n\t[1] All\n\t[2] Weekly\n\t[3] Biweekly\n\t[4] Monthly");
        String userInput = Ui.readCommand();
        if (userInput.equals("1")) {
            return timeRange.ALL;
        } else if (userInput.equals("2")) {
            return timeRange.WEEKLY;
        } else if (userInput.equals("3")) {
            return timeRange.BIWEEKLY;
        } else if (userInput.equals("4")) {
            return timeRange.MONTHLY;
        } else {
            Ui.printWithTab("Invalid input");
            return askForTimeRange();
        }
    }
}
