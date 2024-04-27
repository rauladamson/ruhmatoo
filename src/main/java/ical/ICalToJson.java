package ical;

import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.io.text.ICalReader;
import biweekly.property.DateEnd;
import biweekly.property.DurationProperty;
import biweekly.property.RecurrenceRule;
import biweekly.util.com.google.ical.compat.javautil.DateIterator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ICalToJson {

    public static void convert(String icalUrl) {
        try {
            Path tempFile = Files.createTempFile("ical", ".ics");

            try (InputStream in = new URL(icalUrl).openStream()) {
                Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }

            ICalReader reader = new ICalReader(tempFile.toFile());
            List<ICalendar> icals = reader.readAll(); // Read all iCalendars

            JSONArray jsonArray = new JSONArray();

            for (ICalendar ical : icals) {
                List<VEvent> events = ical.getEvents();
                for (VEvent event : events) {
                    handleEvent(event, jsonArray);
                }
            }
            System.out.println(jsonArray.length());
            System.out.println(jsonArray.toString());

            // Delete the temporary file
            Files.deleteIfExists(tempFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleEvent(VEvent event, JSONArray jsonArray) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("summary", event.getSummary().getValue());

        if (event.getDateStart() != null) {
            jsonObject.put("start", event.getDateStart().getValue().toString());
        }

        DateEnd dateEnd = event.getDateEnd();
        if (dateEnd != null) {
            jsonObject.put("end", dateEnd.getValue().toString());
        } else {
            // If there's no explicit end date, use the start date plus the duration
            DurationProperty duration = event.getDuration();
            if (duration != null) {
                jsonObject.put("end", duration.getValue().add(event.getDateStart().getValue()));
            }
        }

        RecurrenceRule recurrenceRule = event.getRecurrenceRule();
        if (recurrenceRule != null) {
            DateIterator iterator = recurrenceRule.getDateIterator(event.getDateStart().getValue(), TimeZone.getDefault());
            while (iterator.hasNext()) {
                Date occurrence = iterator.next();
                JSONObject occurrenceJson = new JSONObject(jsonObject); // Copy the original event details
                occurrenceJson.put("start", occurrence.toString());
                if (dateEnd != null) {
                    occurrenceJson.put("end", event.getDuration().getValue().add(occurrence));
                }
                jsonArray.put(occurrenceJson);
            }
        } else {
            jsonArray.put(jsonObject);
        }
    }
}
