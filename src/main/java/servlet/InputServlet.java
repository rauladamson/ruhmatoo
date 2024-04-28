package servlet;

import java.io.FileWriter;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Map;
import java.util.Arrays;
//import java.util.HashMap;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

//import pdf.PDFPrintTest;
import org.json.JSONObject;
import pdfsave.FetchData;
import oppeaine.Oppeaine;

import org.json.JSONArray;
import org.json.JSONObject;

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

        Map<String, String[]> inputsMap = request.getParameterMap(); // sisend teisedatakse Mapiks
        ArrayList<Oppeaine> oppeained = new ArrayList<>(); // luuakse uus Õppeaine objektide list
        //HashMap<String, String[]> inputsMap = new PDFPrintTest(null).getInputsMap(); // etapp 1 - vana
        //StringBuilder builder = new StringBuilder(); // luuakse StringBuilder objekt

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

                    Oppeaine oa = FetchData.fetchAPIData(paramValues[0]); // luuakse uus Õppeaine objekt
                    // TODO kontrolli paramValues pikkust - kas on alati ainult üks el?
                    //builder.append("Parameter obj: ").append(oa); // vastuse sisule lisatakse Õppeaine objekt

                    // Õppeaine object to Json file
                    JSONObject jo = new JSONObject(oa);
                    jo.put("uuid", oa.getUuid()); // Uuid
                    jo.put("data", oa.getData()); // Data
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
                    //builder.append("Parameter name: ").append(paramName); // vastuse sisule lisatakse võti
                    //builder.append("Parameter values: ").append(Arrays.toString(paramValues)); // vastuse sisule lisatakse väärtus
                    addJsonArrayToJsonObject(jsonObject, "text-input", paramValues);
                }

            }

            //String text = builder.toString();

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
    }
}