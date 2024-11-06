package seedu.type;

import seedu.classes.Parser;
import seedu.exception.WiagiInvalidInputException;
import seedu.recurrence.Recurrence;

import java.util.ArrayList;
import java.util.Comparator;

import static seedu.classes.Constants.MAX_LIST_AMOUNT_EXCEEDED;
import static seedu.classes.Constants.MAX_LIST_TOTAL_AMOUNT;

/**
 * Represents a list of {@link Income} entries.
 * Provides methods to manage and update income recurrence.
 */
public class IncomeList extends ArrayList<Income> {

    private double total;

    public IncomeList() {
        super();
        total = 0;
    }

    public double getTotal() {
        return total;
    }

    /**
     * Updates the recurrence for each income entry in the list.
     * If an income entry has a recurrence, it updates according to the specified recurrence rules.
     * The list is then sorted by the date of each entry.
     */
    public void updateRecurrence() {
        int size = this.size();
        for (int i = 0; i < size; i++) {
            Income income = this.get(i);
            Recurrence recurrence = Parser.parseRecurrence(income);
            if (recurrence != null) {
                recurrence.checkIncomeRecurrence(income, this, true);
            }
        }
    }

    @Override
    public boolean add(Income income) {
        total += income.getAmount();
        super.add(income);
        this.sort(Comparator.comparing(EntryType::getDate));
        return true;
    }
}
