package seedu.commands;

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
import static seedu.classes.Constants.INVALID_AMOUNT_RANGE;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.INVALID_DATE_RANGE;
import static seedu.classes.Constants.INVALID_FIELD;
import static seedu.classes.Constants.LONG_FIND_FROM_VALUE;
import static seedu.classes.Constants.LONG_FIND_SPECIFIC_VALUE;
import static seedu.classes.Constants.LONG_FIND_TO_VALUE;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Constants.VALID_TEST_DATE;
import static seedu.classes.Ui.commandInputForTest;


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
        commandInputForTest("find", emptyIncomes, emptySpendings);
        assertEquals(TAB + INCORRECT_PARAMS_NUMBER + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidCategory_expectIllegalArgumentException() {
        commandInputForTest("find invalidCategory amount 1", incomes, spendings);
        assertEquals(TAB + INVALID_CATEGORY + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidField_expectIllegalArgumentException() {
        commandInputForTest("find spending invalidField 1", incomes, spendings);
        assertEquals(TAB + INVALID_FIELD + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_stringFindAmount_expectIllegalArgumentExceptionThrown() {
        commandInputForTest("find spending amount stringAmount", incomes, spendings);
        assertEquals(TAB + AMOUNT_NOT_NUMBER + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_negativeFindAmount_expectIllegalArgumentExceptionThrown() {
        commandInputForTest("find spending amount -1", incomes, spendings);
        assertEquals(TAB + INVALID_AMOUNT + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidDateFormat_expectIllegalInputExceptionThrown() {
        commandInputForTest("find spending date 2024/10/10", incomes, spendings);
        assertEquals(TAB + INCORRECT_DATE_FORMAT + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_longSpecificValue_expectIllegalInputExceptionThrown() {
        commandInputForTest("find spending date 2024/10/10 extraString", incomes, spendings);
        assertEquals(TAB + LONG_FIND_SPECIFIC_VALUE + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_longFromValue_expectIllegalInputExceptionThrown() {
        commandInputForTest("find spending date 2024/10/10 extraString to 2024-12-12", incomes, spendings);
        assertEquals(TAB + LONG_FIND_FROM_VALUE + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_longToValue_expectIllegalInputExceptionThrown() {
        commandInputForTest("find spending date 2024/10/10 to 2024-12-12 extraString", incomes, spendings);
        assertEquals(TAB + LONG_FIND_TO_VALUE + FIND_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_findSpendingAmount_success() {
        commandInputForTest("find spending amount 1", incomes, spendings);
        assertEquals(TAB + "Here are the matching results:" + System.lineSeparator()
                + TAB + "2: " + spendings.get(1).toString() + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_findIncomeAmount_success() {
        commandInputForTest("find income amount 1", incomes, spendings);
        assertEquals(TAB + "Here are the matching results:" + System.lineSeparator()
                + TAB + "2: " + incomes.get(1).toString() + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_findSpendingAmountRange_success() {
        commandInputForTest("find spending amount 1 to 2", incomes, spendings);
        assertEquals(TAB + "Here are the matching results:" + System.lineSeparator()
                + TAB + "1: " + spendings.get(0).toString() + System.lineSeparator()
                + TAB + "2: " + spendings.get(1).toString() + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_findIncomeAmountRange_success() {
        commandInputForTest("find income amount 1 to 2", incomes, spendings);
        assertEquals(TAB + "Here are the matching results:" + System.lineSeparator()
                + TAB + "1: " + incomes.get(0).toString() + System.lineSeparator()
                + TAB + "2: " + incomes.get(1).toString() + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_invalidAmountRange_expectIllegalInputExceptionThrown() {
        commandInputForTest("find income amount 2 to 1", incomes, spendings);
        assertEquals(TAB + INVALID_AMOUNT_RANGE + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_findSpendingDescription_success() {
        commandInputForTest("find spending description macdonalds", incomes, spendings);
        assertEquals(TAB + "Here are the matching results:" + System.lineSeparator()
                + TAB + "1: " + spendings.get(0).toString() + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_findIncomeDescription_success() {
        commandInputForTest("find income description dividends", incomes, spendings);
        assertEquals(TAB + "Here are the matching results:" + System.lineSeparator()
                + TAB + "1: " + incomes.get(0).toString() + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_findSpendingDate_success() {
        commandInputForTest("find spending date 2024-10-10", incomes, spendings);
        assertEquals(TAB + "Here are the matching results:" + System.lineSeparator()
                + TAB + "1: " + spendings.get(0).toString() + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_findIncomeDate_success() {
        commandInputForTest("find income date 2024-10-10", incomes, spendings);
        assertEquals(TAB + "Here are the matching results:" + System.lineSeparator()
                + TAB + "1: " + incomes.get(0).toString() + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_findSpendingDateRange_success() {
        commandInputForTest("find spending date 2024-10-10 to " + VALID_TEST_DATE, incomes, spendings);
        assertEquals(TAB + "Here are the matching results:" + System.lineSeparator()
                + TAB + "1: " + spendings.get(0).toString() + System.lineSeparator()
                + TAB + "2: " + spendings.get(1).toString() + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_findIncomeDateRange_success() {
        commandInputForTest("find income date 2024-10-10 to " + VALID_TEST_DATE, incomes, spendings);
        assertEquals(TAB + "Here are the matching results:" + System.lineSeparator()
                + TAB + "1: " + incomes.get(0).toString() + System.lineSeparator()
                + TAB + "2: " + incomes.get(1).toString() + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_invalidDateRange_expectIllegalInputExceptionThrown() {
        commandInputForTest("find income date 2024-10-10 to 2024-10-09", incomes, spendings);
        assertEquals(TAB + INVALID_DATE_RANGE + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_findIncomeWithNoMatch_success() {
        commandInputForTest("find income date 0000-01-01", incomes, spendings);
        assertEquals(TAB + "No entries found match the criteria." + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_findSpendingNoMatch_success() {
        commandInputForTest("find spending date 0000-01-01", incomes, spendings);
        assertEquals(TAB + "No entries found match the criteria." + System.lineSeparator(),
                outContent.toString());
    }
}
