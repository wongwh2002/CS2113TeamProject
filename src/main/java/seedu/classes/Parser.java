package seedu.classes;

import seedu.commands.AddCommand;
import seedu.commands.ByeCommand;
import seedu.commands.Command;
import seedu.commands.DeleteCommand;
import seedu.commands.ListCommand;
import seedu.commands.EditCommand;
import seedu.commands.BudgetCommand;
import seedu.commands.UnknownCommand;
import seedu.recurrence.DailyRecurrence;
import seedu.recurrence.MonthlyRecurrence;
import seedu.recurrence.Recurrence;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.Type;
import seedu.recurrence.YearlyRecurrence;

public class Parser {

    public static Command parse(String fullCommand) {
        String command = fullCommand.split(" ")[0].toLowerCase();

        switch (command) {
        case "bye":
            return new ByeCommand();
        case "add": // user input should be in the form add [add type] [amount] [description]...
            return new AddCommand(fullCommand);
        case "delete":
            return new DeleteCommand(fullCommand);
        case "list":
            return new ListCommand(fullCommand);
        case "edit":
            return new EditCommand(fullCommand);
        case "budget":
            return new BudgetCommand(fullCommand);
        default:
            return new UnknownCommand();
        }
    }

    public static Recurrence parseRecurrence(Type entry) {
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
