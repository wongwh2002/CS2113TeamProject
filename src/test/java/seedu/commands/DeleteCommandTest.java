package seedu.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.classes.Constants;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        DeleteCommand c = new DeleteCommand("delete");
        c.execute(incomes, spendings);
        assertEquals(Constants.TAB + Constants.INCORRECT_PARAMS_NUMBER + Constants.DELETE_COMMAND_FORMAT
                + System.lineSeparator(), outputStreamCaptor.toString());
    }

    @Test
    void execute_validInput_successfullyDeleted() {
        DeleteCommand c = new DeleteCommand("delete income 1");
        c.execute(incomes, spendings);
        assertEquals("Successfully deleted!", outputStreamCaptor.toString().trim());
        assertEquals(0, incomes.size());
    }

    @Test
    void execute_invalidIndex_expectIllegalArgumentExceptionMessage() {
        DeleteCommand c = new DeleteCommand("delete income a");
        c.execute(incomes, spendings);
        assertEquals(Constants.TAB + Constants.INDEX_NOT_INTEGER + Constants.DELETE_COMMAND_FORMAT
                + System.lineSeparator(), outputStreamCaptor.toString());
        assertEquals(1, incomes.size());
    }

    @Test
    void execute_indexOutOfBounds_expectIllegalArgumentExceptionMessage() {
        DeleteCommand c = new DeleteCommand("delete income 10");
        c.execute(incomes, spendings);
        assertEquals(Constants.INDEX_OUT_OF_BOUNDS, outputStreamCaptor.toString().trim());
        assertEquals(1, incomes.size());
    }

    @AfterEach
    void tearDown() {
        incomes.clear();
        spendings.clear();
        System.setOut(standardOut);
    }
}
