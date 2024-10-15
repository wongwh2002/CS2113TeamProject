package seedu.commands;

import seedu.type.IncomeList;
import seedu.type.SpendingList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    /*
    @Test
    public void execute_emptyList_success() {
        IncomeList emptyIncomes = new IncomeList();
        SpendingList emptySpendings = new SpendingList();
        ListCommand listCommand = new ListCommand("list");
        listCommand.execute(emptyIncomes, emptySpendings);

        assertEquals("Spendings \n\tTotal spendings: 0\n\tIncomes \n\tTotal incomes: 0",
                outContent.toString().trim());
    } //idk how to do this properly, its not working well.

        @Test
        public void execute_allLists_success() {
        }

        @Test
        public void execute_listIncome_success() {
        }

        @Test
        public void execute_listSpending_success() {
        }

        @Test
        public void execute_randomInput_exceptionThrown() {
        }
    */
}
