package seedu.storage;

import seedu.classes.Ui;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

public class Storage {
    private static IncomeList incomes;
    private static SpendingList spendings;
    private static int password;

    public Storage(Ui ui) {
        incomes = IncomeListStorage.load();
        spendings = SpendingListStorage.load();
        password = LoginStorage.load(ui);
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
