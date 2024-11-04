package seedu.type;

import seedu.classes.Parser;
import seedu.recurrence.Recurrence;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Represents a list of {@link Income} entries.
 * Provides methods to manage and update income recurrence.
 */
public class IncomeList extends ArrayList<Income> {
    public IncomeList() {
        super();
    }

    public IncomeList(IncomeList incomes) {
        super(incomes);  // Initialise with data in storage
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
        this.sort(Comparator.comparing(EntryType::getDate));
    }
}
