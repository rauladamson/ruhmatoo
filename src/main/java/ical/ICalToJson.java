package ical;

import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.io.text.ICalReader;
import biweekly.property.DateEnd;
import biweekly.property.DurationProperty;
import biweekly.property.RecurrenceRule;
import biweekly.util.com.google.ical.compat.javautil.DateIterator;
import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

public class ICalToJson {

    public static JSONArray convert(String icalUrl) {
        try {
            Path tempFile = Files.createTempFile("ical", ".ics");

            URL iCalLink = new URL(icalUrl);

            try (InputStream in = iCalLink.openStream()) {
                Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }

            ICalReader reader = new ICalReader(tempFile.toFile());
            List<ICalendar> icals = reader.readAll(); // Read all iCalendars

            JSONArray jsonArray = new JSONArray();

            for (ICalendar ical : icals) {
                List<VEvent> events = ical.getEvents();
                for (VEvent event : events) {
                    handleEvent(event, jsonArray, iCalLink);
                }
            }
            // Delete the temporary file
            Files.deleteIfExists(tempFile);
            return jsonArray;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void handleEvent(VEvent event, JSONArray jsonArray, URL iCalLink) {
        String summary = event.getSummary().getValue();
        Date startDate = event.getDateStart().getValue();
        DateEnd dateEnd = event.getDateEnd();
        Date endDate = dateEnd != null ? dateEnd.getValue() : null;
        DurationProperty duration = event.getDuration();
        RecurrenceRule recurrenceRule = event.getRecurrenceRule();

        CalendarEvent calendarEvent = new CalendarEvent(iCalLink, summary, startDate, endDate, recurrenceRule);
        calendarEvent.calculateEndFromDuration(duration.getValue());

        if (calendarEvent.getRecurrenceRule() != null) {
            DateIterator iterator = calendarEvent.getRecurrenceIterator();
            while (iterator.hasNext()) {
                Date occurrence = iterator.next();
                CalendarEvent occurrenceEvent = new CalendarEvent(iCalLink, summary, occurrence, null, null);
                occurrenceEvent.setEnd(new Date(occurrence.getTime() + duration.getValue().toMillis()));
                jsonArray.put(occurrenceEvent.toJson());
            }
        } else {
            jsonArray.put(calendarEvent.toJson());
        }
    }

}
