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
import static seedu.classes.Constants.DAILY_BUDGET_QUESTION;
import static seedu.classes.Constants.EMPTY_STRING;
import static seedu.classes.Constants.MONTHLY_BUDGET_MESSAGE;
import static seedu.classes.Constants.MONTHLY_OPTION;
import static seedu.classes.Constants.NO_ENTRIES_TIME_RANGE_MESSAGE;
import static seedu.classes.Constants.INCOMES_TIME_RANGE_MESSAGE;
import static seedu.classes.Constants.SPENDINGS_TIME_RANGE_MESSAGE;
import static seedu.classes.Constants.SEPARATOR;
import static seedu.classes.Constants.SPACE_REGEX;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Constants.SELECT_TIME_RANGE_MESSAGE_INCOMES;
import static seedu.classes.Constants.SELECT_TIME_RANGE_MESSAGE_SPENDINGS;
import static seedu.classes.Constants.WEEKLY_OPTION;
import static seedu.classes.Constants.YEARLY_BUDGET_MESSAGE;

public class Ui {
    private static final String INCOME = "Incomes";
    private static final String SPENDING = "Spendings";
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Sets the input stream to the given data
     * @param data  The data to be set as the input stream
     */
    public static void userInputForTest(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        scanner = new Scanner(System.in);
    }

    /**
     * Executes the command given by the user
     * @param userCommand   The command given by the user
     * @param incomes        List of incomes in the application
     * @param spendings      List of spendings in the application
     */
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

    public static String readUserPassword() {
        String password = scanner.nextLine();
        printSeparator();
        return password;
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

    /**
     * Prints a welcome message
     */
    public static void welcome() {
        printSeparator();
        printWithTab("Hello from");
        printFancyWiagi();
        printSeparator();
    }

    /**
     * Prints a welcome WIAGI letters in ASCII art
     */
    private static void printFancyWiagi() {
        printWithTab("__        __  ___      /\\       ____   ___");
        printWithTab("\\ \\      / / |_ _|    /  \\     / ___| |_ _|");
        printWithTab(" \\ \\ /\\ / /   | |    / /\\ \\   | |  _   | |");
        printWithTab("  \\ V  V /    | |   / ____ \\  | |_| |  | |");
        printWithTab("   \\_/\\_/    |___| /_/    \\_\\  \\____| |___|");
        printSeparator();
    }

    /**
     * Prints the spending statistics for daily, monthly, and yearly spendings, budgets and budget left
     * @param spendings List of spendings in the application
     */
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

    /**
     * Prints the elements of the given ArrayList, along with the list name and its total amount
     * @param list   The ArrayList containing elements to be printed
     * @param <T>    The type of elements in the ArrayList, which must extend the EntryType class.
     */
    public static <T extends EntryType> void printListWithTotal(ArrayList<T> list) {
        String typeOfList;
        double total;
        if (list instanceof SpendingList) {
            typeOfList = SPENDING;
            total = ((SpendingList) list).getTotal();
        } else {
            typeOfList = INCOME;
            total = ((IncomeList) list).getTotal();
        }
        printWithTab(typeOfList);
        printList(list);
        printWithTab("Total " + typeOfList.toLowerCase() + ": " + formatPrintDouble(total));
    }

    /**
     * Prints the elements of the given ArrayList
     *
     * @param <T>  The type of elements in the ArrayList, which must extend the EntryType class.
     * @param list The ArrayList containing elements to be printed and the total.
     */
    public static <T extends EntryType> void printList(ArrayList<T> list) {
        for (int indexInList = 0; indexInList < list.size(); indexInList++) {
            assert list != null : "ArrayList is null";
            int indexToUser = indexInList + 1;
            printWithTab(indexToUser + ". " + list.get(indexInList));
        }
    }

    /**
     * Prints the double in either 2dp, if double has decimal places, or without 2dp, if double can be represented as
     * an integer
     *
     * @param sum The double to be printed
     */
    public static String formatPrintDouble(double sum) {
        if (sum % 1 == 0) { //it is an integer
            return String.valueOf((int) sum);
        }
        // not integer

        sum = Math.round(sum * 100.0) / 100.0;
        return String.format("%.02f", sum);
    }

    //@@author wongwh2002
    /**
     * Prints all unique tags
     * @param incomes   List of incomes in the application
     * @param spendings List of spendings in the application
     */
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
    /**
     * Prints all entries in spendings and incomes that contain the specified tag
     * @param incomes   List of incomes in the application
     * @param spendings List of spendings in the application
     * @param tag       Tag to search for
     */
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
    private static <T extends EntryType> void printTimeRangeDates(ArrayList<T> list,
            LocalDate start, LocalDate end) {
        if (list instanceof SpendingList) {
            printWithTab(SPENDINGS_TIME_RANGE_MESSAGE + start + " to " + end);
        } else {
            printWithTab(INCOMES_TIME_RANGE_MESSAGE + start + " to " + end);
        }
    }

    /**
     * Prints all entries within the current month
     * @param list   List of spendings/incomes in the application
     * @param <T>    The type of elements in the list, which must extend the EntryType class
     */
    public static <T extends EntryType> void printWeekly(ArrayList<T> list) {
        StringBuilder filteredListString = new StringBuilder();
        LocalDate currDate = LocalDate.now();
        LocalDate monday = getMondayDate(currDate);
        LocalDate sunday = getSundayDate(currDate);
        printTimeRangeDates(list, monday, sunday);
        double sum = 0.0;
        for (int indexInList = 0; indexInList < list.size(); indexInList++) {
            EntryType entry = list.get(indexInList);
            int indexToUser = indexInList + 1;
            if (isInRange(entry.getDate(), monday, sunday)) {
                filteredListString.append(TAB).append(indexToUser).append(". ")
                        .append(entry).append(System.lineSeparator());
                sum += entry.getAmount();
            }
        }
        filteredListString = (filteredListString.isEmpty()) ? new StringBuilder(NO_ENTRIES_TIME_RANGE_MESSAGE)
                : filteredListString;
        printWithTab(filteredListString.toString().strip());
        printWithTab("Total: " + formatPrintDouble(sum));
    }

    /**
     * Prints all entries within the current month
     * @param list   List of spendings/incomes in the application
     * @param <T>    The type of elements in the list, which must extend the EntryType class
     */
    public static <T extends EntryType> void printMonthly(ArrayList<T> list) {
        LocalDate currDate = LocalDate.now();
        LocalDate monthStart = LocalDate.of(currDate.getYear(), currDate.getMonth(), 1);
        LocalDate monthEnd = monthStart.plusDays(currDate.getMonth().length(currDate.isLeapYear()) - 1);
        printTimeRangeDates(list, monthStart, monthEnd);
        StringBuilder filteredListString = new StringBuilder();
        double sum = 0.0;
        for (int indexInList = 0; indexInList < list.size(); indexInList++) {
            EntryType entry = list.get(indexInList);
            int indexToUser = indexInList + 1;
            if (isInRange(entry.getDate(), monthStart, monthEnd)) {
                filteredListString.append(TAB).append(indexToUser).append(". ")
                        .append(entry).append(System.lineSeparator());
                sum += entry.getAmount();
            }
        }
        filteredListString = (filteredListString.isEmpty()) ? new StringBuilder(NO_ENTRIES_TIME_RANGE_MESSAGE)
                : filteredListString;
        printWithTab(filteredListString.toString().strip());
        printWithTab("Total: " + formatPrintDouble(sum));
    }

    /**
     * Prints all entries within the current 2 weeks
     * @param list   list of spendings/incomes in the application
     * @param <T>    The type of elements in the list, which must extend the EntryType class
     */
    public static <T extends EntryType> void printBiweekly(ArrayList<T> list) {
        LocalDate currDate = LocalDate.now();
        LocalDate start = getMondayDate(currDate.minusDays(7));
        LocalDate end = getSundayDate(currDate);
        printTimeRangeDates(list, start, end);
        StringBuilder filteredListString = new StringBuilder();
        double sum = 0.0;
        for (int indexInList = 0; indexInList < list.size(); indexInList++) {
            EntryType entry = list.get(indexInList);
            int indexToUser = indexInList + 1;
            if (isInRange(entry.getDate(), start, end)) {
                filteredListString.append(TAB).append(indexToUser).append(". ")
                        .append(entry).append(System.lineSeparator());
                sum += entry.getAmount();
            }
        }
        filteredListString = (filteredListString.isEmpty()) ? new StringBuilder(NO_ENTRIES_TIME_RANGE_MESSAGE)
                : filteredListString;
        printWithTab(filteredListString.toString().strip());
        printWithTab("Total: " + formatPrintDouble(sum));
    }

    //@@author wx-03
    /**
     * Prints a message asking the user to select a time range
     * @param list   List of spendings/incomes in the application
     * @param <T>    The type of elements in the list, which must extend the EntryType class
     * @return       Boolean value that is true if the user chooses to list all spendings, false otherwise
     */
    public static <T extends EntryType> boolean printListOfTimeRange(ArrayList<T> list) {
        while (true) {
            if (list instanceof SpendingList) {
                printWithTab(SELECT_TIME_RANGE_MESSAGE_SPENDINGS);
            } else {
                printWithTab(SELECT_TIME_RANGE_MESSAGE_INCOMES);
            }
            String userInput = readCommand();
            switch (userInput) {
            case ALL_TIME_OPTION:
                return true;
            case WEEKLY_OPTION:
                printWeekly(list);
                return false;
            case BIWEEKLY_OPTION:
                printBiweekly(list);
                return false;
            case MONTHLY_OPTION:
                printMonthly(list);
                return false;
            default:
                printWithTab("Invalid input");
            }
        }
    }

    /**
     * Prints a message asking the user if they would like to print spending statistics and carries out the
     * required printing.
     * @param spendings list of spendings in the application
     */
    public static void printStatisticsIfRequired(SpendingList spendings) {
        printWithTab("List all statistics? [Y/N]:");
        while (true) {
            String userInput = readCommand().toLowerCase();
            switch (userInput) {
            case "y":
                printListWithTotal(spendings);
                printSpendingStatistics(spendings);
                return;
            case "n":
                printListWithTotal(spendings);
                return;
            default:
                printWithTab("Invalid input. [Y/N].");
            }
        }
    }

    /**
     * Prints a message asking the user if they would like to backlog recurring entries
     * @param toAdd Entry to be added
     * @param <T>   The type of entry to be added, which must extend the EntryType class
     * @return      Boolean value indicating if the user wants to backlog recurring entries
     */
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

    /**
     * Prints results from find command if they exist, else prints a message saying that no entries match
     * the criteria.
     * @param findResults   ArrayList of results of the find command
     * @param list          Original list of incomes / spendings
     * @param <T>           The type of entries in findResults, which must extend the EntryType class
     */
    public static <T extends EntryType> void printFindResults(ArrayList<T> findResults, ArrayList<T> list) {
        if (findResults.isEmpty()) {
            printWithTab("No entries found match the criteria.");
        } else {
            printWithTab("Here are the matching results:");
            findResults.forEach(entry -> printWithTab((list.indexOf(entry)+1) + ": " + entry.toString()));
        }
    }

    /**
     * Prints message for new user to set budgets
     */
    public static void newUserBudgetMessage() {
        printWithTab("Hello! So happy you took this first step of financial management.");
        printWithTab("Let's first set your budgets!");
    }

    public static void initialiseDailyBudgetMessage() {
        printWithTab(DAILY_BUDGET_QUESTION);
    }

    public static void initialiseMonthlyBudgetMessage() {
        printWithTab(MONTHLY_BUDGET_MESSAGE);
    }

    public static void initialiseYearlyBudgetMessage() {
        printWithTab(YEARLY_BUDGET_MESSAGE);
    }

    public static void errorLoadingBudgetMessage() {
        printWithTab("Hmmmm, seems to have some issues loading your budgets, please re-enter them :(");
    }

    public static void errorLoadingPasswordMessage() {
        printWithTab("Hmmmm, seems to have some issues loading your password, hard resetting... deleting files...");
    }

    /**
     * Prints message displaying line number and file name when a corrupted entry is detected
     * @param e             WiagiStorageCorruptedException
     * @param counter       Line number of corrupted entry
     * @param typeOfFile    Type of corrupted file, can be "spendings" or "incomes"
     */
    public static void handleCorruptedEntry(WiagiStorageCorruptedException e, long counter, String typeOfFile) {
        Ui.printWithTab(e.getMessage());
        Ui.printWithTab("Detected at line " + counter + " in the " + typeOfFile + " file.");
        Ui.printWithTab("Deleting corrupted entry...");
    }
}
