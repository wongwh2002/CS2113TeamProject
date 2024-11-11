package seedu.classes;

import seedu.commands.AddCommand;
import seedu.commands.BudgetCommand;
import seedu.commands.ByeCommand;
import seedu.commands.Command;
import seedu.commands.DeleteCommand;
import seedu.commands.EditCommand;
import seedu.commands.FindCommand;
import seedu.commands.UnknownCommand;
import seedu.commands.HelpCommand;
import seedu.commands.listcommands.InvalidListCommand;
import seedu.commands.listcommands.ListAllCommand;
import seedu.commands.listcommands.ListIncomeCommand;
import seedu.commands.listcommands.ListSpendingCommand;
import seedu.commands.listcommands.ListTagsCommand;
import seedu.exception.WiagiInvalidInputException;
import seedu.recurrence.DailyRecurrence;
import seedu.recurrence.MonthlyRecurrence;
import seedu.recurrence.Recurrence;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.EntryType;
import seedu.recurrence.YearlyRecurrence;

import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.LIST_MAX_ARGUMENTS_LENGTH;
import static seedu.classes.Constants.WHITESPACE;
import static seedu.classes.Constants.LIST_COMMAND_FORMAT;

public class Parser {
    private static final int LIST_FIELD_INDEX = 1;
    private static final int LIST_ALL_COMMAND_LENGTH = 1;
    private static final int COMMAND_WORD_ARGUMENT = 0;

    /**
     * Parses the user input and returns the corresponding Command object.
     *
     * @param fullCommand the full user input
     * @return the corresponding Command object
     */
    public static Command parseUserInput(String fullCommand) {
        String command = fullCommand.split(WHITESPACE)[COMMAND_WORD_ARGUMENT].toLowerCase();

        switch (command) {
        case ByeCommand.COMMAND_WORD:
            return new ByeCommand();
        case AddCommand.COMMAND_WORD:
            return new AddCommand(fullCommand);
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommand(fullCommand);
        case ListAllCommand.COMMAND_WORD:
            return parseListCommand(fullCommand);
        case EditCommand.COMMAND_WORD:
            return new EditCommand(fullCommand);
        case BudgetCommand.COMMAND_WORD:
            return new BudgetCommand(fullCommand);
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case FindCommand.COMMAND_WORD:
            return new FindCommand(fullCommand);
        default:
            return new UnknownCommand();
        }
    }

    private static Command parseListCommand(String fullCommand) {
        String[] arguments = fullCommand.split(WHITESPACE, LIST_MAX_ARGUMENTS_LENGTH);
        int commandSize = arguments.length;
        assert commandSize != 0 : "command should have at least 1 word";
        if (commandSize == LIST_ALL_COMMAND_LENGTH) {
            return new ListAllCommand(arguments);
        }
        String field = arguments[LIST_FIELD_INDEX];
        switch (field) {
        case ListTagsCommand.COMMAND_WORD:
            return new ListTagsCommand(arguments);
        case ListSpendingCommand.COMMAND_WORD:
            return new ListSpendingCommand(arguments);
        case ListIncomeCommand.COMMAND_WORD:
            return new ListIncomeCommand(arguments);
        default:
            return new InvalidListCommand(new WiagiInvalidInputException(INVALID_CATEGORY + LIST_COMMAND_FORMAT));
        }
    }

    /**
     * Parses the entry string and returns the corresponding recurrence object.
     * @param entry
     * @return
     */
    public static Recurrence parseRecurrence(EntryType entry) {
        RecurrenceFrequency frequency = entry.getRecurrenceFrequency();

        switch (frequency) {
        case DAILY:
            return new DailyRecurrence();
        case MONTHLY:
            return new MonthlyRecurrence();
        case YEARLY:
            return new YearlyRecurrence();
        default:
            return null;
        }
    }
}
