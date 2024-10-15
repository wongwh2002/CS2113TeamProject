package seedu.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

class ListCommandTest {
    @Test
    public void execute_emptyList_success() {
        IncomeList emptyIncomes = new IncomeList();
        SpendingList emptySpendings = new SpendingList();
        ListCommand listCommand = new ListCommand("list");
        listCommand.execute(emptyIncomes, emptySpendings);

        assertEquals("", listCommand.print_list(emptyIncomes));

    }

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


}