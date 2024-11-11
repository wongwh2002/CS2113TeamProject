package seedu.commands;

import org.junit.jupiter.api.Test;
import seedu.classes.Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classes.Constants.UNKNOWN_COMMAND_MESSAGE;

public class CommandTest {
    @Test
    public void isExit_correctCommand_success() {
        Command c = Parser.parseUserInput("bye");
        assertTrue(c.isExit());
    }

    @Test
    public void isExit_wrongCommand_failure() {
        Command c = Parser.parseUserInput("list");
        assertFalse(c.isExit());
    }

    @Test
    void execute_unknownCommand_unknownCommandMessage() {
        Command c = Parser.parseUserInput("blah");
        assertInstanceOf(UnknownCommand.class, c, UNKNOWN_COMMAND_MESSAGE);
    }
}
