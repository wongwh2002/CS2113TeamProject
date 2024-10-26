package seedu.classes;

import seedu.enums.TimeRange;
import seedu.exception.WiagiInvalidInputException;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;
import seedu.type.Type;

import java.io.ByteArrayInputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static seedu.classes.Constants.TIME_RANGE_MESSAGE;

public class Ui {
    public static final String EMPTY_STRING = "";
    public static final String TAB = "\t";
    public static final String INCOME = "Incomes";
    public static final String SPENDING = "Spendings";
    private static Scanner scanner = new Scanner(System.in);

    public static void userInputForTest(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        scanner = new Scanner(System.in);
    }

    public static String readCommand() {
        String line = scanner.nextLine();
        assert line != null : "Input line is null";
        Ui.printSeparator();
        return line;
    }

    public static void printSeparator() {
        printWithTab(Constants.SEPARATOR);
    }
    public static void printWithTab(String message) {
        System.out.println(TAB + message);
    }
    public static void printWithDoubleTab(String message) {
        System.out.println(TAB+TAB + message);
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
        Ui.printWithDoubleTab("Daily spendings: " + formatPrintDouble(spendings.getDailySpending()));
        Ui.printWithDoubleTab("Daily Budget: " + spendings.getDailyBudget());
        Ui.printWithDoubleTab("Daily budget left: " + formatPrintDouble(spendings.getDailyBudget() -
                spendings.getDailySpending()));
        Ui.printWithDoubleTab("Monthly spendings: " + formatPrintDouble(spendings.getMonthlySpending()));
        Ui.printWithDoubleTab("Monthly Budget: " + spendings.getMonthlyBudget());
        Ui.printWithDoubleTab("Monthly budget left: " +
                formatPrintDouble(spendings.getMonthlyBudget() - spendings.getMonthlySpending()));
        Ui.printWithDoubleTab("Yearly spendings: " + formatPrintDouble(spendings.getYearlySpending()));
        Ui.printWithDoubleTab("Yearly Budget: " + spendings.getYearlyBudget());
        Ui.printWithDoubleTab("Yearly budget left: " + formatPrintDouble(spendings.getYearlyBudget() -
                spendings.getYearlySpending()));
    }

    public static void printSpendings(SpendingList spendings) {
        Ui.printWithTab(SPENDING);
        Ui.printWithTab("Total spendings: " + print_list(spendings));

    }

    public static void printIncomes(IncomeList incomes) {
        Ui.printWithTab(INCOME);
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
        double sum = 0;
        for (int i = 0; i < arrList.size(); i++) {
            assert arrList != null : "ArrayList is null";
            int oneIndexedI = i + 1;
            sum += ((Type) arrList.get(i)).getAmount();
            Ui.printWithTab(oneIndexedI + ". " + arrList.get(i));
        }
        return formatPrintDouble(sum);
    }

    private static String formatPrintDouble(double sum) {
        if (sum % 1 == 0) {
            return String.valueOf((int) sum);
        }
        return String.valueOf(sum);
    }

    //@@author wongwh2002
    public static void printAllTags(IncomeList incomes, SpendingList spendings) {
        ArrayList<String> tags = getStrings(incomes, spendings);
        tags.sort(String::compareTo);
        if (tags.isEmpty()) {
            throw new WiagiInvalidInputException("No tags found. Please input more tags!");
        }
        assert tags != null : "Tags list is null";
        Ui.printWithTab("Tags");
        for (int i = 0; i < tags.size(); i++) {
            int oneIndexedI = i + 1;
            Ui.printWithTab(oneIndexedI + ". " + tags.get(i));
        }
    }

    //@@author wongwh2002
    private static ArrayList<String> getStrings(IncomeList incomes, SpendingList spendings) {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(EMPTY_STRING);
        assert tags != null : "Tags list is null";
        for (Income income : incomes) {
            String tag = income.getTag();
            if (!tags.contains(tag)) {
                tags.add(tag);
            }
        }
        for (Spending spending : spendings) {
            String tag = spending.getTag();
            if (!tags.contains(tag)) {
                tags.add(tag);
            }
        }
        tags.remove(EMPTY_STRING);
        return tags;
    }

    //@@author wongwh2002
    public static void printSpecificTag(IncomeList incomes, SpendingList spendings, String tag) {
        StringBuilder sbIncome = new StringBuilder();
        StringBuilder sbSpending = new StringBuilder();
        assert tag != null && !tag.isEmpty() : "Tag is null or empty";

        int tagsCount;
        int incomeCount;
        int spendingCount;

        incomeCount = getTagsCount(incomes, tag, sbIncome, INCOME);
        spendingCount = getTagsCount(spendings, tag, sbSpending, SPENDING);
        tagsCount = incomeCount + spendingCount;

        if (tagsCount == 0) {
            throw new WiagiInvalidInputException("No entries with tag: " + tag + ". Please input tags first!");
        }

        assert tagsCount > 0 : "No entries with tag: " + tag;
        assert incomeCount > 0 || spendingCount > 0 : "No entries with tag: " + tag;

        Ui.printWithTab("Tag: " + tag);
        if (incomeCount > 0) {
            Ui.printWithTab(sbIncome.toString().trim());
        }
        if (spendingCount > 0) {
            Ui.printWithTab(sbSpending.toString().trim());
        }
    }

    //@@author wongwh2002
    private static <T extends Type> int getTagsCount(ArrayList<T> arrList, String tag,
                                        StringBuilder sb, String listName) {
        sb.append(listName).append(System.lineSeparator());
        int tagsCount = 0;
        for (int i = 0; i < arrList.size(); i++) {
            Type listIndex = (Type) arrList.get(i);
            if (listIndex.getTag().equals(tag)) {
                tagsCount++;
                int oneIndexedI = i + 1;
                sb.append(TAB).append(oneIndexedI).append(". ")
                        .append(listIndex).append(System.lineSeparator());
            }
        }
        return tagsCount;
    }

    //@@author wx-03
    public static <T extends Type> void printWeekly(ArrayList<T> arrList) {
        ArrayList<T> filteredList = new ArrayList<>();
        LocalDate currDate = LocalDate.now();
        LocalDate monday = getMondayDate(currDate);
        LocalDate sunday = getSundayDate(currDate);
        for (T entry : arrList) {
            LocalDate entryDate = entry.getDate();
            if (inRange(entryDate, monday, sunday)) {
                filteredList.add(entry);
            }
        }
        print_list(filteredList);
    }

    public static <T extends Type> void printMonthly(ArrayList<T> arrList) {
        ArrayList<T> filteredList = new ArrayList<>();
        LocalDate currDate = LocalDate.now();
        LocalDate monthStart = LocalDate.of(currDate.getYear(), currDate.getMonth(), 1);
        LocalDate monthEnd = monthStart.plusDays(currDate.getMonth().length(currDate.isLeapYear()) - 1);
        for (T entry : arrList) {
            if (inRange(entry.getDate(), monthStart, monthEnd)) {
                filteredList.add(entry);
            }
        }
        print_list(filteredList);
    }

    public static <T extends Type> void printBiweekly(ArrayList<T> arrList) {
        ArrayList<T> filteredList = new ArrayList<>();
        LocalDate currDate = LocalDate.now();
        LocalDate start = getMondayDate(currDate.minusDays(7));
        LocalDate end = getSundayDate(currDate);
        for (T entry : arrList) {
            LocalDate entryDate = entry.getDate();
            if (inRange(entryDate, start, end)) {
                filteredList.add(entry);
            }
        }
        print_list(filteredList);
    }

    private static LocalDate getMondayDate(LocalDate currDate) {
        while (currDate.getDayOfWeek() != DayOfWeek.MONDAY) {
            currDate = currDate.minusDays(1);
        }
        return currDate;
    }

    private static LocalDate getSundayDate(LocalDate currDate) {
        while (currDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
            currDate = currDate.plusDays(1);
        }
        return currDate;
    }

    private static boolean inRange(LocalDate date, LocalDate start, LocalDate end) {
        return (date.isAfter(start) || date.isEqual(start))
                && (date.isBefore(end) || date.isEqual(end));
    }

    //@@author wx-03
    public static TimeRange askForTimeRange() {
        TimeRange selectedTimeRange = null;
        while (selectedTimeRange == null) {
            Ui.printWithTab(TIME_RANGE_MESSAGE);
            String userInput = Ui.readCommand();
            switch (userInput) {
            case "1":
                selectedTimeRange = TimeRange.ALL;
                break;
            case "2":
                selectedTimeRange = TimeRange.WEEKLY;
                break;
            case "3":
                selectedTimeRange = TimeRange.BIWEEKLY;
                break;
            case "4":
                selectedTimeRange = TimeRange.MONTHLY;
                break;
            default:
                Ui.printWithTab("Invalid input");
            }
        }
        return selectedTimeRange;
    }
}

