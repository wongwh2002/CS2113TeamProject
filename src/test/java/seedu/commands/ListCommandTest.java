package seedu.commands;

import seedu.classes.Parser;
import seedu.classes.Ui;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.SpendingList;
import seedu.type.Spending;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classes.Constants.INCORRECT_PARAMS_NUMBER;
import static seedu.classes.Constants.INVALID_CATEGORY;
import static seedu.classes.Constants.LIST_COMMAND_FORMAT;
import static seedu.classes.Constants.SEPARATOR;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Constants.TIME_RANGE_MESSAGE;
import static seedu.classes.Constants.VALID_TEST_DATE;

class ListCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private final IncomeList incomes = new IncomeList();
    private final SpendingList spendings = new SpendingList();

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @BeforeEach
    public void setUp() {
        spendings.add(new Spending(10, "girlfriends", VALID_TEST_DATE, "", null, null, 0));
        spendings.add(new Spending(10, "macdonalds", VALID_TEST_DATE, "food", null, null, 0));
        incomes.add(new Income(10, "savings", VALID_TEST_DATE, "", null, null, 0));
        incomes.add(new Income(10, "dividends", VALID_TEST_DATE, "investment", null, null, 0));
        provideInput("N");
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
        String userInout = "list";
        Command c = Parser.parseUserInput(userInout);
        c.execute(emptyIncomes, emptySpendings);

        assertEquals("\tSpendings" + System.lineSeparator() +
                        "\tTotal spendings: 0" + System.lineSeparator() +
                        "\tIncomes" + System.lineSeparator() +
                        "\tTotal incomes: 0" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_listEmptyTags_expectWiagiInvalidInputException() {
        IncomeList emptyIncomes = new IncomeList();
        SpendingList emptySpendings = new SpendingList();
        String userInout = "list tags";
        Command c = Parser.parseUserInput(userInout);
        c.execute(emptyIncomes, emptySpendings);

        assertEquals("\tNo tags found. Please input more tags!" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_listSpecificEmptyTags_expectWiagiInvalidInputException() {
        IncomeList emptyIncomes = new IncomeList();
        SpendingList emptySpendings = new SpendingList();
        String userInout = "list tags tag";
        Command c = Parser.parseUserInput(userInout);
        c.execute(emptyIncomes, emptySpendings);

        assertEquals("\tNo entries with tag: tag. Please input tags first!"
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_allLists_success() {
        String userInout = "list";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tSpendings" + System.lineSeparator() +
                        "\t1. girlfriends - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                        "\t2. macdonalds - 10 - " + VALID_TEST_DATE + " - Tag: food" + System.lineSeparator() +
                        "\tTotal spendings: 20" + System.lineSeparator() +
                        "\tIncomes" + System.lineSeparator() +
                        "\t1. savings - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                        "\t2. dividends - 10 - " + VALID_TEST_DATE + " - Tag: investment" + System.lineSeparator() +
                        "\tTotal incomes: 20" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_listIncome_success() {
        String userInout = "list incomes";
        Command c = Parser.parseUserInput(userInout);
        Ui.userInputForTest("1");
        c.execute(incomes, spendings);

        assertEquals(TAB + TIME_RANGE_MESSAGE + System.lineSeparator() +
                        "\t" + SEPARATOR + System.lineSeparator() +
                        "\tIncomes" + System.lineSeparator() +
                        "\t1. savings - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                        "\t2. dividends - 10 - " + VALID_TEST_DATE + " - Tag: investment" + System.lineSeparator() +
                        "\tTotal incomes: 20" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_randomInput_expectWiagiInvalidInputException() {
        String userInout = "list 1234";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);

        assertEquals(TAB + INVALID_CATEGORY + LIST_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_tooManyInputs_expectWiagiInvalidInputException() {
        String userInout = "list spendings incomes";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);

        assertEquals(TAB + INCORRECT_PARAMS_NUMBER + LIST_COMMAND_FORMAT
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_listTags_success() {
        String userInout = "list tags";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tTags" + System.lineSeparator() +
                        "\t1. food" + System.lineSeparator() +
                         "\t2. investment" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    void execute_listSpecificInvestmentTag_success() {
        String userInout = "list tags investment";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tTag: investment" + System.lineSeparator() +
                "\tIncomes" + System.lineSeparator() +
                "\t2. dividends - 10 - " + VALID_TEST_DATE + " - Tag: investment" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    void execute_listSpecificFoodTag_success() {
        String userInout = "list tags food";
        Command c = Parser.parseUserInput(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tTag: food" + System.lineSeparator() +
                        "\tSpendings" + System.lineSeparator() +
                        "\t2. macdonalds - 10 - " + VALID_TEST_DATE + " - Tag: food" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_listSpendingAllStatistics_correctMessage() {
        String userInout = "list spendings";
        Command c = Parser.parseUserInput(userInout);
        Ui.userInputForTest(String.format("1%sY", System.lineSeparator()));
        c.execute(incomes, spendings);

        assertEquals(TAB + TIME_RANGE_MESSAGE + System.lineSeparator() +
                        "\t" + SEPARATOR + System.lineSeparator() +
                        "\tList all statistics? [Y/N]:" + System.lineSeparator() +
                        "\t____________________________________________________________" + System.lineSeparator() +
                        "\tSpendings" + System.lineSeparator() +
                        "\t1. girlfriends - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                        "\t2. macdonalds - 10 - " + VALID_TEST_DATE + " - Tag: food" + System.lineSeparator() +
                        "\tTotal spendings: 20" + System.lineSeparator() +
                        "\t\tDaily spendings: 20" + System.lineSeparator() +
                        "\t\tDaily Budget: 0.0" + System.lineSeparator() +
                        "\t\tDaily budget left: -20" + System.lineSeparator() +
                        "\t\tMonthly spendings: 20" + System.lineSeparator() +
                        "\t\tMonthly Budget: 0.0" + System.lineSeparator() +
                        "\t\tMonthly budget left: -20" + System.lineSeparator() +
                        "\t\tYearly spendings: 20" + System.lineSeparator() +
                        "\t\tYearly Budget: 0.0" + System.lineSeparator() +
                        "\t\tYearly budget left: -20" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_listSpendingNotAllStatistics_correctMessage() {
        String userInout = "list spendings";
        Command c = Parser.parseUserInput(userInout);
        Ui.userInputForTest(String.format("1%sN", System.lineSeparator()));
        c.execute(incomes, spendings);
        assertEquals(TAB + TIME_RANGE_MESSAGE + System.lineSeparator() +
                        "\t" + SEPARATOR + System.lineSeparator() +
                        "\tList all statistics? [Y/N]:" + System.lineSeparator() +
                        "\t" + SEPARATOR + System.lineSeparator() +
                        "\tSpendings" + System.lineSeparator() +
                        "\t1. girlfriends - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                        "\t2. macdonalds - 10 - " + VALID_TEST_DATE + " - Tag: food" + System.lineSeparator() +
                        "\tTotal spendings: 20" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_listWeeklySpendings_expectWeeklySpendingList() {
        spendings.add(new Spending(10, "lunch", VALID_TEST_DATE.minusDays(7), "", null, null, 0));
        String userInout = "list spendings";
        Command c = Parser.parseUserInput(userInout);
        Ui.userInputForTest("2");
        c.execute(incomes, spendings);
        assertEquals(TAB + TIME_RANGE_MESSAGE + System.lineSeparator() +
                        "\t" + SEPARATOR + System.lineSeparator() +
                        "\t2. girlfriends - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                        "\t3. macdonalds - 10 - " + VALID_TEST_DATE + " - Tag: food" + System.lineSeparator(),
                outContent.toString());
        spendings.remove(2);
    }

    @Test
    public void execute_listBiweeklySpendings_expectBiweeklySpendingList() {
        spendings.add(new Spending(10, "lunch", VALID_TEST_DATE.minusDays(14), "", null, null, 0));
        String userInout = "list spendings";
        Command c = Parser.parseUserInput(userInout);
        Ui.userInputForTest("3");
        c.execute(incomes, spendings);
        assertEquals(TAB + TIME_RANGE_MESSAGE + System.lineSeparator() +
                        "\t" + SEPARATOR + System.lineSeparator() +
                        "\t2. girlfriends - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                        "\t3. macdonalds - 10 - " + VALID_TEST_DATE + " - Tag: food" + System.lineSeparator(),
                outContent.toString());
        spendings.remove(2);
    }

    @Test
    public void execute_listMonthlySpendings_expectMonthlySpendingList() {
        spendings.add(new Spending(10, "lunch", VALID_TEST_DATE.minusDays(31), "", null, null, 0));
        String userInout = "list spendings";
        Command c = Parser.parseUserInput(userInout);
        Ui.userInputForTest("4");
        c.execute(incomes, spendings);
        assertEquals(TAB + TIME_RANGE_MESSAGE + System.lineSeparator() +
                        "\t" + SEPARATOR + System.lineSeparator() +
                        "\t2. girlfriends - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                        "\t3. macdonalds - 10 - " + VALID_TEST_DATE + " - Tag: food" + System.lineSeparator(),
                outContent.toString());
        spendings.remove(2);
    }

    @Test
    public void execute_listWeeklyIncomes_expectWeeklyIncomeList() {
        incomes.add(new Income(1000, "salary", VALID_TEST_DATE.minusDays(7), "", null, null, 0));
        String userInout = "list incomes";
        Command c = Parser.parseUserInput(userInout);
        Ui.userInputForTest("2");
        c.execute(incomes, spendings);
        assertEquals(TAB + TIME_RANGE_MESSAGE + System.lineSeparator() +
                        "\t" + SEPARATOR + System.lineSeparator() +
                        "\t2. savings - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                        "\t3. dividends - 10 - " + VALID_TEST_DATE + " - Tag: investment" + System.lineSeparator(),
                outContent.toString());
        incomes.remove(2);
    }

    @Test
    public void execute_listBiweeklyIncomes_expectBiweeklyIncomeList() {
        incomes.add(new Income(1000, "salary", VALID_TEST_DATE.minusDays(14), "", null, null, 0));
        String userInout = "list incomes";
        Command c = Parser.parseUserInput(userInout);
        Ui.userInputForTest("3");
        c.execute(incomes, spendings);
        assertEquals(TAB + TIME_RANGE_MESSAGE + System.lineSeparator() +
                        "\t" + SEPARATOR + System.lineSeparator() +
                        "\t2. savings - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                        "\t3. dividends - 10 - " + VALID_TEST_DATE + " - Tag: investment" + System.lineSeparator(),
                outContent.toString());
        incomes.remove(2);
    }

    @Test
    public void execute_listMonthlyIncomes_expectMonthlyIncomeList() {
        incomes.add(new Income(1000, "salary", VALID_TEST_DATE.minusDays(31), "", null, null, 0));
        String userInout = "list incomes";
        Command c = Parser.parseUserInput(userInout);
        Ui.userInputForTest("4");
        c.execute(incomes, spendings);
        assertEquals(TAB + TIME_RANGE_MESSAGE + System.lineSeparator() +
                        "\t" + SEPARATOR + System.lineSeparator() +
                        "\t2. savings - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                        "\t3. dividends - 10 - " + VALID_TEST_DATE + " - Tag: investment" + System.lineSeparator(),
                outContent.toString());
        incomes.remove(2);
    }

    @Test
    public void execute_listInvalidTimeRangeWeekly_expectAskAgainShowWeekly() {
        incomes.add(new Income(1000, "salary", VALID_TEST_DATE.minusDays(7), "", null, null, 0));
        String userInout = "list incomes";
        Command c = Parser.parseUserInput(userInout);
        Ui.userInputForTest(String.format("5%s2", System.lineSeparator()));
        c.execute(incomes, spendings);
        assertEquals(TAB + TIME_RANGE_MESSAGE + System.lineSeparator() +
                        "\t" + SEPARATOR + System.lineSeparator() +
                        "\tInvalid input" + System.lineSeparator() +
                        TAB + TIME_RANGE_MESSAGE + System.lineSeparator() +
                        "\t" + SEPARATOR + System.lineSeparator() +
                        "\t2. savings - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                        "\t3. dividends - 10 - " + VALID_TEST_DATE + " - Tag: investment" + System.lineSeparator(),
                outContent.toString());
        incomes.remove(2);
    }
}


