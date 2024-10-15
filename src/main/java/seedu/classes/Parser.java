package seedu.classes;

import seedu.commands.AddCommand;
import seedu.commands.ByeCommand;
import seedu.commands.Command;
import seedu.commands.DeleteCommand;
import seedu.commands.ListCommand;
import seedu.commands.UnknownCommand;

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
        default:
            return new UnknownCommand();
        }
    }
}
