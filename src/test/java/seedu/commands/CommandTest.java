package seedu.commands;

import org.junit.jupiter.api.Test;
import seedu.classes.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Ui.commandInputForTest;

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
        assertInstanceOf(UnknownCommand.class, c, "Unknown Command");
    }
}
