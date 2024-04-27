    package servlet;

    import ical.ICalToJson;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.json.JSONArray;

    import java.io.IOException;

    @WebServlet("/calendarData")
    public class CalendarDataServlet extends HttpServlet {

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String icalUrl = request.getParameter("icalUrl");
            if (icalUrl == null || icalUrl.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "icalUrl parameter is missing");
                return;
            }

            JSONArray jsonArray = ICalToJson.convert(icalUrl);
            if (jsonArray == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to convert iCal data");
                return;
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            System.out.println(jsonArray);
            response.getWriter().write(jsonArray.toString());
        }
    }
