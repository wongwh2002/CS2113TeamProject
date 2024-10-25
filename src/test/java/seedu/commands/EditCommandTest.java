package seedu.commands;

import seedu.classes.Constants;
import seedu.classes.Parser;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EditCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private final IncomeList incomes = new IncomeList();
    private final SpendingList spendings = new SpendingList();
    private final LocalDate currentDate = LocalDate.now();

    @BeforeEach
    public void setUp() {
        spendings.add(new Spending(10, "girlfriends", currentDate, "", RecurrenceFrequency.NONE, null, 0));
        spendings.add(new Spending(10, "macdonalds", currentDate, "", RecurrenceFrequency.NONE, null, 0));
        incomes.add(new Income(10, "savings", currentDate, "", RecurrenceFrequency.NONE, null, 0));
        incomes.add(new Income(10, "dividends", currentDate, "", RecurrenceFrequency.NONE, null, 0));
        incomes.add(new Income(10, "stocks", LocalDate.of(2024, 10, 10), "wronginput",
                RecurrenceFrequency.NONE, null, 0));
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restore() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void execute_missingEditDescription_expectIllegalArgumentException() {
        IncomeList emptyIncomes = new IncomeList();
        SpendingList emptySpendings = new SpendingList();
        String userInout = "edit";
        Command c = Parser.parse(userInout);
        c.execute(emptyIncomes, emptySpendings);
        assertEquals(Constants.TAB + Constants.INCORRECT_PARAMS_NUMBER + Constants.EDIT_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidInput_expectIllegalArgumentException() {
        String userInout = "edit notspendingincome 1 amount 1";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals(Constants.TAB + Constants.INVALID_CATEGORY + Constants.EDIT_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidInputIndex_expectIndexOutOfBoundsException() {
        String userInout = "edit spending 5 amount 1";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals(Constants.TAB + Constants.INDEX_OUT_OF_BOUNDS + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidEditType_expectIllegalArgumentException() {
        String userInout = "edit spending 1 notamountdescription 1";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals(Constants.TAB + Constants.INVALID_FIELD + Constants.EDIT_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidEditAmount_expectIllegalArgumentExceptionThrown() {
        String userInout = "edit spending 1 amount notanint";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals(Constants.TAB + Constants.INVALID_AMOUNT + Constants.EDIT_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_negativeEditAmount_expectIllegalArgumentExceptionThrown() {
        String userInout = "edit spending 1 amount -1";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals(Constants.TAB + Constants.INVALID_AMOUNT + Constants.EDIT_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_editSpendingAmount_success() {
        String userInout = "edit spending 1 amount 1";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals("girlfriends" + Constants.LIST_SEPARATOR + "1" + Constants.LIST_SEPARATOR + currentDate,
                spendings.get(0).toString());
    }

    @Test
    public void execute_editIncomeAmount_success() {
        String userInout = "edit income 1 amount 1";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals("savings" + Constants.LIST_SEPARATOR + "1" + Constants.LIST_SEPARATOR + currentDate,
                incomes.get(0).toString());
    }


    @Test
    public void execute_editIncomeDate_success() {
        String userInout = "edit income 2 date 2024-10-10";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals("dividends" + Constants.LIST_SEPARATOR + "10" + Constants.LIST_SEPARATOR + "2024-10-10",
                incomes.get(1).toString());
    }

    @Test
    public void execute_editSpendingDate_success() {
        String userInout = "edit spending 2 date 2024-10-10";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals("macdonalds" + Constants.LIST_SEPARATOR + "10" + Constants.LIST_SEPARATOR + "2024-10-10",
                spendings.get(1).toString());
    }

    @Test
    public void execute_editTag_success() {
        String userInout = "edit income 3 tag investments";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals("stocks" + Constants.LIST_SEPARATOR + "10" + Constants.LIST_SEPARATOR + "2024-10-10" +
                        Constants.LIST_SEPARATOR + "investments",
                incomes.get(2).toString());
    }
}
