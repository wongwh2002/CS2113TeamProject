package seedu.storage;

import seedu.classes.Ui;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

public class Storage {
    static IncomeList incomes;
    static SpendingList spendings;
    static int password;

    public Storage(Ui ui) {
        IncomeListStorage.load();
        SpendingListStorage.load();
        LoginStorage.load(ui);
    }
    public void save(IncomeList incomes, SpendingList spendings) {
        IncomeListStorage.save(incomes);
        SpendingListStorage.save(spendings);
    }
    public static IncomeList getIncomes() {
        return incomes;
    }
    public static SpendingList getSpendings() {
        return spendings;
    }
    public static int getPassword() {
        return password;
    }
}
