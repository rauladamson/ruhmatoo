package ical;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import servlet.CalendarDataServlet;
import user.User;
import user.UserCache;

public class ICalUrlConversionTest {

    //@Disabled
    @Test
    public void testConvert() {
        User userObj = new User(); // luuakse uus kasutaja objekt
        UserCache.addUser(userObj); // objekt lisatakse vahemällu

        String iCalUrl = "https://ois2.ut.ee/api/timetable/personal/link/8bf38d9ab00c498fa52393683212b641/et";
        iCalObj ical = CalendarDataServlet.convertUrl(iCalUrl);

        System.out.println(UserCache.getUser());
        // Asserting that the conversion did not return null
        assertNotNull(ical, "The conversion should not return null");

        // Asserting the size of the JSONArray
        assertTrue(ical.getEvents().size() > 0, "The JSONArray should contain at least one event");

        // Looping through the events and printing them
        /*for (Object event : events) { //.getJSONArray("events")
            System.out.println(event);
        }*/
    }
}
