package seedu.commands;

import org.junit.jupiter.api.Test;
import seedu.classes.Parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandTest {
    @Test
    public void isExit_correctCommand_success() {
        Command c = Parser.parse("bye");
        assertTrue(c.isExit());
    }

    @Test
    public void isExit_wrongCommand_failure() {
        Command c = Parser.parse("list");
        assertFalse(c.isExit());
    }
}
