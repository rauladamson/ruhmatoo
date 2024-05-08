package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CalendarDataServletTest {

    @Disabled
    @Test
    public void testDoGet() throws Exception {
        // Creating mock objects
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        // Setting up mock to return a dummy icalUrl value
        Mockito.when(request.getParameter("icalUrl")).thenReturn("https://ois2.ut.ee/api/timetable/personal/link/8bf38d9ab00c498fa52393683212b641/et"); // semester

        //System.out.println("testDoGet()");
        // Creating a StringWriter to capture the response output
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(writer);

        // Calling the overridden doGet method
        CalendarDataServlet servlet = new CalendarDataServlet() {
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                super.doGet(req, resp);
            }
        };
        servlet.doGet(request, response);

        // Printing the response to the console
        writer.flush(); // Ensuring all output is written
        String output = stringWriter.toString();

       // System.out.println(output);
    }
}


