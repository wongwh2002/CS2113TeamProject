package seedu.type;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Recurrence {
    protected final Type recurrence;

    public Recurrence(Type entry) {
        this.recurrence = entry;
    }

    public abstract void checkRecurrence(ArrayList<Type> list);

    public Type getRecurrence() {
        return recurrence;
    }
}
