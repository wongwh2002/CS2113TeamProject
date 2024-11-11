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

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.classes.Constants.AMOUNT_NOT_NUMBER;
import static seedu.classes.Constants.EDIT_COMMAND_FORMAT;
import static seedu.classes.Constants.INDEX_NOT_INTEGER;
import static seedu.classes.Constants.INDEX_OUT_OF_BOUNDS;
import static seedu.classes.Constants.INVALID_AMOUNT;
import static seedu.classes.Constants.OVER_MAX_ENTRY_AMOUNT;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.INVALID_FIELD;
import static seedu.classes.Constants.LIST_SEPARATOR;
import static seedu.classes.Constants.NEXT_LINE;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.TODAY;
import static seedu.classes.Ui.commandInputForTest;


class EditCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private final IncomeList incomes = new IncomeList();
    private final SpendingList spendings = new SpendingList();

    @BeforeEach
    public void setUp() {
        spendings.add(new Spending(10, "girlfriends", TODAY, "", RecurrenceFrequency.NONE, null, 0));
        spendings.add(new Spending(10, "macdonalds", TODAY, "", RecurrenceFrequency.NONE, null, 0));
        incomes.add(new Income(10, "savings", TODAY.minusDays(7), "", RecurrenceFrequency.NONE, null, 0));
        incomes.add(new Income(10, "dividends", TODAY.minusDays(6), "", RecurrenceFrequency.NONE, null, 0));
        incomes.add(new Income(10, "stocks", TODAY.minusDays(5), "wronginput",
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
    public void execute_missingEditArguments_incorrectParamsNumberMessage() {
        IncomeList emptyIncomes = new IncomeList();
        SpendingList emptySpendings = new SpendingList();
        commandInputForTest("edit", emptyIncomes, emptySpendings);
        assertEquals(TAB + INCORRECT_PARAMS_NUMBER + EDIT_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    public void execute_invalidCategory_invalidCategoryMessage() {
        commandInputForTest("edit invalidCategory 1 amount 1", incomes, spendings);
        assertEquals(TAB + INVALID_CATEGORY + EDIT_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    public void execute_indexOutOfBoundsSpendingInput_indexOutOfBoundsMessage() {
        commandInputForTest("edit spending 5 amount 1", incomes, spendings);
        assertEquals(TAB + INDEX_OUT_OF_BOUNDS + NEXT_LINE, outContent.toString());
    }

    @Test
    public void execute_indexOutOfBoundsIncomeInput_indexOutOfBoundsMessage() {
        commandInputForTest("edit income 5 amount 1", incomes, spendings);
        assertEquals(TAB + INDEX_OUT_OF_BOUNDS + NEXT_LINE, outContent.toString());
    }

    @Test
    public void execute_stringIndexSpendingInput_indexNotIntegerMessage() {
        commandInputForTest("edit spending string amount 1", incomes, spendings);
        assertEquals(TAB + INDEX_NOT_INTEGER + NEXT_LINE, outContent.toString());
    }

    @Test
    public void execute_stringIndexIncomeInput_indexNotIntegerMessage() {
        commandInputForTest("edit income string amount 1", incomes, spendings);
        assertEquals(TAB + INDEX_NOT_INTEGER + NEXT_LINE, outContent.toString());
    }

    @Test
    public void execute_invalidEditFieldSpendingInput_invalidFieldMessage() {
        commandInputForTest("edit spending 1 invalidField 1", incomes, spendings);
        assertEquals(TAB + INVALID_FIELD + EDIT_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    public void execute_invalidEditFieldIncomeInput_invalidFieldMessage() {
        commandInputForTest("edit income 1 invalidField 1", incomes, spendings);
        assertEquals(TAB + INVALID_FIELD + EDIT_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    public void execute_stringEditAmountSpendingInput_amountNotNumberMessage() {
        commandInputForTest("edit spending 1 amount notanint", incomes, spendings);
        assertEquals(TAB + AMOUNT_NOT_NUMBER + EDIT_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    public void execute_stringEditAmountIncomeInput_amountNotNumberMessage() {
        commandInputForTest("edit income 1 amount notanint", incomes, spendings);
        assertEquals(TAB + AMOUNT_NOT_NUMBER + EDIT_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    public void execute_negativeEditAmountSpendingInput_invalidAmountMessage() {
        commandInputForTest("edit spending 1 amount -1", incomes, spendings);
        assertEquals(TAB + INVALID_AMOUNT + EDIT_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    public void execute_negativeEditAmountIncomeInput_invalidAmountMessage() {
        commandInputForTest("edit income 1 amount -1", incomes, spendings);
        assertEquals(TAB + INVALID_AMOUNT + EDIT_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    public void execute_overflowEditAmountSpendingInput_invalidAmountMaxMessage() {
        commandInputForTest("edit spending 1 amount 100000000", incomes, spendings);
        assertEquals(TAB + OVER_MAX_ENTRY_AMOUNT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    public void execute_overflowEditAmountIncomeInput_invalidAmountMaxMessage() {
        commandInputForTest("edit income 1 amount 100000000", incomes, spendings);
        assertEquals(TAB + OVER_MAX_ENTRY_AMOUNT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    public void execute_editSpendingAmount_success() {
        double expectedTotalAfterEdit = spendings.getTotal() - spendings.get(0).getAmount() + 1.05;
        commandInputForTest("edit spending 1 amount 1.05", incomes, spendings);
        assertEquals("girlfriends" + LIST_SEPARATOR + "1.05" + LIST_SEPARATOR + TODAY,
                spendings.get(0).toString());
        assertEquals(expectedTotalAfterEdit, spendings.getTotal());
    }

    @Test
    public void execute_editIncomeAmount_success() {
        double expectedTotalAfterEdit = incomes.getTotal() - incomes.get(0).getAmount() + 1;
        commandInputForTest("edit income 1 amount 1", incomes, spendings);
        assertEquals("savings" + LIST_SEPARATOR + "1" + LIST_SEPARATOR + TODAY.minusDays(7),
                incomes.get(0).toString());
        assertEquals(expectedTotalAfterEdit, incomes.getTotal());
    }

    @Test
    public void execute_editSpendingDescription_success() {
        commandInputForTest("edit spending 1 description test", incomes, spendings);
        assertEquals("test" + LIST_SEPARATOR + "10" + LIST_SEPARATOR + TODAY,
                spendings.get(0).toString());
    }

    @Test
    public void execute_editIncomeDescription_success() {
        commandInputForTest("edit income 1 description test", incomes, spendings);
        assertEquals("test" + LIST_SEPARATOR + "10" + LIST_SEPARATOR +
                TODAY.minusDays(7),
                incomes.get(0).toString());
    }

    @Test
    public void execute_editSpendingDate_success() {
        commandInputForTest("edit spending 2 date 2024-10-10", incomes, spendings);
        assertEquals("macdonalds" + LIST_SEPARATOR + "10" + LIST_SEPARATOR + "2024-10-10",
                spendings.get(1).toString());
    }

    @Test
    public void execute_editIncomeDate_success() {
        commandInputForTest("edit income 2 date 2024-10-10", incomes, spendings);
        assertEquals("dividends" + LIST_SEPARATOR + "10" + LIST_SEPARATOR + "2024-10-10",
                incomes.get(1).toString());
    }

    @Test
    public void execute_editSpendingTag_success() {
        commandInputForTest("edit spending 2 tag food", incomes, spendings);
        assertEquals("macdonalds" + LIST_SEPARATOR + "10" + LIST_SEPARATOR + TODAY +
                        LIST_SEPARATOR + "Tag: food",
                spendings.get(1).toString());
    }

    @Test
    public void execute_editIncomeTag_success() {
        commandInputForTest("edit income 3 tag investments", incomes, spendings);
        assertEquals("stocks" + LIST_SEPARATOR + "10" + LIST_SEPARATOR + TODAY.minusDays(5) +
                LIST_SEPARATOR + "Tag: investments",
                incomes.get(2).toString());
    }
}
