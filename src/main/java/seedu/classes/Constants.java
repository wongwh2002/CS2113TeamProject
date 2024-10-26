package seedu.classes;

import java.time.LocalDate;

public class Constants {
    public static final String SEPARATOR = "____________________________________________________________";
    public static final String LIST_SEPARATOR = " - ";
    public static final String TAB = "\t";
    public static final String DAILY_RECURRENCE = "daily";
    public static final String MONTHLY_RECURRENCE = "monthly";
    public static final String YEARLY_RECURRENCE = "yearly";
    public static final LocalDate VALID_TEST_DATE = LocalDate.now();
    public static final String LIST_COMMAND_FORMAT = "Please enter in the form: list " +
            "[$CATEGORY]/[tags [$TAG_NAME]]";
    public static final String BUDGET_COMMAND_FORMAT = "Please enter in the form: budget {$PERIOD} {$AMOUNT}";
    public static final String EDIT_COMMAND_FORMAT = "Please enter in the form: edit {$CATEGORY} {$INDEX} {$FIELD} " +
            "{$NEW_VALUE}";
    public static final String DELETE_COMMAND_FORMAT = "Please enter in the form: delete {$CATEGORY} {$INDEX}";
    public static final String ADD_COMMAND_FORMAT = "Please enter in the form: add {$CATEGORY} {$AMOUNT} " +
            "{$DESCRIPTION} [/$DATE/] [*$TAG*] [~$Frequency~]";
    public static final String INCORRECT_PARAMS_NUMBER = "Incorrect number of parameters! ";
    public static final String INVALID_CATEGORY = "No such category exists! ";
    public static final String INVALID_FIELD = "No such field exists! ";
    public static final String INDEX_OUT_OF_BOUNDS = "Not a valid index!";
    public static final String INDEX_NOT_INTEGER = "Please enter a integer as the index! ";
    public static final String INVALID_AMOUNT = "Invalid amount! ";
    public static final String MISSING_DESCRIPTION = "No description input! ";
    public static final String INCORRECT_DATE_FORMAT = "Invalid date format! Use \"/YYYY-MM-DD/\" ";
    public static final String INVALID_FREQUENCY = "Invalid frequency type! Please input ~daily/monthly/yearly~ ";
    public static final String MISSING_AMOUNT = "No amount found! ";
    public static final String TIME_RANGE_MESSAGE = "Select time range:" + System.lineSeparator() +
            "\t[1] All" + System.lineSeparator() + "\t[2] Weekly" + System.lineSeparator() +
            "\t[3] Biweekly" + System.lineSeparator() + "\t[4] Monthly";
}
