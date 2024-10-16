package seedu.commands;

import seedu.classes.Constants;
import seedu.classes.Parser;
import seedu.type.IncomeList;
import seedu.type.SpendingList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EditCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private final IncomeList incomes = new IncomeList();
    private final SpendingList spendings = new SpendingList();

    @BeforeEach
    public void setUp() {
        Command c = Parser.parse("add spending 10 girlfriends");
        c.execute(incomes, spendings);

        c = Parser.parse("add spending 10 macdonalds");
        c.execute(incomes, spendings);

        c = Parser.parse("add income 10 savings");
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
    public void execute_noArgs_expectIllegalArgumentException() {
        IncomeList emptyIncomes = new IncomeList();
        SpendingList emptySpendings = new SpendingList();
        String userInout = "edit";
        Command c = Parser.parse(userInout);
        c.execute(emptyIncomes, emptySpendings);
        assertEquals("\tInvalid input. Please enter in the form: edit [spending/income] [index] " +
                        "[amount/description] [new value]..." + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_invalidInput_expectIllegalArgumentException() {
        String userInout = "edit notspendingincome 1 amount 1";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals("\tInvalid input. Please enter in the form: edit [spending/income] [index] " +
                        "[amount/description] [new value]..." + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_invalidInputIndex_expectIndexOutOfBoundsException() {
        String userInout = "edit spending 5 amount 1";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals("\tPlease enter a valid index." + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_invalidThirdArgument_expectIllegalArgumentException() {
        String userInout = "edit spending 1 notamountdescription 1";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals("\tInvalid input. Please enter in the form: edit [spending/income] [index] " +
                        "[amount/description] [new value]..." + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_invalidLastArgumentExpectingInt_expectIllegalArgumentExceptionThrown() {
        String userInout = "edit spending 1 amount notanint";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals("\tInvalid input. Please enter in the form: edit [spending/income] [index] " +
                        "[amount/description] [new value]..." + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_editSpendingAmount_success() {
        String userInout = "edit spending 1 amount 1";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals(spendings.get(0).toString(), "girlfriends" + Constants.LIST_SEPARATOR + "1");
    }

    @Test
    public void execute_editIncomeAmount_success() {
        String userInout = "edit income 1 amount 1";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);
        assertEquals(incomes.get(0).toString(), "savings" + Constants.LIST_SEPARATOR + "1");
    }

}
