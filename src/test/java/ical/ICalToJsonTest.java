package ical;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class ICalToJsonTest {

    @Disabled
    @Test
    public void testConvert() {
        String iCalUrl = "https://ois2.ut.ee/api/timetable/personal/link/4532c65c9c0b42db9865bfbc3fe2be5e/et";
        JSONArray events = ICalToJson.convert(iCalUrl);

        // Asserting that the conversion did not return null
        assertNotNull(events, "The conversion should not return null");

        // Asserting the size of the JSONArray
        assertTrue(events.length() > 0, "The JSONArray should contain at least one event");

        // Looping through the events and printing them
        for (int i = 0; i < events.length(); i++) {
            JSONObject event = events.getJSONObject(i);
            System.out.println(event);
        }
    }
}
