package seedu.type;

import java.util.ArrayList;

public class IncomeList extends ArrayList<Income> {
    public IncomeList() {
        super();
    }
    public IncomeList(IncomeList incomes) {
        super(incomes);  // Initialise with data in storage
    }
}
