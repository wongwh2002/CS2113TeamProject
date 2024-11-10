package seedu.classes;

import java.time.LocalDate;

public class Constants {

    // Strings
    public static final String SEPARATOR = "____________________________________________________________";
    public static final String EMPTY_STRING = "";
    public static final String LIST_SEPARATOR = " - ";
    public static final String NEXT_LINE = System.lineSeparator();
    public static final String STORAGE_SEPARATOR = "|";
    public static final String STORAGE_LOAD_SEPARATOR = "\\|";
    public static final String TAB = "\t";
    public static final String SPACE_REGEX = "\\s+";
    public static final String WHITESPACE= " ";
    public static final String TAG_IDENTIFIER = "\\*";
    public static final String RESTRICT_CHARACTER = ".*[*/~|].*";
    public static final String DATE_IDENTIFIER = "/";
    public static final String RECURRENCE_IDENTIFIER = "~";
    public static final String FIND_RANGE_DIVIDER = "to";
    public static final String SPENDING = "spending";
    public static final String INCOME = "income";
    public static final String DAILY_RECURRENCE = "daily";
    public static final String MONTHLY_RECURRENCE = "monthly";
    public static final String YEARLY_RECURRENCE = "yearly";
    public static final double MAX_ENTRY_AMOUNT = 10000000;
    public static final double MAX_LIST_TOTAL_AMOUNT = 100000000;
    public static final String INVALID_AMOUNT_MAX = "Invalid amount! Amount is greater than 10 million!";
    public static final String MAX_LIST_AMOUNT_EXCEEDED_FOR_ADD = "The total amount will exceed the list limit " +
            "of 100 million after adding! Entry rejected!";
    public static final String MAX_LIST_AMOUNT_EXCEEDED_FOR_EDIT = "The total amount will exceed the list limit " +
            "of 100 million after editing! Editing rejected!";
    public static final String MAX_LIST_AMOUNT_EXCEEDED_FOR_RECURRENCE = "The total amount will exceed the list limit" +
            " of 100 million after recurrence! Recurrence rejected!";
    public static final LocalDate VALID_TEST_DATE = LocalDate.now();
    public static final String BUDGET_INITIALISE_FORMAT = "What amount would you like to set: ";
    public static final String LIST_COMMAND_FORMAT = "Please enter in the form: list " +
            "[$CATEGORY]/[tags [$TAG_NAME]]";
    public static final String BUDGET_COMMAND_FORMAT = "Please enter in the form: budget {$PERIOD} {$AMOUNT}";
    public static final String EDIT_COMMAND_FORMAT = "Please enter in the form: edit {$CATEGORY} {$INDEX} {$FIELD} " +
            "{$NEW_VALUE}";
    public static final String FIND_COMMAND_FORMAT = "Please enter in the form: find {$CATEGORY} {$FIELD} " +
            "{$FIND_VALUE} [to $ANOTHER_FIND_VALUE]";
    public static final String DELETE_COMMAND_FORMAT = "Please enter in the form: delete {$CATEGORY} {$INDEX}";
    public static final String ADD_COMMAND_FORMAT = "Please enter in the form: add {$CATEGORY} {$AMOUNT} " +
            "{$DESCRIPTION} [/$DATE/] [*$TAG*] [~$FREQUENCY~]";
    public static final String DATE_NOT_ENCLOSED = "Date is not properly enclosed with \"/\"! ";
    public static final String TAG_NOT_ENCLOSED = "Tag is not properly enclosed with \"*\"! ";
    public static final String RECURRENCE_NOT_ENCLOSED = "Recurrence is not properly enclosed with \"~\"! ";
    public static final String INCORRECT_PARAMS_NUMBER = "Incorrect number of parameters! ";
    public static final String INVALID_CATEGORY = "No such category exists! ";
    public static final String INVALID_FIELD = "No such field exists! ";
    public static final String INDEX_OUT_OF_BOUNDS = "Not a valid index!";
    public static final String INDEX_NOT_INTEGER = "Please enter a integer as the index! ";
    public static final String AMOUNT_NOT_NUMBER = "Please enter a number for the amount! ";
    public static final String INVALID_AMOUNT = "Invalid amount! Amount must be greater than 0 (when rounded to 2dp)! ";
    public static final String INVALID_DESCRIPTION_CHARACTERS_IN_EDIT = "Invalid characters in description. " +
            "Edit unsuccessful.";
    public static final String INVALID_DESCRIPTION_CHARACTERS_IN_ADD = "Invalid characters in description. " +
            "Add unsuccessful.";
    public static final String INVALID_TAG_CHARACTERS = "Invalid characters in description. Edit unsuccessful.";
    public static final String MISSING_DESCRIPTION = "No description input! ";
    public static final String INVALID_DATE_FORMAT = "Invalid date format! Use YYYY-MM-DD ";
    public static final String INVALID_FREQUENCY = "Invalid frequency type! Please input daily/monthly/yearly ";
    public static final String MISSING_AMOUNT = "No amount found! ";
    public static final String MISSING_AMOUNT_AND_DESCRIPTION = "No amount and description found! ";
    public static final String MISSING_AMOUNT_DESCRIPTION_CATEGORY = "No category, amount, and description found! ";
    public static final String FIND_EXTRA_FROM_VALUE = "There should only be one from-value, " +
            "no whitespace separating it! ";
    public static final String FIND_EXTRA_TO_VALUE = "There should only be one to-value, " +
            "no whitespace separating it! ";
    public static final String FIND_EXTRA_SPECIFIC_VALUE = "There should only be one value, " +
            "no whitespace separating it! ";
    public static final String INVALID_FIND_RANGE_DIVIDER_FORMAT = "\"to\" should be separated from find values! ";
    public static final String INVALID_AMOUNT_RANGE = "The to-amount should be more than the from-amount! ";
    public static final String INVALID_DATE_RANGE = "The to-date should be after the from-date! ";
    public static final String TIME_RANGE_MESSAGE = "Select time range:" + NEXT_LINE +
            TAB + "[1] All" + NEXT_LINE + TAB + "[2] Weekly" + NEXT_LINE +
            TAB + "[3] Biweekly" + NEXT_LINE + TAB + "[4] Monthly";
    public static final String ALL_TIME_OPTION = "1";
    public static final String WEEKLY_OPTION = "2";
    public static final String BIWEEKLY_OPTION = "3";
    public static final String MONTHLY_OPTION = "4";
    public static final int LIST_TYPE_INDEX = 1;
    public static final int LIST_COMPULSORY_ARGUMENTS_LENGTH = 2;
    public static final int LIST_MAX_ARGUMENTS_LENGTH = 3;
    public static final String NO_TAGS_FOUND = "No tags found. Please input more tags!";
    public static final String INVALID_LIST_OPTION = "Invalid option!";

    // Storage load
    public static final String LOAD_INCOME_FILE_ERROR = "An error has occurred when loading income file!";
    public static final String SAVE_INCOME_FILE_ERROR = "An error has occurred when saving income file!";
    public static final String LOAD_SPENDING_FILE_ERROR = "An error has occurred when loading spending file!";
    public static final String SAVE_SPENDING_FILE_ERROR = "An error has occurred when saving spending file!";
    public static final String NO_RECURRENCE = "null";

    public static final int LOAD_AMOUNT_INDEX = 0;
    public static final int LOAD_DESCRIPTION_INDEX = 1;
    public static final int LOAD_DATE_INDEX = 2;
    public static final int LOAD_TAG_INDEX = 3;
    public static final int LOAD_RECURRENCE_INDEX = 4;
    public static final int LOAD_LAST_RECURRED_INDEX = 5;
    public static final int LOAD_DAY_OF_RECURRENCE_INDEX = 6;
    public static final int LOAD_DAILY_BUDGET_INDEX = 0;
    public static final int LOAD_MONTHLY_BUDGET_INDEX = 1;
    public static final int LOAD_YEARLY_BUDGET_INDEX = 2;
}
