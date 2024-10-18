package seedu.classes;

import seedu.commands.*;

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
}
