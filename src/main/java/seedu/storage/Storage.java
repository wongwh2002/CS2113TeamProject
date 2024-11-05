package seedu.storage;

import seedu.type.IncomeList;
import seedu.type.SpendingList;

/**
 * Handles storage operations for the application, including loading and saving data.
 * Manages the incomes and spendings lists, as well as the password.
 */
public class Storage {
    static IncomeList incomes = new IncomeList();
    static SpendingList spendings = new SpendingList();
    static int password;

    /**
     * Constructs a Storage object and loads the data for incomes, spendings, and login credentials.
     */
    public Storage() {
        IncomeListStorage.load();
        SpendingListStorage.load();
        LoginStorage.load();
    }

    /**
     * Saves the provided IncomeList and SpendingList data to persistent storage.
     *
     * @param incomes the list of incomes to save.
     * @param spendings the list of spendings to save.
     */
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
