package seedu.type;

import java.time.LocalDate;
import java.util.ArrayList;

public class MonthlyRecurrence extends Recurrence {
    public MonthlyRecurrence(Type entry) {
        super(entry);
    }

    @Override
    public void checkRecurrence(ArrayList<Type> list) {
        Type recurrence = getRecurrence();
        LocalDate lastRecurred = recurrence.getLastRecurrence();
        if ((LocalDate.now().getMonthValue() > (lastRecurred.getMonthValue())
                && LocalDate.now().getDayOfMonth() > lastRecurred.getDayOfMonth())
                || LocalDate.now().getYear() > lastRecurred.getYear()) {
            for (LocalDate date = lastRecurred.plusMonths(1);
                 date.isBefore(LocalDate.now().plusMonths(1)); date = date.plusMonths(1)) {
                recurrence.editDateWithLocalDate(date);
                list.add(recurrence);
            }
        }
    }

}
