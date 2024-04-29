package servlet;

import OIS_API.CoursesApi;
import oppeaine.AineCache;
import oppeaine.Oppeaine;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/inputServlet")
public class InputServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;


    public InputServlet() {
        super();
        AineCache.updateCacheFromFile();
    }

    protected void addJsonArrayToJsonObject(JSONObject jsonObject, String key, Object value) {
        if (!jsonObject.has(key)) {
            jsonObject.put(key, new JSONArray());
        }
        jsonObject.getJSONArray(key).put(value);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String[]> inputsMap = request.getParameterMap(); // sisend teisedatakse Mapiks

        // Assume you have a JSON object like this: {k1 : array, k2 : array, k3 : array}
        JSONObject jsonObject = new JSONObject();

        if (inputsMap.isEmpty()) {
            return;
        } // kui sisend ei ole tühi, siis töödeldakse vastuse sisust asjakohaseid väärtused

        // System.out.println(inputsMap);
        for (String paramName : inputsMap.keySet()) { // sisendväärtuste iteratsioon
            String[] paramValues = inputsMap.get(paramName); // võtamele vastava väärtuse massiv massiiv

            if (paramValues.length != 1) { // Ootame sisendit kindlas vormis
                throw new IOException("Vale sisend: " + Arrays.toString(paramValues));
            }

            if (paramName.contains("url-input")) {
                Pattern leiaAinekoodUrlist = Pattern.compile("[A-Z]{4}\\.[0-9]{2}\\.[0-9]{3}");
                Matcher matcher = leiaAinekoodUrlist.matcher(paramValues[0]);

                if(!matcher.find()) {
                    System.out.println(paramValues[0]);
                    throw new RuntimeException("EI leidnud koodi URL-ist >:(");
                }

                Oppeaine oa = AineCache.getAine(matcher.group());

                // Õppeaine object to Json file
                JSONObject jo = oa.convertToJson();

                String filename = paramName + ".json";
                try (FileWriter file = new FileWriter(filename)) {
                    file.write(jo.toString());
                    file.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                addJsonArrayToJsonObject(jsonObject, "url-input", oa);
            } else if (paramName.contains("cal-input")) {

                /* OTSE KLIENDILE VÄLJASTAMINE:

                request.setAttribute("icalUrl", paramValues[0]);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/calendarData");
                dispatcher.forward(request, response);*/

                // kuna me tahame kalendrit vahepeal töödelda, siis ei sa atulemust kohe tagasi saata
                CalendarDataServlet calendarDataServlet = new CalendarDataServlet();
                String calendarData = calendarDataServlet.getCalendarData(paramValues[0]);
                /*if (calendarData != null) {
                    builder.append(calendarData);
                }*/
                addJsonArrayToJsonObject(jsonObject, "cal-input", calendarData);
            } else { // TODO
                Oppeaine oa = AineCache.getAine(paramValues[0]);
                //builder.append("Parameter name: ").append(paramName); // vastuse sisule lisatakse võti
                //builder.append("Parameter values: ").append(Arrays.toString(paramValues)); // vastuse sisule lisatakse väärtus
                addJsonArrayToJsonObject(jsonObject, "text-input", paramValues);
            }

        }


        response.setContentType("application/json"); // vastus saadetakse JSON-kujul
        response.setCharacterEncoding("UTF-8"); // kodeering on UTF-8
        response.getWriter().write(String.valueOf(jsonObject)); // JSON-sõne vastusesse kirjutamine

            /* String text = builder.toString();
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(text);
            */
        // TODO salvesta iga massiivi obj faili (KUI uuid hulgas pole): json
        // TODO kirjuta uuid eraldi faili
        // TODO uuid-d kuhugi cahce-i
    }

    @Override
    public void destroy() {
        AineCache.writeCacheToFile();
        super.destroy();
    }
}
