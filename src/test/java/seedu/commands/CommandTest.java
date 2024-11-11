package seedu.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.classes.Parser;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classes.Constants.UNKNOWN_COMMAND_MESSAGE;

public class CommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restore() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

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
        c.execute(new IncomeList(), new SpendingList());
        assertEquals(UNKNOWN_COMMAND_MESSAGE, outContent.toString().strip());
    }
}
