package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
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
            if (commandSize == 0) {
                throw new WiagiMissingParamsException("Missing parameters. " +
                        "Please enter in the form: list [spendings/incomes/tags]");
            }



            if (commandSize == 1) {
                Ui.printSpendings(spendings);
                Ui.printIncomes(incomes);
                return;
            }
            switch (fullCommands[1]) {
            case "tags":
                if (commandSize == 3) {
                    Ui.printSpecificTag(incomes, spendings, fullCommands[2]);
                } else {
                    Ui.printAllTags(incomes, spendings);
                }
                break;
            case "spendings":
                if (commandSize > 2) {
                    throw new WiagiInvalidInputException("Too many arguments. " +
                            "Please enter in the form: list [spendings/incomes/tags]");
                }
                Ui.printSpendings(spendings);
                break;
            case "incomes":
                if (commandSize > 2) {
                    throw new WiagiInvalidInputException("Too many arguments. " +
                            "Please enter in the form: list [spendings/incomes/tags]");
                }
                Ui.printIncomes(incomes);
                break;
            default:
                throw new WiagiInvalidInputException("Invalid input. " +
                        "Please enter in the form: list [spendings/incomes/{tags TAG_NAME}]");
            }
        } catch (WiagiInvalidInputException | WiagiMissingParamsException e) {
            Ui.printWithTab(e.getMessage());
        } catch (Exception e) {
            Ui.printWithTab("An error occurred while listing the items.");
        }
    }
}
