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
    private LocalDate currentDate = LocalDate.now();

    @BeforeEach
    public void setUp() {
        Command c = Parser.parse("add spending 10 girlfriends /2024-10-10");
        c.execute(incomes, spendings);

        c = Parser.parse("add spending 10 macdonalds");
        c.execute(incomes, spendings);

        c = Parser.parse("add income 10 savings /2024-10-10");
        c.execute(incomes, spendings);

        c = Parser.parse("add income 10 dividends");
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

        assertEquals("\tSpendings " + System.lineSeparator() +
                        "\tTotal spendings: 0" + System.lineSeparator() +
                        "\tIncomes " + System.lineSeparator() +
                        "\tTotal incomes: 0" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_allLists_success() {
        String userInout = "list";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tSpendings " + System.lineSeparator() +
                "\t1. girlfriends - 10 - 2024-10-10" + System.lineSeparator() +
                "\t2. macdonalds - 10 - " + currentDate + System.lineSeparator() +
                "\tTotal spendings: 20" + System.lineSeparator() +
                        "\tIncomes " + System.lineSeparator() +
                "\t1. savings - 10 - 2024-10-10" + System.lineSeparator() +
                "\t2. dividends - 10 - " + currentDate + System.lineSeparator() +
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
                        "\t2. macdonalds - 10 - " + currentDate + System.lineSeparator() +
                        "\tTotal spendings: 20" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_listSpending_success() {
        String userInout = "list incomes";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tIncomes" + System.lineSeparator() +
                        "\t1. savings - 10 - 2024-10-10" + System.lineSeparator() +
                        "\t2. dividends - 10 - " + currentDate + System.lineSeparator() +
                        "\tTotal incomes: 20" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_randomInput_exceptionThrown() {
        String userInout = "list 1234";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tInvalid input. Please enter in the form: list [spendings/incomes]" +
                System.lineSeparator(), outContent.toString());
    }

    @Test
    public void execute_tooManyInputs_exceptionThrown() {
        String userInout = "list spendings incomes";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tInvalid input. Please enter in the form: list [spending/income]" +
                System.lineSeparator(), outContent.toString());
    }

}
