package seedu.classes;

import seedu.commands.Command;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiStorageCorruptedException;
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
import static seedu.classes.Constants.SPACE_REGEX;
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

    public static void commandInputForTest(String userCommand, IncomeList incomes, SpendingList spendings) {
        Command c = Parser.parseUserInput(userCommand);
        c.execute(incomes, spendings);
    }

    public static String readCommand() {
        String line = scanner.nextLine().trim();
        assert line != null : "Input line is null";
        printSeparator();
        // replace whitespaces to a single whitespace and remove trailing spaces
        return line.replaceAll(SPACE_REGEX, " ").trim();
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
        printSeparator();
        printWithTab("Hello from");
        printFancyWiagi();
        printSeparator();
    }
    private static void printFancyWiagi() {
        printWithTab("__        __  ___      /\\       ____   ___");
        printWithTab("\\ \\      / / |_ _|    /  \\     / ___| |_ _|");
        printWithTab(" \\ \\ /\\ / /   | |    / /\\ \\   | |  _   | |");
        printWithTab("  \\ V  V /    | |   / ____ \\  | |_| |  | |");
        printWithTab("   \\_/\\_/    |___| /_/    \\_\\  \\____| |___|");
        printSeparator();
    }

    public static void printSpendingStatistics(SpendingList spendings) {
        printWithDoubleTab("Daily spendings: " + formatPrintDouble(spendings.getDailySpending()));
        printWithDoubleTab("Daily Budget: " + formatPrintDouble(spendings.getDailyBudget()));
        printWithDoubleTab("Daily budget left: " + formatPrintDouble(spendings.getDailyBudget() -
                spendings.getDailySpending()));
        printWithDoubleTab("Monthly spendings: " + formatPrintDouble(spendings.getMonthlySpending()));
        printWithDoubleTab("Monthly Budget: " + formatPrintDouble(spendings.getMonthlyBudget()));
        printWithDoubleTab("Monthly budget left: " +
                formatPrintDouble(spendings.getMonthlyBudget() - spendings.getMonthlySpending()));
        printWithDoubleTab("Yearly spendings: " + formatPrintDouble(spendings.getYearlySpending()));
        printWithDoubleTab("Yearly Budget: " + formatPrintDouble(spendings.getYearlyBudget()));
        printWithDoubleTab("Yearly budget left: " + formatPrintDouble(spendings.getYearlyBudget() -
                spendings.getYearlySpending()));
    }

    public static <T extends EntryType> void printArrList(ArrayList<T> arrList) {
        String typeOfList;
        double total;
        if (arrList instanceof SpendingList) {
            typeOfList = SPENDING;
            total = ((SpendingList) arrList).getTotal();
        } else {
            typeOfList = INCOME;
            total = ((IncomeList) arrList).getTotal();
        }
        printWithTab(typeOfList);
        printList(arrList);
        printWithTab("Total " + typeOfList.toLowerCase() + ": " + formatPrintDouble(total));

    }

    /**
     * Prints the elements of the given ArrayList
     *
     * @param <T>     The type of elements in the ArrayList, which must extend the EntryType class.
     * @param arrList The ArrayList containing elements to be printed and the total.
     */
    public static <T extends EntryType> void printList(ArrayList<T> arrList) {
        for (int indexInList = 0; indexInList < arrList.size(); indexInList++) {
            assert arrList != null : "ArrayList is null";
            int indexToUser = indexInList + 1;
            printWithTab(indexToUser + ". " + arrList.get(indexInList));
        }
    }

    public static String formatPrintDouble(double sum) {
        if (sum % 1 == 0) { //it is an integer
            return String.valueOf((int) sum);
        }
        // not integer

        sum = Math.round(sum * 100.0) / 100.0;
        return String.format("%.02f", sum);
    }

    //@@author wongwh2002
    public static void printAllTags(IncomeList incomes, SpendingList spendings) {
        ArrayList<String> tags = getStrings(incomes, spendings);
        tags.sort(String::compareTo);
        if (tags.isEmpty()) {
            throw new WiagiInvalidInputException("No tags found. Please input more tags!");
        }
        assert tags != null : "Tags list is null";
        printWithTab("Tags");
        for (int indexInList = 0; indexInList < tags.size(); indexInList++) {
            int indexToUser = indexInList + 1;
            printWithTab(indexToUser + ". " + tags.get(indexInList));
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

        printWithTab("Tag: " + tag);
        if (incomeCount > 0) {
            printWithTab(sbIncome.toString().trim());
        }
        if (spendingCount > 0) {
            printWithTab(sbSpending.toString().trim());
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

    public static void printOverspendMessage(String budgetType, double overspendAmount) {
        overspendAmount *= -1;
        printWithTab("!!! You have overspent your " + budgetType + " by: " +
                formatPrintDouble(overspendAmount) + " !!!");
    }

    //@@author wx-03
    public static <T extends EntryType> void printWeekly(ArrayList<T> arrList) {
        StringBuilder filteredListString = new StringBuilder();
        LocalDate currDate = LocalDate.now();
        LocalDate monday = getMondayDate(currDate);
        LocalDate sunday = getSundayDate(currDate);
        double sum = 0.0;
        for (int indexInList = 0; indexInList < arrList.size(); indexInList++) {
            EntryType entry = arrList.get(indexInList);
            int indexToUser = indexInList + 1;
            if (isInRange(entry.getDate(), monday, sunday)) {
                filteredListString.append(TAB).append(indexToUser).append(". ")
                        .append(entry).append(System.lineSeparator());
                sum += entry.getAmount();
            }
        }
        printWithTab(filteredListString.toString().strip());
        printWithTab("Total: " + formatPrintDouble(sum));
    }

    public static <T extends EntryType> void printMonthly(ArrayList<T> arrList) {
        LocalDate currDate = LocalDate.now();
        LocalDate monthStart = LocalDate.of(currDate.getYear(), currDate.getMonth(), 1);
        LocalDate monthEnd = monthStart.plusDays(currDate.getMonth().length(currDate.isLeapYear()) - 1);
        StringBuilder filteredListString = new StringBuilder();
        double sum = 0.0;
        for (int indexInList = 0; indexInList < arrList.size(); indexInList++) {
            EntryType entry = arrList.get(indexInList);
            int indexToUser = indexInList + 1;
            if (isInRange(entry.getDate(), monthStart, monthEnd)) {
                filteredListString.append(TAB).append(indexToUser).append(". ")
                        .append(entry).append(System.lineSeparator());
                sum += entry.getAmount();
            }
        }
        printWithTab(filteredListString.toString().strip());
        printWithTab("Total: " + formatPrintDouble(sum));
    }

    public static <T extends EntryType> void printBiweekly(ArrayList<T> arrList) {
        LocalDate currDate = LocalDate.now();
        LocalDate start = getMondayDate(currDate.minusDays(7));
        LocalDate end = getSundayDate(currDate);
        StringBuilder filteredListString = new StringBuilder();
        double sum = 0.0;
        for (int indexInList = 0; indexInList < arrList.size(); indexInList++) {
            EntryType entry = arrList.get(indexInList);
            int indexToUser = indexInList + 1;
            if (isInRange(entry.getDate(), start, end)) {
                filteredListString.append(TAB).append(indexToUser).append(". ")
                        .append(entry).append(System.lineSeparator());
                sum += entry.getAmount();
            }
        }
        printWithTab(filteredListString.toString().strip());
        printWithTab("Total: " + formatPrintDouble(sum));
    }

    //@@author wx-03
    public static <T extends EntryType> boolean printListOfTimeRange(ArrayList<T> arrList) {
        while (true) {
            printWithTab(TIME_RANGE_MESSAGE);
            String userInput = readCommand();
            switch (userInput) {
            case ALL_TIME_OPTION:
                return true;
            case WEEKLY_OPTION:
                printWeekly(arrList);
                return false;
            case BIWEEKLY_OPTION:
                printBiweekly(arrList);
                return false;
            case MONTHLY_OPTION:
                printMonthly(arrList);
                return false;
            default:
                printWithTab("Invalid input");
            }
        }
    }

    public static void printStatisticsIfRequired(SpendingList spendings) {
        printWithTab("List all statistics? [Y/N]:");
        while (true) {
            String userInput = readCommand().toLowerCase();
            switch (userInput) {
            case "y":
                printArrList(spendings);
                printSpendingStatistics(spendings);
                return;
            case "n":
                printArrList(spendings);
                return;
            default:
                printWithTab("Invalid input. [Y/N].");
            }
        }
    }

    public static <T extends EntryType> boolean hasRecurrenceBacklog(T toAdd) {
        printWithTab("Do you want to backlog recurrence entries from " + toAdd.getDate() + " to "
                + LocalDate.now() + " if any? [Y/N]");
        while (true) {
            String userInput = readCommand().toLowerCase();
            switch (userInput) {
            case "y":
                return true;
            case "n":
                return false;
            default:
                printWithTab("Not a valid input. Please enter [Y/N]");
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
            printWithTab("No entries found match the criteria.");
        } else {
            printWithTab("Here are the matching results:");
            findResults.forEach(entry -> printWithTab((list.indexOf(entry)+1) + ": " + entry.toString()));
        }
    }

    public static void newUserBudgetMessage() {
        printWithTab("Hello! So happy you took this first step of financial management.");
        printWithTab("Let's first set your budgets!");
    }

    public static void initialiseDailyBudgetMessage() {
        printWithTab("Please enter a daily budget you have in mind:");
    }

    public static void initialiseMonthlyBudgetMessage() {
        printWithTab("Next, please enter a monthly budget you have in mind:");
    }

    public static void initialiseYearlyBudgetMessage() {
        printWithTab("Last one! Please enter a yearly budget you have in mind:");
    }

    public static void errorLoadingBudgetMessage() {
        printWithTab("Hmmmm, seems to have some issues loading your budgets, please re-enter them :(");
    }

    public static void errorLoadingPasswordMessage() {
        printWithTab("Hmmmm, seems to have some issues loading your password, hard resetting... deleting files...");
    }

    public static void handleCorruptedEntry(WiagiStorageCorruptedException e, long counter, String typeOfFile) {
        Ui.printWithTab(e.getMessage());
        Ui.printWithTab("Detected at line " + counter + " in the " + typeOfFile + " file.");
        Ui.printWithTab("Deleting corrupted entry...");
    }
}
