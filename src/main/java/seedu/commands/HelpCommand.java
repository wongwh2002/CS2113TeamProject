package seedu.commands;

import seedu.classes.Ui;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import static seedu.classes.Constants.NEXT_LINE;
import static seedu.classes.Constants.TAB;

/**
 * Represents a command that displays usage instructions for the application.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    //@@author rharwo
    /**
     * Executes a help command with the given arguments, printing the usage instructions for each command.
     *
     * @param incomes   list of incomes in the application
     * @param spendings list of spendings in the application
     */
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        assert incomes != null;
        assert spendings != null;
        StringBuilder helpText = new StringBuilder();
        appendCommandDescription(helpText);
        appendAddCommandHelp(helpText);
        appendListCommandHelp(helpText);
        appendEditCommandHelp(helpText);
        appendDeleteCommandHelp(helpText);
        appendBudgetCommandHelp(helpText);
        appendFindCommandHelp(helpText);
        appendOtherCommandsHelp(helpText);
        Ui.printWithTab(helpText.toString());
    }

    private void appendCommandDescription(StringBuilder helpText) {
        helpText.append(NEXT_LINE).append("Notes about the command format:").append(NEXT_LINE)
                .append("Words in {$UPPER_CASE} are the parameters to be supplied by the user.")
                .append(NEXT_LINE)
                .append("e.g., in add spending {$AMOUNT} {$DESCRIPTION}, {$AMOUNT} and {$DESCRIPTION}")
                .append(NEXT_LINE).append("are parameters which can be used as add spending 4 dinner.")
                .append(NEXT_LINE)
                .append("Items in square brackets are optional.").append(NEXT_LINE)
                .append("e.g. add spending {$AMOUNT} {$DESCRIPTION} [/$DATE/] can be used as")
                .append(NEXT_LINE).append("add spending 4 lunch or add spending 4 lunch /2024-10-20/.")
                .append(NEXT_LINE).append(NEXT_LINE);
    }

    private void appendAddCommandHelp(StringBuilder helpText) {
        helpText.append("Adding Entries:").append(NEXT_LINE)
                .append(TAB + "add income {$AMOUNT} {$DESCRIPTION} [/$DATE/] [*$TAG*] [~$FREQUENCY~]")
                .append(NEXT_LINE)
                .append(TAB + "add spending {$AMOUNT} {$DESCRIPTION} [/$DATE/] [*$TAG*] [~$FREQUENCY~]")
                .append(NEXT_LINE)
                .append(TAB + "e.g., add income 5000 Salary /2024-03-15/ *work* ~monthly~")
                .append(NEXT_LINE)
                .append(TAB + "e.g., add spending 50 Lunch /2024-03-15/ *food*").append(NEXT_LINE)
                .append(NEXT_LINE);
    }

    private void appendListCommandHelp(StringBuilder helpText) {
        helpText.append("Listing Entries:").append(NEXT_LINE)
                .append(TAB + "list - shows all entries").append(NEXT_LINE)
                .append(TAB + "list income - shows all income entries").append(NEXT_LINE)
                .append(TAB + "list spending - shows all spending entries").append(NEXT_LINE)
                .append(TAB + "list tags {$TAG} - shows entries with specific tag").append(NEXT_LINE)
                .append(NEXT_LINE);
    }

    private void appendEditCommandHelp(StringBuilder helpText) {
        helpText.append("Editing Entries:").append(NEXT_LINE)
                .append(TAB + "edit {$CATEGORY} {$INDEX} {$FIELD} {$NEW_VALUE}").append(NEXT_LINE)
                .append(TAB + "-note that {$FIELD} only works for amount, description, date and tags").append(NEXT_LINE)
                .append(TAB + "e.g., edit spending 1 amount 100").append(NEXT_LINE)
                .append(TAB + "e.g., edit income 2 description Bonus").append(NEXT_LINE)
                .append(NEXT_LINE);
    }

    private void appendDeleteCommandHelp(StringBuilder helpText) {
        helpText.append("Deleting Entries:").append(NEXT_LINE)
                .append(TAB + "delete {$CATEGORY} {$INDEX}").append(NEXT_LINE)
                .append(TAB + "e.g., delete spending 1").append(NEXT_LINE)
                .append(NEXT_LINE);
    }

    private void appendBudgetCommandHelp(StringBuilder helpText) {
        helpText.append("Setting Budget:").append(NEXT_LINE)
                .append(TAB + "budget {$PERIOD} {$AMOUNT}").append(NEXT_LINE)
                .append(TAB + "e.g., budget daily 50").append(NEXT_LINE)
                .append(TAB + "e.g., budget monthly 1500").append(NEXT_LINE)
                .append(NEXT_LINE);
    }

    private void appendFindCommandHelp(StringBuilder helpText) {
        helpText.append("Finding entries:").append(NEXT_LINE)
                .append(TAB + "find {$CATEGORY} {$FIELD} {$FIND_VALUE} [to $ANOTHER_FIND_VALUE]")
                .append(NEXT_LINE)
                .append(TAB + "-note that [to $ANOTHER_FIND_VALUE] only works for amount and date field")
                .append(NEXT_LINE)
                .append(TAB + "e.g., find income amount 10").append(NEXT_LINE)
                .append(TAB + "e.g., find spending date 2024-01-01 to 2024-12-31").append(NEXT_LINE)
                .append(NEXT_LINE);
    }

    private void appendOtherCommandsHelp(StringBuilder helpText) {
        helpText.append("Other Commands:").append(NEXT_LINE)
                .append(TAB + "help - shows this help message").append(NEXT_LINE)
                .append(TAB + "bye - exits the application").append(NEXT_LINE);
    }
}
