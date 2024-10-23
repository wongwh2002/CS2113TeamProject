package seedu.type;

import java.time.LocalDate;
import java.util.ArrayList;

public class DailyRecurrence extends Recurrence{
    public DailyRecurrence(Type entry) {
        super(entry);
    }

    @Override
    public void checkRecurrence(ArrayList<Type> list) {
        Type recurrence = getRecurrence();
        LocalDate lastRecurred = recurrence.getLastRecurrence();
        if (LocalDate.now().isAfter(lastRecurred)) {
            for (LocalDate date = lastRecurred.plusDays(1);
                 date.isBefore(LocalDate.now().plusDays(1)); date = date.plusDays(1)) {
                recurrence.editDateWithLocalDate(date);
                list.add(recurrence);
            }
        }
    }
}
