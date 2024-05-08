package servlet;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import OIS_API.CoursesApi;
import oppeaine.Oppeaine;


@WebServlet("/inputServlet")
public class InputServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public InputServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void addJsonArrayToJsonObject(JSONObject jsonObject, String key, Object value) {
        if (!jsonObject.has(key)) {
            jsonObject.put(key,  new JSONArray());
        }
        jsonObject.getJSONArray(key).put(value);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("InputServlet doPost");
        Map<String, String[]> inputsMap = request.getParameterMap(); // sisend teisedatakse Mapiks
        ArrayList<Oppeaine> oppeained = new ArrayList<>(); // luuakse uus Õppeaine objektide list

        // Assume you have a JSON object like this: {k1 : array, k2 : array, k3 : array}
        JSONObject jsonObject = new JSONObject();

        if (!inputsMap.isEmpty()) { // kui sisend ei ole tühi, siis töödeldakse vastuse sisust asjakohaseid väärtused

           // System.out.println(inputsMap);
            for (String paramName : inputsMap.keySet()) { // sisendväärtuste iteratsioon
                String[] paramValues = inputsMap.get(paramName); // võtamele vastava väärtuse massiv massiiv

                //System.out.println("Parameter name: " + paramName);
                //System.out.println("Parameter values: " + Arrays.toString(paramValues));

                if (paramName.contains("url-input")) {

                    //builder.append("Parameter name: ").append(paramName); // vastuse sisule lisatakse võti

                    Oppeaine oa = CoursesApi.getAineFromUserURL(paramValues[0]); // luuakse uus Õppeaine objekt
                    // TODO kontrolli paramValues pikkust - kas on alati ainult üks el?
                    //builder.append("Parameter obj: ").append(oa); // vastuse sisule lisatakse Õppeaine objekt

                    JSONObject jo = new JSONObject(oa);  // Õppeaine object to Json file
                    String joString = jo.toString();

                    String filename = paramName + ".json";
                    try (FileWriter file = new FileWriter(filename)) {
                        file.write(joString);
                        file.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    addJsonArrayToJsonObject(jsonObject, "url-input", oa);
                    oppeained.add(oa); // Õppeaine objekt lisatakse Õppeaine objektide listi

                } else if (paramName.contains("cal-input")) {

                    System.out.println("Calendar input: " + paramValues[0]);
                    // kuna me tahame kalendrit vahepeal töödelda, siis ei sa atulemust kohe tagasi saata
                    CalendarDataServlet calendarDataServlet = new CalendarDataServlet();
                    String calendarData = calendarDataServlet.convert(paramValues[0]).toString();

                    //System.out.println(calendarData);
                    addJsonArrayToJsonObject(jsonObject, "cal-input", calendarData);
                } else { // TODO
                    addJsonArrayToJsonObject(jsonObject, "text-input", paramValues);
                }
            }

            System.out.println(jsonObject);

            response.setContentType("application/json"); // vastus saadetakse JSON-kujul
            response.setCharacterEncoding("UTF-8"); // kodeering on UTF-8
            response.getWriter().write(String.valueOf(jsonObject)); // JSON-sõne vastusesse kirjutamine
            //response.getWriter().write(String.valueOf(jsonObject)); // JSON-sõne vastusesse kirjutamine

            // TODO salvesta iga massiivi obj faili (KUI uuid hulgas pole): json
            // TODO kirjuta uuid eraldi faili
            // TODO uuid-d kuhugi cahce-i
        }
    }
}
