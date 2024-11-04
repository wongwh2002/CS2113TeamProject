package seedu.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classes.Constants.ADD_COMMAND_FORMAT;
import static seedu.classes.Constants.INCORRECT_DATE_FORMAT;
import static seedu.classes.Constants.INVALID_AMOUNT;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.MISSING_AMOUNT;
import static seedu.classes.Constants.MISSING_AMOUNT_AND_DESCRIPTION;
import static seedu.classes.Constants.MISSING_DESCRIPTION;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Constants.VALID_TEST_DATE;
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
        String expectedOutput = "macs - 10 - " + VALID_TEST_DATE;
        commandInputForTest("add spending 10 macs", incomes, spendings);
        assertEquals(expectedOutput, spendings.get(0).toString());
    }

    @Test
    void execute_correctIncomeWithoutDateInput_success() {
        String expectedOutput = "dishwasher - 1500 - " + VALID_TEST_DATE;
        commandInputForTest("add income 1500 dishwasher", incomes, spendings);
        assertEquals(expectedOutput, incomes.get(0).toString());
    }

    @Test
    void execute_correctSpendingWithDateInput_success() {
        commandInputForTest("add spending 10 macs /2024-10-10", incomes, spendings);
        assertEquals("macs - 10 - 2024-10-10", spendings.get(0).toString());
    }

    @Test
    void execute_correctIncomeWithDateInput_success() {
        commandInputForTest("add income 1500 dishwasher /2024-10-10", incomes, spendings);
        assertEquals("dishwasher - 1500 - 2024-10-10", incomes.get(0).toString());
    }

    @Test
    void execute_correctSpendingWithTag_success() {
        String expectedOutput = "movie ticket - 10 - 2024-10-10 - Tag: entertainment";
        commandInputForTest("add spending 10 movie ticket /2024-10-10/ *entertainment*", incomes, spendings);
        assertEquals(expectedOutput, spendings.get(0).toString());
    }

    @Test
    void execute_correctIncomeWithTag_success() {
        String expectedOutput = "part time - 10 - 2024-10-10 - Tag: job";
        commandInputForTest("add income 10 part time /2024-10-10/ *job*", incomes, spendings);
        assertEquals(expectedOutput, incomes.get(0).toString());
    }

    @Test
    void execute_missingAmountSpendingInput_noSpendingAdded() {
        commandInputForTest("add spending", incomes, spendings);
        assertEquals(TAB + MISSING_AMOUNT_AND_DESCRIPTION + ADD_COMMAND_FORMAT
                        + System.lineSeparator(), outContent.toString());
    }

    @Test
    void execute_stringAmountSpendingInput_noSpendingAdded() {
        commandInputForTest("add spending randomPrice macs", incomes, spendings);
        assertEquals(TAB + MISSING_AMOUNT + ADD_COMMAND_FORMAT +
                        System.lineSeparator(), outContent.toString());
    }

    @Test
    void execute_negativeAmountSpendingInput_noSpendingAdded() {
        commandInputForTest("add spending -1 macs", incomes, spendings);
        assertEquals(TAB + INVALID_AMOUNT + ADD_COMMAND_FORMAT
                        + System.lineSeparator(), outContent.toString());
    }

    @Test
    void execute_missingAmountIncomeInput_noIncomeAdded(){
        commandInputForTest("add income", incomes, spendings);
        assertEquals(TAB + MISSING_AMOUNT_AND_DESCRIPTION + ADD_COMMAND_FORMAT
                        + System.lineSeparator(), outContent.toString());
    }

    @Test
    void execute_invalidAmountIncomeInput_noIncomeAdded(){
        commandInputForTest("add income randomNum salary", incomes, spendings);
        assertEquals(TAB + MISSING_AMOUNT + ADD_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    void execute_missingDescriptionSpendingInput_noSpendingAdded(){
        commandInputForTest("add spending 1000", incomes, spendings);
        assertEquals(TAB + MISSING_DESCRIPTION + ADD_COMMAND_FORMAT
                        + System.lineSeparator(), outContent.toString());
    }

    @Test
    void execute_missingDescriptionIncomeInput_noIncomeAdded(){
        commandInputForTest("add income 1000", incomes, spendings);
        assertEquals(TAB + MISSING_DESCRIPTION + ADD_COMMAND_FORMAT
                        + System.lineSeparator(), outContent.toString());
    }

    @Test
    void execute_invalidDateInput_noIncomeAdded() {
        commandInputForTest("add income 1000 part-time /2024/10/10", incomes, spendings);
        assertEquals(TAB + INCORRECT_DATE_FORMAT + ADD_COMMAND_FORMAT
                        + System.lineSeparator(), outContent.toString());
    }

    @Test
    void execute_invalidDateInput_noSpendingAdded() {
        commandInputForTest("add spending 1000 ipad /2024/10/10", incomes, spendings);
        assertEquals(TAB + INCORRECT_DATE_FORMAT + ADD_COMMAND_FORMAT
                        + System.lineSeparator(), outContent.toString());
    }

    @Test
    void execute_wrongOrderUnknownCommand_unknownCommandMessage() {
        commandInputForTest("blah add", incomes, spendings);
        assertEquals(TAB + "Unknown command" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void execute_validCommandOverspend_overspendMessage() {
        commandInputForTest("add spending 10 macs", incomes, spendings);
        assertEquals(TAB + "Entry successfully added!" + System.lineSeparator()
                + TAB + "!!! You have overspent your daily by: 10.0 !!!" + System.lineSeparator()
                + TAB + "!!! You have overspent your monthly by: 10.0 !!!" + System.lineSeparator()
                + TAB + "!!! You have overspent your yearly by: 10.0 !!!" + System.lineSeparator(),
                outContent.toString());
    }
    
    @Test
    void execute_invalidCategory_invalidCategoryMessage() {
        commandInputForTest("add spendings 10 lunch", incomes, spendings);
        assertEquals(TAB + INVALID_CATEGORY + ADD_COMMAND_FORMAT + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    void execute_missingAmountWithDescription_missingAmountMesage() {
        commandInputForTest("add spending lunch", incomes, spendings);
        assertEquals(TAB + MISSING_AMOUNT + ADD_COMMAND_FORMAT + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    void execute_wrongFormat_wrongFormatMessage() {
        commandInputForTest("add spending lunch 10", incomes, spendings);
        assertEquals(TAB + ADD_COMMAND_FORMAT + System.lineSeparator(),
                outContent.toString());
    }
}
