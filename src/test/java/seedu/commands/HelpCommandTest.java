package seedu.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classes.Ui.commandInputForTest;

public class HelpCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    IncomeList incomes = new IncomeList();
    SpendingList spendings = new SpendingList();

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
    public void execute_helpCommandExecuted_messagePrinted() {
        commandInputForTest("help", incomes, spendings);
        assertEquals(45 ,outContent.toString().lines().count());
    }
}
