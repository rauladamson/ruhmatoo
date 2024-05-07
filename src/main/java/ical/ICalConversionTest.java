package ical;

import org.json.JSONArray;
import org.json.JSONObject;
import servlet.CalendarDataServlet;

import java.util.Scanner;

public class ICalConversionTest {
    public static void main(String[] args) {
        Scanner URL = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter iCal link");
        JSONObject iCalObj = CalendarDataServlet.convert(URL.nextLine());

        System.out.println(iCalObj.length());
        for (int i = 0; i < iCalObj.length(); i++) {
            JSONObject event = iCalObj.getJSONObject(String.valueOf(i));
            System.out.println(event);

        }
    }
}
