package ical;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

public class ICalToJsonTest {
    public static void main(String[] args) {
        Scanner URL = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter iCal link");
        JSONArray events = ICalToJson.convert(URL.nextLine());

        System.out.println(events.length());
        for (int i = 0; i < events.length(); i++) {
            JSONObject event = events.getJSONObject(i);
            System.out.println(event);

        }
    }
}
