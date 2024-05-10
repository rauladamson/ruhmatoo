    package servlet;
    import biweekly.ICalendar;
    import biweekly.io.text.ICalReader;

    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;

    import ical.iCalObj;
    import org.json.JSONArray;
    import org.json.JSONObject;

    import java.io.File;
    import java.io.IOException;
    import java.io.InputStream;
    import java.net.URL;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.StandardCopyOption;

    @WebServlet("/calendarData")
    public class CalendarDataServlet extends HttpServlet {

        public static JSONObject convert(String icalUrl) {
            try {
                Path tempFile = Files.createTempFile("ical", ".ics"); // ajutise faili loomine
                URL iCalLink = new URL(icalUrl); // sisestatud iCal faili URL
                try (InputStream in = iCalLink.openStream()) {Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);} // TODO  mis siin tehakse=

                ICalReader reader = new ICalReader(tempFile.toFile()); // iCalReader objekti loomine
                ICalendar icals = reader.readAll().get(0); // kõigi sisestatud iCal failide lugemine

                iCalObj iCalObj = new iCalObj(iCalLink, icals.getEvents()); // uue iCalObj objekti loomine
                return iCalObj.toJson() ;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           // System.out.println("CalendarDataServletTestDoGet()");
            String icalUrl = request.getParameter("icalUrl");
            if (icalUrl == null || icalUrl.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "icalUrl parameter is missing");
                return;
            }

            JSONObject jsonArray = this.convert(icalUrl);

            System.out.println(jsonArray.get("events"));
            if (jsonArray == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to convert iCal data");
                return;
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            File file = new File("calendar1.json");
            response.getWriter().write(jsonArray.toString());
        }
    }
