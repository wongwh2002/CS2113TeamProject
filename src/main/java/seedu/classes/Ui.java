package seedu.classes;

import seedu.exception.WiagiInvalidInputException;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;
import seedu.type.EntryType;

import java.io.ByteArrayInputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static seedu.classes.Constants.ALL_TIME_OPTION;
import static seedu.classes.Constants.BIWEEKLY_OPTION;
import static seedu.classes.Constants.EMPTY_STRING;
import static seedu.classes.Constants.MONTHLY_OPTION;
import static seedu.classes.Constants.SEPARATOR;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Constants.TIME_RANGE_MESSAGE;
import static seedu.classes.Constants.WEEKLY_OPTION;

public class Ui {
    private static final String INCOME = "Incomes";
    private static final String SPENDING = "Spendings";
    private static Scanner scanner = new Scanner(System.in);

    public static void userInputForTest(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        scanner = new Scanner(System.in);
    }

    public static String readCommand() {
        String line = scanner.nextLine().trim();
        assert line != null : "Input line is null";
        Ui.printSeparator();
        return line;
    }

    public static void printSeparator() {
        printWithTab(SEPARATOR);
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

    public static <T extends EntryType> void printArrList(ArrayList<T> arrList) {
        String typeOfList;
        if (arrList instanceof SpendingList) {
            typeOfList = SPENDING;
        } else {
            typeOfList = INCOME;
        }
        Ui.printWithTab(typeOfList);
        Ui.printWithTab("Total " + typeOfList.toLowerCase() + ": " + printList(arrList));

    }

    /**
     * Prints the elements of the given ArrayList and calculates the sum of their amounts.
     *
     * @param <T>     The type of elements in the ArrayList, which must extend the Type class.
     * @param arrList The ArrayList containing elements to be printed and summed.
     * @return The sum of the amounts of the elements in the ArrayList as a String.
     */
    public static <T> String printList(ArrayList<T> arrList) {
        double sumOfAmountInList = 0;
        for (int indexInList = 0; indexInList < arrList.size(); indexInList++) {
            assert arrList != null : "ArrayList is null";
            int indexToUser = indexInList + 1;
            sumOfAmountInList += ((EntryType) arrList.get(indexInList)).getAmount();
            Ui.printWithTab(indexToUser + ". " + arrList.get(indexInList));
        }
        return formatPrintDouble(sumOfAmountInList);
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
        for (int indexInList = 0; indexInList < tags.size(); indexInList++) {
            int indexToUser = indexInList + 1;
            Ui.printWithTab(indexToUser + ". " + tags.get(indexInList));
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
    private static <T extends EntryType> int getTagsCount(ArrayList<T> arrList, String tag,
                                        StringBuilder sb, String listName) {
        sb.append(listName).append(System.lineSeparator());
        int tagsCount = 0;
        for (int i = 0; i < arrList.size(); i++) {
            EntryType listIndex = arrList.get(i);
            if (listIndex.getTag().equals(tag)) {
                tagsCount++;
                int oneIndexedI = i + 1;
                sb.append(TAB).append(oneIndexedI).append(". ")
                        .append(listIndex).append(System.lineSeparator());
            }
        }
        return tagsCount;
    }

    public static void printOverspendMessage(String budgetType, double overspendAmont) {
        overspendAmont *= -1;
        Ui.printWithTab("!!! You have overspent your " + budgetType + " by: " + overspendAmont + " !!!");
    }

    //@@author wx-03
    public static <T extends EntryType> void printWeekly(ArrayList<T> arrList) {
        StringBuilder filteredList = new StringBuilder();
        LocalDate currDate = LocalDate.now();
        LocalDate monday = getMondayDate(currDate);
        LocalDate sunday = getSundayDate(currDate);
        for (int indexInList = 0; indexInList < arrList.size(); indexInList++) {
            EntryType entry = arrList.get(indexInList);
            int indexToUser = indexInList + 1;
            if (isInRange(entry.getDate(), monday, sunday)) {
                filteredList.append(TAB).append(indexToUser).append(". ")
                        .append(entry).append(System.lineSeparator());
            }
        }
        System.out.print(filteredList);
    }

    public static <T extends EntryType> void printMonthly(ArrayList<T> arrList) {
        LocalDate currDate = LocalDate.now();
        LocalDate monthStart = LocalDate.of(currDate.getYear(), currDate.getMonth(), 1);
        LocalDate monthEnd = monthStart.plusDays(currDate.getMonth().length(currDate.isLeapYear()) - 1);
        StringBuilder filteredList = new StringBuilder();
        for (int indexInList = 0; indexInList < arrList.size(); indexInList++) {
            EntryType entry = arrList.get(indexInList);
            int indexToUser = indexInList + 1;
            if (isInRange(entry.getDate(), monthStart, monthEnd)) {
                filteredList.append(TAB).append(indexToUser).append(". ")
                        .append(entry).append(System.lineSeparator());
            }
        }
        System.out.print(filteredList);
    }

    public static <T extends EntryType> void printBiweekly(ArrayList<T> arrList) {
        LocalDate currDate = LocalDate.now();
        LocalDate start = getMondayDate(currDate.minusDays(7));
        LocalDate end = getSundayDate(currDate);
        StringBuilder filteredList = new StringBuilder();
        for (int indexInList = 0; indexInList < arrList.size(); indexInList++) {
            EntryType entry = arrList.get(indexInList);
            int indexToUser = indexInList + 1;
            if (isInRange(entry.getDate(), start, end)) {
                filteredList.append(TAB).append(indexToUser).append(". ")
                        .append(entry).append(System.lineSeparator());
            }
        }
        System.out.print(filteredList);
    }

    //@@author wx-03
    public static <T extends EntryType> boolean printListOfTimeRange(ArrayList<T> arrList) {
        while (true) {
            Ui.printWithTab(TIME_RANGE_MESSAGE);
            String userInput = Ui.readCommand();
            switch (userInput) {
            case ALL_TIME_OPTION:
                return true;
            case WEEKLY_OPTION:
                Ui.printWeekly(arrList);
                return false;
            case BIWEEKLY_OPTION:
                Ui.printBiweekly(arrList);
                return false;
            case MONTHLY_OPTION:
                Ui.printMonthly(arrList);
                return false;
            default:
                Ui.printWithTab("Invalid input");
            }
        }
    }

    public static void printStatisticsIfRequired(SpendingList spendings) {
        Ui.printWithTab("List all statistics? [Y/N]:");
        while (true) {
            String userInput = Ui.readCommand().toLowerCase();
            switch (userInput) {
            case "y":
                Ui.printArrList(spendings);
                Ui.printSpendingStatistics(spendings);
                return;
            case "n":
                Ui.printArrList(spendings);
                return;
            default:
                Ui.printWithTab("Invalid input. [Y/N].");
            }
        }
    }

    public static <T extends EntryType> boolean hasRecurrenceBacklog(T toAdd) {
        Ui.printWithTab("Do you want to backlog recurrence entries from " + toAdd.getDate() + " to "
                + LocalDate.now() + " if any? [Y/N]");
        while (true) {
            String userInput = Ui.readCommand().toLowerCase();
            switch (userInput) {
            case "y":
                return true;
            case "n":
                return false;
            default:
                Ui.printWithTab("Not a valid input. Please enter [Y/N]");
            }
        }
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

    private static boolean isInRange(LocalDate date, LocalDate start, LocalDate end) {
        return (date.isAfter(start) || date.isEqual(start))
                && (date.isBefore(end) || date.isEqual(end));
    }

    public static <T extends EntryType> void printFindResults(ArrayList<T> findResults, ArrayList<T> list) {
        if (findResults.isEmpty()) {
            Ui.printWithTab("No entries found match the criteria.");
        } else {
            Ui.printWithTab("Here are the matching results:");
            findResults.forEach(entry -> Ui.printWithTab((list.indexOf(entry)+1) + ": " + entry.toString()));
        }
    }
}

