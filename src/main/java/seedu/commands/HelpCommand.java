package seedu.commands;

import seedu.classes.Ui;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

/**
 * Represents a command that displays usage instructions for the application.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

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
        appendOtherCommandsHelp(helpText);
        Ui.printWithTab(helpText.toString());
    }

    private void appendCommandDescription(StringBuilder helpText) {
        helpText.append(System.lineSeparator()).append("Notes about the command format:").append(System.lineSeparator())
                .append("Words in {$UPPER_CASE} are the parameters to be supplied by the user.")
                .append(System.lineSeparator())
                .append("e.g., in add spending {$AMOUNT} {$DESCRIPTION}, {$AMOUNT} and {$DESCRIPTION}")
                .append(System.lineSeparator()).append("are parameters which can be used as add spending 4 dinner.")
                .append(System.lineSeparator())
                .append("Items in square brackets are optional.").append(System.lineSeparator())
                .append("e.g. add spending {$AMOUNT} {$DESCRIPTION} [/$DATE/] can be used as")
                .append(System.lineSeparator()).append("add spending 4 lunch or add spending 4 lunch /2024-10-20/.")
                .append(System.lineSeparator()).append(System.lineSeparator());
    }

    private void appendAddCommandHelp(StringBuilder helpText) {
        helpText.append("Adding Entries:").append(System.lineSeparator())
                .append("\tadd income {$AMOUNT} {$DESCRIPTION} [/$DATE/] [*$TAG*] [~$FREQUENCY~]")
                .append(System.lineSeparator())
                .append("\tadd spending {$AMOUNT} {$DESCRIPTION} [/$DATE/] [*$TAG*] [~$FREQUENCY~]")
                .append(System.lineSeparator())
                .append("\te.g., add income 5000 Salary /2024-03-15/ *work* ~monthly~").append(System.lineSeparator())
                .append("\te.g., add spending 50 Lunch /2024-03-15/ *food*").append(System.lineSeparator())
                .append(System.lineSeparator());
    }

    private void appendListCommandHelp(StringBuilder helpText) {
        helpText.append("Listing Entries:").append(System.lineSeparator())
                .append("\tlist - shows all entries").append(System.lineSeparator())
                .append("\tlist incomes - shows all income entries").append(System.lineSeparator())
                .append("\tlist spendings - shows all spending entries").append(System.lineSeparator())
                .append("\tlist tags {$TAG} - shows entries with specific tag").append(System.lineSeparator())
                .append(System.lineSeparator());
    }

    private void appendEditCommandHelp(StringBuilder helpText) {
        helpText.append("Editing Entries:").append(System.lineSeparator())
                .append("\tedit {$TYPE} {$INDEX} {$FIELD} {$NEW_VALUE}").append(System.lineSeparator())
                .append("\te.g., edit spending 1 amount 100").append(System.lineSeparator())
                .append("\te.g., edit income 2 description Bonus").append(System.lineSeparator())
                .append(System.lineSeparator());
    }

    private void appendDeleteCommandHelp(StringBuilder helpText) {
        helpText.append("Deleting Entries:").append(System.lineSeparator())
                .append("\tdelete {$TYPE} {$INDEX}").append(System.lineSeparator())
                .append("\te.g., delete spending 1").append(System.lineSeparator())
                .append(System.lineSeparator());
    }

    private void appendBudgetCommandHelp(StringBuilder helpText) {
        helpText.append("Setting Budget:").append(System.lineSeparator())
                .append("\tbudget {$PERIOD} {$AMOUNT}").append(System.lineSeparator())
                .append("\te.g., budget daily 50").append(System.lineSeparator())
                .append("\te.g., budget monthly 1500").append(System.lineSeparator())
                .append(System.lineSeparator());
    }

    private void appendOtherCommandsHelp(StringBuilder helpText) {
        helpText.append("Other Commands:").append(System.lineSeparator())
                .append("\thelp - shows this help message").append(System.lineSeparator())
                .append("\tbye - exits the application").append(System.lineSeparator());
    }
}
