package seedu.classes;

import java.time.LocalDate;

public class Constants {
    public static final String SEPARATOR = "____________________________________________________________";
    public static final String LIST_SEPARATOR = " - ";
    public static final String DAILY_RECURRENCE = "daily";
    public static final String MONTHLY_RECURRENCE = "monthly";
    public static final String YEARLY_RECURRENCE = "yearly";
    public static final LocalDate VALID_TEST_DATE = LocalDate.now();
    public static final String ADD_COMMAND_CORRECT_FORMAT = "Invalid input. " +
            "Please enter in the form: add [spending/income] [amount] " +
            "[description] {/YYYY-MM-DD/} {*tag*} {~recurrence~}";
    public static final String ADD_COMMAND_INE_SEPARATOR_CORRECT_FORMAT =
            System.lineSeparator() + "\t" + ADD_COMMAND_CORRECT_FORMAT;

}
