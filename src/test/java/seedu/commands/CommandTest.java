package seedu.commands;

import org.junit.jupiter.api.Test;
import seedu.classes.Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
