package ical;

import java.util.Scanner;

public class ICalToJsonTest {
    public static void main(String[] args) {
        Scanner URL = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter iCal link");
        ICalToJson.convert(URL.nextLine());
    }
}
