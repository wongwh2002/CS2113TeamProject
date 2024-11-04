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
import static seedu.classes.Constants.FIND_COMMAND_FORMAT;
import static seedu.classes.Constants.INCORRECT_DATE_FORMAT;
import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.INVALID_AMOUNT;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.INVALID_FIELD;
import static seedu.classes.Constants.LIST_SEPARATOR;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Constants.VALID_TEST_DATE;


class FindCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private final IncomeList incomes = new IncomeList();
    private final SpendingList spendings = new SpendingList();

    @BeforeEach
    public void setUp() {
        spendings.add(new Spending(1, "girlfriends", VALID_TEST_DATE, "", RecurrenceFrequency.NONE, null, 0));
        spendings.add(new Spending(2, "macdonalds", LocalDate.of(2024, 10, 10), "", RecurrenceFrequency.NONE, null, 0));
        incomes.add(new Income(1, "savings", VALID_TEST_DATE, "", RecurrenceFrequency.NONE, null, 0));
        incomes.add(new Income(2, "dividends", LocalDate.of(2024, 10, 10), "", RecurrenceFrequency.NONE, null, 0));
        incomes.add(new Income(3, "stocks", VALID_TEST_DATE, "wronginput",
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
    public void execute_missingFindDescription_expectIllegalArgumentException() {
        IncomeList emptyIncomes = new IncomeList();
        SpendingList emptySpendings = new SpendingList();
        String userInout = "find";
        Command c = Parser.parseUserInput(userInout);
        c.execute(emptyIncomes, emptySpendings);
        assertEquals(TAB + INCORRECT_PARAMS_NUMBER + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidCategory_expectIllegalArgumentException() {
        String userInout = "find invalidCategory amount 1";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals(TAB + INVALID_CATEGORY + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidField_expectIllegalArgumentException() {
        String userInout = "find spending invalidField 1";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals(TAB + INVALID_FIELD + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_stringFindAmount_expectIllegalArgumentExceptionThrown() {
        String userInout = "find spending amount stringAmount";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals(TAB + AMOUNT_NOT_NUMBER + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_negativeFindAmount_expectIllegalArgumentExceptionThrown() {
        String userInout = "find spending amount -1";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals(TAB + INVALID_AMOUNT + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidDateFormat_expectIllegalInputExceptionThrown() {
        String userInout = "find spending date 2024/10/10";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals(TAB + INCORRECT_DATE_FORMAT + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_findSpendingAmount_success() {
        String userInout = "find spending amount 1";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals("girlfriends" + LIST_SEPARATOR + "1" + LIST_SEPARATOR + VALID_TEST_DATE,
                spendings.get(0).toString());
    }

    @Test
    public void execute_findIncomeAmount_success() {
        String userInout = "find income amount 1";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals("savings" + LIST_SEPARATOR + "1" + LIST_SEPARATOR + VALID_TEST_DATE,
                incomes.get(0).toString());
    }

    @Test
    public void execute_findSpendingDescription_success() {
        String userInout = "find spending description macdonalds";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals("macdonalds" + LIST_SEPARATOR + "2" + LIST_SEPARATOR + "2024-10-10",
                spendings.get(1).toString());
    }

    @Test
    public void execute_findIncomeDescription_success() {
        String userInout = "find income description dividends";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals("dividends" + LIST_SEPARATOR + "2" + LIST_SEPARATOR + "2024-10-10",
                incomes.get(1).toString());
    }

    @Test
    public void execute_findSpendingDate_success() {
        String userInout = "find spending date 2024-10-10";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals("macdonalds" + LIST_SEPARATOR + "2" + LIST_SEPARATOR + "2024-10-10",
                spendings.get(1).toString());
    }

    @Test
    public void execute_findIncomeDate_success() {
        String userInout = "find income date 2024-10-10";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals("dividends" + LIST_SEPARATOR + "2" + LIST_SEPARATOR + "2024-10-10",
                incomes.get(1).toString());
    }

    @Test
    public void execute_findIncomeWithNoMatch_success() {
        String userInout = "find income date 0000-01-01";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals(TAB + "No entries found match the criteria." + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_findSpendingNoMatch_success() {
        String userInout = "find spending date 0000-01-01";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);
        assertEquals(TAB + "No entries found match the criteria." + System.lineSeparator(),
                outContent.toString());
    }
}
