package seedu.type;

import java.util.ArrayList;

public class SpendingList extends ArrayList<Spending> {
    public SpendingList() {
        super();
    }
    public SpendingList(SpendingList spendings) {
        super(spendings);  // Initialise with data in storage
    }
}


