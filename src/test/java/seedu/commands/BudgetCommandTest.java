package seedu.commands;

import seedu.type.IncomeList;
import seedu.type.SpendingList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classes.Constants.AMOUNT_NOT_NUMBER;
import static seedu.classes.Constants.BUDGET_COMMAND_FORMAT;
import static seedu.classes.Constants.ENTER_BUDGET_MESSAGE;
import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.INVALID_FIELD;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Ui.commandInputForTest;

class BudgetCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private final IncomeList incomes = new IncomeList();
    private final SpendingList spendings = new SpendingList();

    @BeforeEach
    public void setUp() {
        spendings.setDailyBudget(100);
        spendings.setMonthlyBudget(1000);
        spendings.setYearlyBudget(10000);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restore() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void execute_setDailyBudget_success() {
        int budget = 1000;

        String userInput = "budget daily " + budget;
        commandInputForTest(userInput, incomes, spendings);

        assertEquals(TAB + "Successfully set daily budget of: " + budget + System.lineSeparator()
                , outContent.toString());
        assertEquals(budget, spendings.getDailyBudget());
    }

    @Test
    public void execute_setMonthlyBudget_success() {
        int budget = 10000;
        String userInput = "budget monthly " + budget;
        commandInputForTest(userInput, incomes, spendings);

        assertEquals(TAB + "Successfully set monthly budget of: " + budget + System.lineSeparator()
                , outContent.toString());
        assertEquals(budget, spendings.getMonthlyBudget());
    }

    @Test
    public void execute_setYearlyBudget_success() {
        int budget = 13210;
        String userInput = "budget yearly " + budget;
        commandInputForTest(userInput, incomes, spendings);

        assertEquals(TAB + "Successfully set yearly budget of: " + budget + System.lineSeparator()
                , outContent.toString());
        assertEquals(budget, spendings.getYearlyBudget());
    }

    @Test
    public void execute_invalidAmount_exceptionThrown() {
        commandInputForTest("budget yearly abc", incomes, spendings);
        assertEquals(TAB + AMOUNT_NOT_NUMBER + BUDGET_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_invalidTimeFrame_exceptionThrown() {
        commandInputForTest("budget notatimeframe 1", incomes, spendings);
        assertEquals(TAB + INVALID_FIELD + BUDGET_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_tooFewInputs_exceptionThrown() {
        commandInputForTest("budget notenoughinputs", incomes, spendings);
        assertEquals(TAB + INCORRECT_PARAMS_NUMBER + BUDGET_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_dailyMoreThanMonthlyBudget_dailyMoreThanMonthlyMessage() {
        commandInputForTest("budget daily 10000", incomes, spendings);
        assertEquals(TAB + "Daily budget should not be larger than monthly budget! " + ENTER_BUDGET_MESSAGE +
                System.lineSeparator() ,outContent.toString());
    }

    @Test
    public void execute_monthlyMoreThanYearlyBudget_monthlyMoreThanYearlyMessage() {
        commandInputForTest("budget monthly 100000", incomes, spendings);
        assertEquals(TAB + "Monthly budget should not be larger than yearly budget! " + ENTER_BUDGET_MESSAGE +
                System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_monthlyLessThanDailyBudget_monthlyLessThanDailyMessage() {
        commandInputForTest("budget monthly 1", incomes, spendings);
        assertEquals(TAB + "Daily budget should not be larger than monthly budget! " + ENTER_BUDGET_MESSAGE +
                System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_yearlyMoreThanMonthlyBudget_yearlyMoreThanMonthlyMessage() {
        commandInputForTest("budget yearly 100", incomes, spendings);
        assertEquals(TAB + "Monthly budget should not be larger than yearly budget! " + ENTER_BUDGET_MESSAGE +
                System.lineSeparator() ,outContent.toString());
    }
}
