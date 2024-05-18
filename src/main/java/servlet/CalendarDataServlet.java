    package servlet;

    import biweekly.ICalendar;
    import biweekly.io.text.ICalReader;
    import com.google.gson.*;
    import ical.iCalObj;
    import ical.iCalObjSerializer;
    import org.json.JSONArray;
    import org.json.JSONObject;

    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.File;
    import java.io.IOException;
    import java.io.InputStream;
    import java.net.URL;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.nio.file.StandardCopyOption;
    import java.util.HashMap;

    @WebServlet("/calendarData")
    public class CalendarDataServlet extends HttpServlet {

        public static JSONObject convertUrl(String icalUrl) {
            try {
                Path tempFile = Files.createTempFile("ical", ".ics"); // ajutise faili loomine
                //Path tempFile = Paths.get("ical.ics"); // fail salvestatakse: saab sisu uurida
                URL iCalLink = new URL(icalUrl); // sisestatud iCal faili URL
                try (InputStream in = iCalLink.openStream()) {Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);} // TODO  mis siin tehakse=

                ICalReader reader = new ICalReader(tempFile.toFile()); // iCalReader objekti loomine
                ICalendar icals = reader.readAll().get(0); // kõigi sisestatud iCal failide lugemine

                //System.out.println(icals);
                iCalObj iCalObj = new iCalObj(iCalLink, icals.getEvents()); // uue iCalObj objekti loomine
                return iCalObj.toJson() ;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static iCalObj convertJson(String icals) {

            Gson gson = new Gson();
            HashMap<String, iCalObj> iCalObjs = new HashMap<>();

            return gson.fromJson(icals, iCalObj.class);
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           // System.out.println("CalendarDataServletTestDoGet()");
            String icalUrl = request.getParameter("icalUrl");
            if (icalUrl == null || icalUrl.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "icalUrl parameter is missing");
                return;
            }

            JSONObject jsonArray = this.convertUrl(icalUrl);

            //System.out.println(jsonArray.get("events"));
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
