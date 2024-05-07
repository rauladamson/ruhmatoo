package ical;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import servlet.CalendarDataServlet;

public class ICalConversionTest {

    @Disabled
    @Test
    public void testConvert() {
        String iCalUrl = "https://ois2.ut.ee/api/timetable/personal/link/4532c65c9c0b42db9865bfbc3fe2be5e/et";
        JSONObject events = CalendarDataServlet.convert(iCalUrl);

        System.out.println(events);
        // Asserting that the conversion did not return null
        assertNotNull(events, "The conversion should not return null");

        // Asserting the size of the JSONArray
        assertTrue(events.length() > 0, "The JSONArray should contain at least one event");

        // Looping through the events and printing them
        for (Object event : events.getJSONArray("events")) {
            System.out.println(event);
        }
    }
}
