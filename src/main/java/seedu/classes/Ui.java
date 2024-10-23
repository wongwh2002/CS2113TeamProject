package seedu.classes;

import seedu.type.IncomeList;
import seedu.type.SpendingList;
import seedu.type.Type;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static Scanner scanner = new Scanner(System.in);

    public static void userInputForTest(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        scanner = new Scanner(System.in);
    }

    public static String readCommand() {
        String line = scanner.nextLine();
        Ui.printSeparator();
        return line;
    }

    public static void printSeparator() {
        printWithTab(Constants.SEPARATOR);
    }
    public static void printWithTab(String message) {
        System.out.println("\t" + message);
    }
    public static void printWithDoubleTab(String message) {
        System.out.println("\t\t" + message);
    }
    public static void welcome() {
        Ui.printSeparator();
        Ui.printWithTab("Hello from");
        printFancyWiagi();
        Ui.printSeparator();
    }
    private static void printFancyWiagi() {
        Ui.printWithTab("__        __  ___      /\\       ____   ___");
        Ui.printWithTab("\\ \\      / / |_ _|    /  \\     / ___| |_ _|");
        Ui.printWithTab(" \\ \\ /\\ / /   | |    / /\\ \\   | |  _   | |");
        Ui.printWithTab("  \\ V  V /    | |   / ____ \\  | |_| |  | |");
        Ui.printWithTab("   \\_/\\_/    |___| /_/    \\_\\  \\____| |___|");
        Ui.printSeparator();
    }

    public static void printSpendingStatistics(SpendingList spendings) {
        Ui.printWithDoubleTab("Daily spendings: " + spendings.getDailySpending());
        Ui.printWithDoubleTab("Daily Budget: " + spendings.getDailyBudget());
        Ui.printWithDoubleTab("Daily budget left: " + (spendings.getDailyBudget() - spendings.getDailySpending()));
        Ui.printWithDoubleTab("Monthly spendings: " + spendings.getMonthlySpending());
        Ui.printWithDoubleTab("Monthly Budget: " + spendings.getMonthlyBudget());
        Ui.printWithDoubleTab("Monthly budget left: " +
                (spendings.getMonthlyBudget() - spendings.getMonthlySpending()));
        Ui.printWithDoubleTab("Yearly spendings: " + spendings.getYearlySpending());
        Ui.printWithDoubleTab("Yearly Budget: " + spendings.getYearlyBudget());
        Ui.printWithDoubleTab("Yearly budget left: " + (spendings.getYearlyBudget() - spendings.getYearlySpending()));
    }

    public static void printSpendings(SpendingList spendings) {
        Ui.printWithTab("Spendings");
        Ui.printWithTab("Total spendings: " + print_list(spendings));

    }

    public static void printIncomes(IncomeList incomes) {
        Ui.printWithTab("Incomes");
        Ui.printWithTab("Total incomes: " + print_list(incomes));
    }

    /**
     * Prints the elements of the given ArrayList and calculates the sum of their amounts.
     *
     * @param <T>     The type of elements in the ArrayList, which must extend the Type class.
     * @param arrList The ArrayList containing elements to be printed and summed.
     * @return The sum of the amounts of the elements in the ArrayList as a String.
     */
    public static <T> String print_list(ArrayList<T> arrList) {
        int sum = 0;
        for (int i = 0; i < arrList.size(); i++) {
            int oneIndexedI = i + 1;
            sum += ((Type) arrList.get(i)).getAmount();
            Ui.printWithTab(oneIndexedI + ". " + arrList.get(i));
        }
        return String.valueOf(sum);
    }
}
