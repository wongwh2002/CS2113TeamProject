package seedu.type;

import seedu.classes.Parser;
import seedu.recurrence.Recurrence;

import java.util.ArrayList;
import java.util.Comparator;

public class IncomeList extends ArrayList<Income> {
    public IncomeList() {
        super();
    }

    public IncomeList(IncomeList incomes) {
        super(incomes);  // Initialise with data in storage
    }

    public void updateRecurrence() {
        int size = this.size();
        for (int i = 0; i < size; i++) {
            Income income = this.get(i);
            Recurrence recurrence = Parser.parseRecurrence(income);
            if (recurrence != null) {
                recurrence.checkIncomeRecurrence(income, this);
            }
        }
        this.sort(Comparator.comparing(EntryType::getDate));
    }
}
