package ical;

import java.util.Date;

public interface CalendarInterface {
    public Date getStart();

    public Date getEnd();

    public void findEnd();

    //public void addOccurrence(CalendarEvent occurrence);
}
