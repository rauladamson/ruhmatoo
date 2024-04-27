package ical;

import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.io.text.ICalReader;
import biweekly.property.DateEnd;
import biweekly.property.DurationProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

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
                            // Assuming the start date is not null, calculate the end date based on duration
                            // This is a simplified example; actual implementation may vary based on your requirements
                            // and the specifics of how durations are handled in your iCal files.
                            // You might need to adjust this logic based on the time zone and the specifics of the iCal format.
                            jsonObject.put("end", duration.getValue().add(event.getDateStart().getValue()));
                        }
                    }

                    jsonArray.put(jsonObject);
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
}



