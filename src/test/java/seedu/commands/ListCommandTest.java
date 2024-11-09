package seedu.commands;

import seedu.classes.Ui;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.SpendingList;
import seedu.type.Spending;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.LIST_COMMAND_FORMAT;
import static seedu.classes.Constants.PRINTING_TIME_RANGE_MESSAGE_INCOMES;
import static seedu.classes.Constants.PRINTING_TIME_RANGE_MESSAGE_SPENDINGS;
import static seedu.classes.Constants.SEPARATOR;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Constants.SELECT_TIME_RANGE_MESSAGE_INCOMES;
import static seedu.classes.Constants.SELECT_TIME_RANGE_MESSAGE_SPENDINGS;
import static seedu.classes.Constants.VALID_TEST_DATE;
import static seedu.classes.Ui.commandInputForTest;

class ListCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private final IncomeList incomes = new IncomeList();
    private final SpendingList spendings = new SpendingList();

    @BeforeEach
    public void setUp() {
        spendings.add(new Spending(10, "girlfriends", VALID_TEST_DATE, "", null, null, 0));
        spendings.add(new Spending(10, "macdonalds", VALID_TEST_DATE, "food", null, null, 0));
        incomes.add(new Income(10, "savings", VALID_TEST_DATE, "", null, null, 0));
        incomes.add(new Income(10, "dividends", VALID_TEST_DATE, "investment", null, null, 0));
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restore() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void execute_emptyList_success() {
        IncomeList emptyIncomes = new IncomeList();
        SpendingList emptySpendings = new SpendingList();
        commandInputForTest("list", emptyIncomes, emptySpendings);

        assertEquals(TAB + "Spendings" + System.lineSeparator() +
                TAB + "Total spendings: 0" + System.lineSeparator() +
                TAB + "Incomes" + System.lineSeparator() +
                TAB + "Total incomes: 0" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_listEmptyTags_expectWiagiInvalidInputException() {
        IncomeList emptyIncomes = new IncomeList();
        SpendingList emptySpendings = new SpendingList();
        commandInputForTest("list tags", emptyIncomes, emptySpendings);

        assertEquals(TAB + "No tags found. Please input more tags!" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_listSpecificEmptyTags_expectWiagiInvalidInputException() {
        IncomeList emptyIncomes = new IncomeList();
        SpendingList emptySpendings = new SpendingList();
        commandInputForTest("list tags tag", emptyIncomes, emptySpendings);

        assertEquals(TAB + "No entries with tag: tag. Please input tags first!"
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_allLists_success() {
        commandInputForTest("list", incomes, spendings);

        assertEquals(TAB + "Spendings" + System.lineSeparator() +
                TAB + "1. girlfriends - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                TAB + "2. macdonalds - 10 - " + VALID_TEST_DATE + " - Tag: food" + System.lineSeparator() +
                TAB + "Total spendings: 20" + System.lineSeparator() +
                TAB + "Incomes" + System.lineSeparator() +
                TAB + "1. savings - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                TAB + "2. dividends - 10 - " + VALID_TEST_DATE + " - Tag: investment" + System.lineSeparator() +
                TAB + "Total incomes: 20" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_listIncome_success() {
        Ui.userInputForTest("1");
        commandInputForTest("list incomes", incomes, spendings);

        assertEquals(TAB + SELECT_TIME_RANGE_MESSAGE_INCOMES + System.lineSeparator() +
                TAB + SEPARATOR + System.lineSeparator() +
                TAB + "Incomes" + System.lineSeparator() +
                TAB + "1. savings - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                TAB + "2. dividends - 10 - " + VALID_TEST_DATE + " - Tag: investment" + System.lineSeparator() +
                TAB + "Total incomes: 20" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_randomInput_expectWiagiInvalidInputException() {
        commandInputForTest("list 1234", incomes, spendings);
        assertEquals(TAB + INVALID_CATEGORY + LIST_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_tooManySpendingsInputs_expectWiagiInvalidInputException() {
        commandInputForTest("list spendings incomes", incomes, spendings);
        assertEquals(TAB + INCORRECT_PARAMS_NUMBER + LIST_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_tooManyIncomesInputs_expectWiagiInvalidInputException() {
        commandInputForTest("list incomes spendings", incomes, spendings);
        assertEquals(TAB + INCORRECT_PARAMS_NUMBER + LIST_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_listTags_success() {
        commandInputForTest("list tags", incomes, spendings);
        assertEquals(TAB + "Tags" + System.lineSeparator() +
                TAB + "1. food" + System.lineSeparator() +
                TAB + "2. investment" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    void execute_listSpecificInvestmentTag_success() {
        commandInputForTest("list tags investment", incomes, spendings);
        assertEquals(TAB + "Tag: investment" + System.lineSeparator() +
                TAB + "Incomes" + System.lineSeparator() +
                TAB + "2. dividends - 10 - " + VALID_TEST_DATE + " - Tag: investment" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    void execute_listSpecificFoodTag_success() {
        commandInputForTest("list tags food", incomes, spendings);
        assertEquals(TAB + "Tag: food" + System.lineSeparator() +
                TAB + "Spendings" + System.lineSeparator() +
                TAB + "2. macdonalds - 10 - " + VALID_TEST_DATE + " - Tag: food" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_listSpendingAllStatistics_correctMessage() {
        Ui.userInputForTest(String.format("1%sY", System.lineSeparator()));
        commandInputForTest("list spendings", incomes, spendings);

        assertEquals(TAB + SELECT_TIME_RANGE_MESSAGE_SPENDINGS + System.lineSeparator() +
                TAB + SEPARATOR + System.lineSeparator() +
                TAB + "List all statistics? [Y/N]:" + System.lineSeparator() +
                TAB + "____________________________________________________________" + System.lineSeparator() +
                TAB + "Spendings" + System.lineSeparator() +
                TAB + "1. girlfriends - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                TAB + "2. macdonalds - 10 - " + VALID_TEST_DATE + " - Tag: food" + System.lineSeparator() +
                TAB + "Total spendings: 20" + System.lineSeparator() +
                TAB + TAB + "Daily spendings: 20" + System.lineSeparator() +
                TAB + TAB + "Daily Budget: 0" + System.lineSeparator() +
                TAB + TAB + "Daily budget left: -20" + System.lineSeparator() +
                TAB + TAB + "Monthly spendings: 20" + System.lineSeparator() +
                TAB + TAB + "Monthly Budget: 0" + System.lineSeparator() +
                TAB + TAB + "Monthly budget left: -20" + System.lineSeparator() +
                TAB + TAB + "Yearly spendings: 20" + System.lineSeparator() +
                TAB + TAB + "Yearly Budget: 0" + System.lineSeparator() +
                TAB + TAB + "Yearly budget left: -20" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_listSpendingNotAllStatistics_correctMessage() {
        Ui.userInputForTest(String.format("1%sN", System.lineSeparator()));
        commandInputForTest("list spendings", incomes, spendings);
        assertEquals(TAB + SELECT_TIME_RANGE_MESSAGE_SPENDINGS + System.lineSeparator() +
                TAB + SEPARATOR + System.lineSeparator() +
                TAB + "List all statistics? [Y/N]:" + System.lineSeparator() +
                TAB + SEPARATOR + System.lineSeparator() +
                TAB + "Spendings" + System.lineSeparator() +
                TAB + "1. girlfriends - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                TAB + "2. macdonalds - 10 - " + VALID_TEST_DATE + " - Tag: food" + System.lineSeparator() +
                TAB + "Total spendings: 20" + System.lineSeparator(),
                outContent.toString());
    }

    private static LocalDate getMondayDate(LocalDate currDate) {
        while (currDate.getDayOfWeek() != DayOfWeek.MONDAY) {
            currDate = currDate.minusDays(1);
        }
        return currDate;
    }

    private static LocalDate getSundayDate(LocalDate currDate) {
        while (currDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
            currDate = currDate.plusDays(1);
        }
        return currDate;
    }

    @Test
    public void execute_listWeeklySpendings_expectWeeklySpendingList() {
        spendings.add(new Spending(10, "lunch", VALID_TEST_DATE.minusDays(7), "", null, null, 0));
        Ui.userInputForTest("2");
        commandInputForTest("list spendings", incomes, spendings);
        LocalDate monday = getMondayDate(VALID_TEST_DATE);
        LocalDate sunday = getSundayDate(VALID_TEST_DATE);
        assertEquals(TAB + SELECT_TIME_RANGE_MESSAGE_SPENDINGS + System.lineSeparator() +
                TAB + SEPARATOR + System.lineSeparator() +
                TAB + PRINTING_TIME_RANGE_MESSAGE_SPENDINGS + monday + " to " +
                sunday + System.lineSeparator() +
                TAB + "2. girlfriends - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                TAB + "3. macdonalds - 10 - " + VALID_TEST_DATE + " - Tag: food" + System.lineSeparator() +
                TAB + "Total: 20" + System.lineSeparator(),
                outContent.toString());
        spendings.remove(2);
    }

    @Test
    public void execute_listBiweeklySpendings_expectBiweeklySpendingList() {
        spendings.add(new Spending(10, "lunch", VALID_TEST_DATE.minusDays(14), "", null, null, 0));
        Ui.userInputForTest("3");
        commandInputForTest("list spendings", incomes, spendings);
        LocalDate lastMonday = getMondayDate(VALID_TEST_DATE.minusDays(7));
        LocalDate sunday = getSundayDate(VALID_TEST_DATE);
        assertEquals(TAB + SELECT_TIME_RANGE_MESSAGE_SPENDINGS + System.lineSeparator() +
                TAB + SEPARATOR + System.lineSeparator() +
                TAB + PRINTING_TIME_RANGE_MESSAGE_SPENDINGS + lastMonday + " to " +
                sunday + System.lineSeparator() +
                TAB + "2. girlfriends - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                TAB + "3. macdonalds - 10 - " + VALID_TEST_DATE + " - Tag: food" + System.lineSeparator() +
                TAB + "Total: 20" + System.lineSeparator(),
                outContent.toString());
        spendings.remove(2);
    }

    @Test
    public void execute_listMonthlySpendings_expectMonthlySpendingList() {
        spendings.add(new Spending(10, "lunch", VALID_TEST_DATE.minusDays(31), "", null, null, 0));
        Ui.userInputForTest("4");
        commandInputForTest("list spendings", incomes, spendings);
        LocalDate monthStart = LocalDate.of(VALID_TEST_DATE.getYear(), VALID_TEST_DATE.getMonth(), 1);
        LocalDate monthEnd = monthStart.plusDays(VALID_TEST_DATE.getMonth().length(VALID_TEST_DATE.isLeapYear()) - 1);
        assertEquals(TAB + SELECT_TIME_RANGE_MESSAGE_SPENDINGS + System.lineSeparator() +
                TAB + SEPARATOR + System.lineSeparator() +
                TAB + PRINTING_TIME_RANGE_MESSAGE_SPENDINGS + monthStart + " to " +
                monthEnd + System.lineSeparator() +
                TAB + "2. girlfriends - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                TAB + "3. macdonalds - 10 - " + VALID_TEST_DATE + " - Tag: food" + System.lineSeparator() +
                TAB + "Total: 20" + System.lineSeparator(),
                outContent.toString());
        spendings.remove(2);
    }

    @Test
    public void execute_listWeeklyIncomes_expectWeeklyIncomeList() {
        incomes.add(new Income(1000, "salary", VALID_TEST_DATE.minusDays(7), "", null, null, 0));
        Ui.userInputForTest("2");
        commandInputForTest("list incomes", incomes, spendings);
        LocalDate monday = getMondayDate(VALID_TEST_DATE);
        LocalDate sunday = getSundayDate(VALID_TEST_DATE);
        assertEquals(TAB + SELECT_TIME_RANGE_MESSAGE_INCOMES + System.lineSeparator() +
                TAB + SEPARATOR + System.lineSeparator() +
                TAB + PRINTING_TIME_RANGE_MESSAGE_INCOMES + monday + " to " +
                sunday + System.lineSeparator() +
                TAB + "2. savings - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                TAB + "3. dividends - 10 - " + VALID_TEST_DATE + " - Tag: investment" + System.lineSeparator() +
                TAB + "Total: 20" + System.lineSeparator(),
                outContent.toString());
        incomes.remove(2);
    }

    @Test
    public void execute_listBiweeklyIncomes_expectBiweeklyIncomeList() {
        incomes.add(new Income(1000, "salary", VALID_TEST_DATE.minusDays(14), "", null, null, 0));
        Ui.userInputForTest("3");
        commandInputForTest("list incomes", incomes, spendings);
        LocalDate lastMonday = getMondayDate(VALID_TEST_DATE.minusDays(7));
        LocalDate sunday = getSundayDate(VALID_TEST_DATE);
        assertEquals(TAB + SELECT_TIME_RANGE_MESSAGE_INCOMES + System.lineSeparator() +
                TAB + SEPARATOR + System.lineSeparator() +
                TAB + PRINTING_TIME_RANGE_MESSAGE_INCOMES + lastMonday + " to " +
                sunday + System.lineSeparator() +
                TAB + "2. savings - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                TAB + "3. dividends - 10 - " + VALID_TEST_DATE + " - Tag: investment" + System.lineSeparator() +
                TAB + "Total: 20" + System.lineSeparator(),
                outContent.toString());
        incomes.remove(2);
    }

    @Test
    public void execute_listMonthlyIncomes_expectMonthlyIncomeList() {
        incomes.add(new Income(1000, "salary", VALID_TEST_DATE.minusDays(31), "", null, null, 0));
        Ui.userInputForTest("4");
        commandInputForTest("list incomes", incomes, spendings);
        LocalDate monthStart = LocalDate.of(VALID_TEST_DATE.getYear(), VALID_TEST_DATE.getMonth(), 1);
        LocalDate monthEnd = monthStart.plusDays(VALID_TEST_DATE.getMonth().length(VALID_TEST_DATE.isLeapYear()) - 1);
        assertEquals(TAB + SELECT_TIME_RANGE_MESSAGE_INCOMES + System.lineSeparator() +
                TAB + SEPARATOR + System.lineSeparator() +
                TAB + PRINTING_TIME_RANGE_MESSAGE_INCOMES + monthStart + " to " +
                monthEnd + System.lineSeparator() +
                TAB + "2. savings - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                TAB + "3. dividends - 10 - " + VALID_TEST_DATE + " - Tag: investment" + System.lineSeparator() +
                TAB + "Total: 20" + System.lineSeparator(),
                outContent.toString());
        incomes.remove(2);
    }

    @Test
    public void execute_listInvalidTimeRangeWeekly_expectAskAgainShowWeekly() {
        incomes.add(new Income(1000, "salary", VALID_TEST_DATE.minusDays(7), "", null, null, 0));
        Ui.userInputForTest(String.format("5%s2", System.lineSeparator()));
        commandInputForTest("list incomes", incomes, spendings);
        LocalDate monday = getMondayDate(VALID_TEST_DATE);
        LocalDate sunday = getSundayDate(VALID_TEST_DATE);
        assertEquals(TAB + SELECT_TIME_RANGE_MESSAGE_INCOMES + System.lineSeparator() +
                TAB + SEPARATOR + System.lineSeparator() +
                TAB + "Invalid input" + System.lineSeparator() +
                TAB + SELECT_TIME_RANGE_MESSAGE_INCOMES + System.lineSeparator() +
                TAB + SEPARATOR + System.lineSeparator() +
                TAB + PRINTING_TIME_RANGE_MESSAGE_INCOMES + monday + " to " +
                sunday + System.lineSeparator() +
                TAB + "2. savings - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                TAB + "3. dividends - 10 - " + VALID_TEST_DATE + " - Tag: investment" + System.lineSeparator() +
                TAB + "Total: 20" + System.lineSeparator(),
                outContent.toString());
        incomes.remove(2);
    }
}


