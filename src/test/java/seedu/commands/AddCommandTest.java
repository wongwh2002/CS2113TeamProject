package seedu.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classes.Constants.ADD_COMMAND_FORMAT;
import static seedu.classes.Constants.DATE_NOT_ENCLOSED;
import static seedu.classes.Constants.INVALID_DATE_FORMAT;
import static seedu.classes.Constants.INVALID_AMOUNT;
import static seedu.classes.Constants.INVALID_AMOUNT_MAX;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.LIST_SEPARATOR;
import static seedu.classes.Constants.MISSING_AMOUNT;
import static seedu.classes.Constants.MISSING_AMOUNT_AND_DESCRIPTION;
import static seedu.classes.Constants.MISSING_AMOUNT_DESCRIPTION_CATEGORY;
import static seedu.classes.Constants.MISSING_DESCRIPTION;
import static seedu.classes.Constants.NEXT_LINE;
import static seedu.classes.Constants.RECURRENCE_NOT_ENCLOSED;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Constants.TAG_NOT_ENCLOSED;
import static seedu.classes.Constants.TODAY;
import static seedu.classes.Ui.commandInputForTest;

import seedu.type.IncomeList;
import seedu.type.SpendingList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AddCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private IncomeList incomes = new IncomeList();
    private SpendingList spendings = new SpendingList();

    @BeforeEach
    public void setUp() {
        incomes = new IncomeList();
        spendings = new SpendingList();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restore() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void execute_correctSpendingWithoutDateInput_success() {
        commandInputForTest("add spending 10 macs", incomes, spendings);
        assertEquals("macs" + LIST_SEPARATOR + "10" + LIST_SEPARATOR + TODAY,
                spendings.get(0).toString());
    }

    @Test
    void execute_correctIncomeWithoutDateInput_success() {
        commandInputForTest("add income 1500 dishwasher", incomes, spendings);
        assertEquals("dishwasher" + LIST_SEPARATOR + "1500" + LIST_SEPARATOR + TODAY,
                incomes.get(0).toString());
    }

    @Test
    void execute_correctSpendingWithDateInput_success() {
        commandInputForTest("add spending 10 macs /2024-10-10/", incomes, spendings);
        assertEquals("macs" + LIST_SEPARATOR + "10" + LIST_SEPARATOR + "2024-10-10",
                spendings.get(0).toString());
    }

    @Test
    void execute_correctIncomeWithDateInput_success() {
        commandInputForTest("add income 1500 dishwasher /2024-10-10/", incomes, spendings);
        assertEquals("dishwasher" + LIST_SEPARATOR + "1500" + LIST_SEPARATOR + "2024-10-10",
                incomes.get(0).toString());
    }

    @Test
    void execute_correctSpendingWithTag_success() {
        commandInputForTest("add spending 10 movie ticket /2024-10-10/ *entertainment*", incomes, spendings);
        assertEquals("movie ticket" + LIST_SEPARATOR + "10" + LIST_SEPARATOR + "2024-10-10" + LIST_SEPARATOR
                + "Tag: entertainment", spendings.get(0).toString());
    }

    @Test
    void execute_correctIncomeWithTag_success() {
        commandInputForTest("add income 10 part time /2024-10-10/ *job*", incomes, spendings);
        assertEquals("part time" + LIST_SEPARATOR + "10" + LIST_SEPARATOR + "2024-10-10" + LIST_SEPARATOR + "Tag: job",
                incomes.get(0).toString());
    }

    @Test
    void execute_correctSpendingWithRecurrence_success() {
        commandInputForTest("add spending 10 electric bill *utilities* ~monthly~", incomes, spendings);
        assertEquals("electric bill" + LIST_SEPARATOR + "10" + LIST_SEPARATOR + TODAY + LIST_SEPARATOR
                + "Tag: utilities" + LIST_SEPARATOR + "Recurring: MONTHLY", spendings.get(0).toString());
    }

    @Test
    void execute_correctIncomeWithRecurrence_success() {
        commandInputForTest("add income 10 salary *work* ~monthly~", incomes, spendings);
        assertEquals("salary" + LIST_SEPARATOR + "10" + LIST_SEPARATOR + TODAY + LIST_SEPARATOR
                + "Tag: work" + LIST_SEPARATOR + "Recurring: MONTHLY", incomes.get(0).toString());
    }


    @Test
    void execute_missingAmountAndDescriptionSpendingInput_missingAmountAndDescriptionMessage() {
        commandInputForTest("add spending", incomes, spendings);
        assertEquals(TAB + MISSING_AMOUNT_AND_DESCRIPTION + ADD_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    void execute_missingAmountAndDescriptionIncomeInput_missingAmountAndDescriptionMessage() {
        commandInputForTest("add income", incomes, spendings);
        assertEquals(TAB + MISSING_AMOUNT_AND_DESCRIPTION + ADD_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    void execute_stringAmountSpendingInput_missingAmountMessage() {
        commandInputForTest("add spending randomPrice macs", incomes, spendings);
        assertEquals(TAB + MISSING_AMOUNT + ADD_COMMAND_FORMAT +
                NEXT_LINE, outContent.toString());
    }

    @Test
    void execute_stringAmountIncomeInput_missingAmountMessage() {
        commandInputForTest("add income randomPrice salary", incomes, spendings);
        assertEquals(TAB + MISSING_AMOUNT + ADD_COMMAND_FORMAT +
                NEXT_LINE, outContent.toString());
    }

    @Test
    void execute_missingAmountSpendingInput_missingAmountMessage() {
        commandInputForTest("add spending lunch", incomes, spendings);
        assertEquals(TAB + MISSING_AMOUNT + ADD_COMMAND_FORMAT + NEXT_LINE,
                outContent.toString());
    }

    @Test
    void execute_missingAmountIncomeInput_missingAmountMessage() {
        commandInputForTest("add income salary", incomes, spendings);
        assertEquals(TAB + MISSING_AMOUNT + ADD_COMMAND_FORMAT + NEXT_LINE,
                outContent.toString());
    }

    @Test
    void execute_negativeAmountSpendingInput_invalidAmountMessage() {
        commandInputForTest("add spending -1 macs", incomes, spendings);
        assertEquals(TAB + INVALID_AMOUNT + ADD_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    void execute_negativeAmountIncomeInput_invalidAmountMessage() {
        commandInputForTest("add income -1 stocks", incomes, spendings);
        assertEquals(TAB + INVALID_AMOUNT + ADD_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    void execute_overflowAmountSpendingInput_invalidAmountMaxMessage(){
        commandInputForTest("add spending 10000000.05 house", incomes, spendings);
        assertEquals(TAB + INVALID_AMOUNT_MAX
                + NEXT_LINE, outContent.toString());
    }

    @Test
    void execute_overflowAmountIncomeInput_invalidAmountMaxMessage(){
        commandInputForTest("add income 10000000.05 lottery", incomes, spendings);
        assertEquals(TAB + INVALID_AMOUNT_MAX
                + NEXT_LINE, outContent.toString());
    }

    @Test
    void execute_missingDescriptionSpendingInput_missingDescriptionMessage() {
        commandInputForTest("add spending 1000", incomes, spendings);
        assertEquals(TAB + MISSING_DESCRIPTION + ADD_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    void execute_missingDescriptionIncomeInput_missingDescriptionMessage() {
        commandInputForTest("add income 1000", incomes, spendings);
        assertEquals(TAB + MISSING_DESCRIPTION + ADD_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    void execute_invalidSpendingDateInput_invalidDateMessage() {
        commandInputForTest("add spending 1000 ipad /2024/10/10", incomes, spendings);
        assertEquals(TAB + INVALID_DATE_FORMAT + ADD_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    void execute_invalidIncomeDateInput_invalidDateMessage() {
        commandInputForTest("add income 1000 part-time /2024/10/10", incomes, spendings);
        assertEquals(TAB + INVALID_DATE_FORMAT + ADD_COMMAND_FORMAT
                + NEXT_LINE, outContent.toString());
    }

    @Test
    void execute_validCommandOverspend_overspendMessage() {
        commandInputForTest("add spending 10 macs", incomes, spendings);
        assertEquals(TAB + "Entry successfully added!" + NEXT_LINE
                + TAB + "!!! You have overspent your daily by: 10 !!!" + NEXT_LINE
                + TAB + "!!! You have overspent your monthly by: 10 !!!" + NEXT_LINE
                + TAB + "!!! You have overspent your yearly by: 10 !!!" + NEXT_LINE,
                outContent.toString());
    }

    @Test
    void execute_invalidCategory_invalidCategoryMessage() {
        commandInputForTest("add invalidCategory 10 lunch", incomes, spendings);
        assertEquals(TAB + INVALID_CATEGORY + ADD_COMMAND_FORMAT + NEXT_LINE,
                outContent.toString());
    }

    @Test
    void execute_missingAddArguments_invalidCategoryMessage() {
        commandInputForTest("add", incomes, spendings);
        assertEquals(TAB + MISSING_AMOUNT_DESCRIPTION_CATEGORY + ADD_COMMAND_FORMAT + NEXT_LINE,
                outContent.toString());
    }

    @Test
    void execute_wrongAddFormatSpendingInput_wrongFormatMessage() {
        commandInputForTest("add spending lunch 10", incomes, spendings);
        assertEquals(TAB + ADD_COMMAND_FORMAT + NEXT_LINE,
                outContent.toString());
    }

    @Test
    void execute_wrongAddFormatIncomeInput_wrongFormatMessage() {
        commandInputForTest("add income salary 10", incomes, spendings);
        assertEquals(TAB + ADD_COMMAND_FORMAT + NEXT_LINE,
                outContent.toString());
    }

    @Test
    void execute_improperEnclosedDateSpendingInput_dateNotEnclosedMessage() {
        commandInputForTest("add spending 10 lunch /2024-11-11", incomes, spendings);
        assertEquals(TAB + DATE_NOT_ENCLOSED + ADD_COMMAND_FORMAT + NEXT_LINE,
                outContent.toString());
    }

    @Test
    void execute_improperEnclosedDateIncomeInput_dateNotEnclosedMessage() {
        commandInputForTest("add income 10 salary /2024-11-11", incomes, spendings);
        assertEquals(TAB + DATE_NOT_ENCLOSED + ADD_COMMAND_FORMAT + NEXT_LINE,
                outContent.toString());
    }

    @Test
    void execute_improperEnclosedTagSpendingInput_tagNotEnclosedMessage() {
        commandInputForTest("add spending 10 lunch *food", incomes, spendings);
        assertEquals(TAB + TAG_NOT_ENCLOSED + ADD_COMMAND_FORMAT + NEXT_LINE,
                outContent.toString());
    }

    @Test
    void execute_improperEnclosedTagIncomeInput_tagNotEnclosedMessage() {
        commandInputForTest("add income 10 salary *work", incomes, spendings);
        assertEquals(TAB + TAG_NOT_ENCLOSED + ADD_COMMAND_FORMAT + NEXT_LINE,
                outContent.toString());
    }

    @Test
    void execute_improperEnclosedRecurrenceSpendingInput_recurrenceNotEnclosedMessage() {
        commandInputForTest("add spending 10 lunch ~daily", incomes, spendings);
        assertEquals(TAB + RECURRENCE_NOT_ENCLOSED + ADD_COMMAND_FORMAT + NEXT_LINE,
                outContent.toString());
    }

    @Test
    void execute_improperEnclosedRecurrenceIncomeInput_recurrenceNotEnclosedMessage() {
        commandInputForTest("add income 10 salary ~monthly", incomes, spendings);
        assertEquals(TAB + RECURRENCE_NOT_ENCLOSED + ADD_COMMAND_FORMAT + NEXT_LINE,
                outContent.toString());
    }
}
