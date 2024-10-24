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
        try {
            String correctFormatString = "Please enter in the form: list [spendings/incomes/tags]";
            if (commandSize == 0) {
                throw new WiagiMissingParamsException("Missing parameters. " +
                        correctFormatString);
            }

            if (commandSize == 1) {
                assert fullCommands[0].equals("list") : "command should be 'list'";
                Ui.printSpendings(spendings);
                Ui.printIncomes(incomes);
                return;
            }
            switch (fullCommands[1]) {
            case "tags":
                assert fullCommands[1].equals("tags") : "command should be to list tags";
                if (commandSize == 3) {
                    assert fullCommands[2] != null : "tag name should not be null";
                    Ui.printSpecificTag(incomes, spendings, fullCommands[2]);
                } else {
                    Ui.printAllTags(incomes, spendings);
                }
                break;
            case "spendings":
                assert fullCommands[1].equals("spendings") : "command should be to list spendings";
                if (commandSize > 2) {
                    throw new WiagiInvalidInputException("Too many arguments. " +
                            correctFormatString);
                }
                timeRange listTimeRange = null;
                while (listTimeRange == null) {
                    listTimeRange = askForTimeRange();
                }
                assert listTimeRange != null : "time range cannot be null";
                if (listTimeRange == timeRange.ALL) {
                    while (!listSpendingStatistics(spendings)) {
                        Ui.printWithTab("Please enter Y/N");
                    }
                } else if (listTimeRange == timeRange.WEEKLY) {
                    Ui.printWeekly(spendings);
                } else if (listTimeRange == timeRange.BIWEEKLY) {
                    Ui.printBiweekly(spendings);
                } else if (listTimeRange == timeRange.MONTHLY) {
                    Ui.printMonthly(spendings);
                }
                break;
            case "incomes":
                assert fullCommands[1].equals("incomes") : "command should be to list incomes";
                if (commandSize > 2) {
                    throw new WiagiInvalidInputException("Too many arguments. " +
                            correctFormatString);
                }
                Ui.printIncomes(incomes);
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
