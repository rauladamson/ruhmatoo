package ical;

import java.util.Date;
import java.util.List;

public interface CalendarInterface {
    Date getStart();

    Date getEnd();

    void findEnd();

    boolean isRecurring();

    List getOccurrences();

    //public void addOccurrence(CalendarEvent occurrence);
}
