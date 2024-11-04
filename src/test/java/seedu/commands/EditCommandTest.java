package seedu.commands;

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

import static seedu.classes.Constants.AMOUNT_NOT_NUMBER;
import static seedu.classes.Constants.EDIT_COMMAND_FORMAT;
import static seedu.classes.Constants.INDEX_NOT_INTEGER;
import static seedu.classes.Constants.INDEX_OUT_OF_BOUNDS;
import static seedu.classes.Constants.INVALID_AMOUNT;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.INVALID_FIELD;
import static seedu.classes.Constants.LIST_SEPARATOR;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.VALID_TEST_DATE;


class EditCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private final IncomeList incomes = new IncomeList();
    private final SpendingList spendings = new SpendingList();

    @BeforeEach
    public void setUp() {
        spendings.add(new Spending(10, "girlfriends", VALID_TEST_DATE, "", RecurrenceFrequency.NONE, null, 0));
        spendings.add(new Spending(10, "macdonalds", VALID_TEST_DATE, "", RecurrenceFrequency.NONE, null, 0));
        incomes.add(new Income(10, "savings", VALID_TEST_DATE, "", RecurrenceFrequency.NONE, null, 0));
        incomes.add(new Income(10, "dividends", VALID_TEST_DATE, "", RecurrenceFrequency.NONE, null, 0));
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
        Command c = Parser.parseUserInput(userInout);
        c.execute(emptyIncomes, emptySpendings);
        assertEquals(TAB + INCORRECT_PARAMS_NUMBER + EDIT_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidInput_expectIllegalArgumentException() {
        String userInout = "edit notspendingincome 1 amount 1";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals(TAB + INVALID_CATEGORY + EDIT_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidInputIndex_expectIndexOutOfBoundsException() {
        String userInout = "edit spending 5 amount 1";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals(TAB + INDEX_OUT_OF_BOUNDS + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidStringIndex_expectIndexOutOfBoundsException() {
        String userInout = "edit spending string amount 1";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals(TAB + INDEX_NOT_INTEGER + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidEditType_expectIllegalArgumentException() {
        String userInout = "edit spending 1 notamountdescription 1";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals(TAB + INVALID_FIELD + EDIT_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidEditAmount_expectIllegalArgumentExceptionThrown() {
        String userInout = "edit spending 1 amount notanint";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals(TAB + AMOUNT_NOT_NUMBER + EDIT_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_negativeEditAmount_expectIllegalArgumentExceptionThrown() {
        String userInout = "edit spending 1 amount -1";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals(TAB + INVALID_AMOUNT + EDIT_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_editSpendingAmount_success() {
        String userInout = "edit spending 1 amount 1.05";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals("girlfriends" + LIST_SEPARATOR + "1.05" + LIST_SEPARATOR + VALID_TEST_DATE,
                spendings.get(0).toString());
    }

    @Test
    public void execute_editIncomeAmount_success() {
        String userInout = "edit income 1 amount 1";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals("savings" + LIST_SEPARATOR + "1" + LIST_SEPARATOR + VALID_TEST_DATE,
                incomes.get(0).toString());
    }


    @Test
    public void execute_editIncomeDate_success() {
        String userInout = "edit income 2 date 2024-10-10";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals("dividends" + LIST_SEPARATOR + "10" + LIST_SEPARATOR + "2024-10-10",
                incomes.get(1).toString());
    }

    @Test
    public void execute_editSpendingDate_success() {
        String userInout = "edit spending 2 date 2024-10-10";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals("macdonalds" + LIST_SEPARATOR + "10" + LIST_SEPARATOR + "2024-10-10",
                spendings.get(1).toString());
    }

    @Test
    public void execute_editTag_success() {
        String userInout = "edit income 3 tag investments";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals("stocks" + LIST_SEPARATOR + "10" + LIST_SEPARATOR + "2024-10-10" +
                        LIST_SEPARATOR + "Tag: investments",
                incomes.get(2).toString());
    }
}
