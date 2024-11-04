package seedu.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classes.Constants.DELETE_COMMAND_FORMAT;
import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.INDEX_NOT_INTEGER;
import static seedu.classes.Constants.INDEX_OUT_OF_BOUNDS;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Ui.commandInputForTest;

class DeleteCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private IncomeList incomes = new IncomeList();
    private SpendingList spendings = new SpendingList();

    // @@author wx-03
    @BeforeEach
    void setUp() {
        incomes.add(new Income(1000, "salary", null, null, null, null, 0));
        spendings.add(new Spending(4, "dinner", null, null, null, null, 0));
        spendings.add(new Spending(5, "lunch", null, null, null, null, 0));
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void execute_missingArg_expectIllegalArgumentExceptionMessage() {
        commandInputForTest("delete", incomes, spendings);
        assertEquals(TAB + INCORRECT_PARAMS_NUMBER + DELETE_COMMAND_FORMAT
                + System.lineSeparator(), outputStreamCaptor.toString());
    }

    @Test
    void execute_validInput_successfullyDeleted() {
        commandInputForTest("delete income 1", incomes, spendings);
        assertEquals("Successfully deleted!", outputStreamCaptor.toString().trim());
        assertEquals(0, incomes.size());
    }

    @Test
    void execute_invalidIndex_expectIllegalArgumentExceptionMessage() {
        commandInputForTest("delete income a", incomes, spendings);
        assertEquals(TAB + INDEX_NOT_INTEGER + DELETE_COMMAND_FORMAT
                + System.lineSeparator(), outputStreamCaptor.toString());
        assertEquals(1, incomes.size());
    }

    @Test
    void execute_indexOutOfBounds_expectIllegalArgumentExceptionMessage() {
        commandInputForTest("delete income 10", incomes, spendings);
        assertEquals(INDEX_OUT_OF_BOUNDS, outputStreamCaptor.toString().trim());
        assertEquals(1, incomes.size());
    }

    @AfterEach
    void tearDown() {
        incomes.clear();
        spendings.clear();
        System.setOut(standardOut);
    }
}
