package seedu.commands;

import seedu.classes.Parser;
import seedu.type.IncomeList;
import seedu.type.SpendingList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private final IncomeList incomes = new IncomeList();
    private final SpendingList spendings = new SpendingList();
    private final LocalDate currentDate = LocalDate.now();

    @BeforeEach
    public void setUp() {
        Command c = Parser.parse("add spending 10 girlfriends /2024-10-10");
        c.execute(incomes, spendings);

        c = Parser.parse("add spending 10 macdonalds *food*");
        c.execute(incomes, spendings);

        c = Parser.parse("add income 10 savings /2024-10-10");
        c.execute(incomes, spendings);

        c = Parser.parse("add income 10 dividends *investment*");
        c.execute(incomes, spendings);

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
        Command c = Parser.parse(userInout);
        c.execute(emptyIncomes, emptySpendings);

        assertEquals("\tSpendings" + System.lineSeparator() +
                        "\tTotal spendings: 0" + System.lineSeparator() +
                        "\tDaily spendings: 0 Daily Budget: 0" + System.lineSeparator() +
                        "\tMonthly spendings: 0 Monthly Budget: 0" + System.lineSeparator() +
                        "\tYearly spendings: 0 Yearly Budget: 0" + System.lineSeparator() +
                        "\tIncomes" + System.lineSeparator() +
                        "\tTotal incomes: 0" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_listEmptyTags_expectWiagiInvalidInputException() {
        IncomeList emptyIncomes = new IncomeList();
        SpendingList emptySpendings = new SpendingList();
        String userInout = "list tags";
        Command c = Parser.parse(userInout);
        c.execute(emptyIncomes, emptySpendings);

        assertEquals("\tNo tags found. Please input more tags!" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_listSpecificEmptyTags_expectWiagiInvalidInputException() {
        IncomeList emptyIncomes = new IncomeList();
        SpendingList emptySpendings = new SpendingList();
        String userInout = "list tags tag";
        Command c = Parser.parse(userInout);
        c.execute(emptyIncomes, emptySpendings);

        assertEquals("\tNo entries with tag: tag. Please input tags first!"
                + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_allLists_success() {
        String userInout = "list";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tSpendings" + System.lineSeparator() +
                        "\t1. girlfriends - 10 - 2024-10-10" + System.lineSeparator() +
                        "\t2. macdonalds - 10 - " + currentDate + " - food" + System.lineSeparator() +
                        "\tTotal spendings: 20" + System.lineSeparator() +
                        "\tDaily spendings: 10 Daily Budget: 0" + System.lineSeparator() +
                        "\tMonthly spendings: 20 Monthly Budget: 0" + System.lineSeparator() +
                        "\tYearly spendings: 20 Yearly Budget: 0" + System.lineSeparator() +
                        "\tIncomes" + System.lineSeparator() +
                        "\t1. savings - 10 - 2024-10-10" + System.lineSeparator() +
                        "\t2. dividends - 10 - " + currentDate + " - investment" + System.lineSeparator() +
                        "\tTotal incomes: 20" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_listIncome_success() {
        String userInout = "list spendings";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tSpendings" + System.lineSeparator() +
                        "\t1. girlfriends - 10 - 2024-10-10" + System.lineSeparator() +
                        "\t2. macdonalds - 10 - " + currentDate + " - food" + System.lineSeparator() +
                        "\tTotal spendings: 20" + System.lineSeparator() +
                        "\tDaily spendings: 10 Daily Budget: 0" + System.lineSeparator() +
                        "\tMonthly spendings: 20 Monthly Budget: 0" + System.lineSeparator() +
                        "\tYearly spendings: 20 Yearly Budget: 0" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_listSpending_success() {
        String userInout = "list incomes";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tIncomes" + System.lineSeparator() +
                        "\t1. savings - 10 - 2024-10-10" + System.lineSeparator() +
                        "\t2. dividends - 10 - " + currentDate + " - investment" + System.lineSeparator() +
                        "\tTotal incomes: 20" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_randomInput_expectWiagiInvalidInputException() {
        String userInout = "list 1234";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tInvalid input. " +
                "Please enter in the form: list [spendings/incomes/{tags TAG_NAME}]" +
                System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_tooManyInputs_expectWiagiInvalidInputException() {
        String userInout = "list spendings incomes";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tToo many arguments. Please enter in the form: list [spendings/incomes/tags]" +
                System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_listTags_success() {
        String userInout = "list tags";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tTags" + System.lineSeparator() +
                        "\t1. food" + System.lineSeparator() +
                         "\t2. investment" + System.lineSeparator(),
                outContent.toString());
    }

    @Test void execute_listSpecificInvestmentTag_success() {
        String userInout = "list tags investment";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tTag: investment" + System.lineSeparator() +
                "\tIncomes" + System.lineSeparator() +
                "\t2. dividends - 10 - " + currentDate + " - investment" + System.lineSeparator(),
                outContent.toString());
    }

    @Test void execute_listSpecificFoodTag_success() {
        String userInout = "list tags food";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tTag: food" + System.lineSeparator() +
                        "\tSpendings" + System.lineSeparator() +
                        "\t2. macdonalds - 10 - " + currentDate + " - food" + System.lineSeparator(),
                outContent.toString());
    }
}

