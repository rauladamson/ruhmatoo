package servlet;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oppeaine.AineCache;
import org.json.JSONArray;
import org.json.JSONObject;

import OIS_API.CoursesApi;
import oppeaine.Oppeaine;

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
            jsonObject.put(key,  new JSONArray());
        }
        jsonObject.getJSONArray(key).put(value);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String[]> inputsMap = request.getParameterMap(); // sisend teisedatakse Mapiks
        JSONObject jsonObject = new JSONObject(); // luuakse uus JSON objekt

        if (!inputsMap.isEmpty()) { // kui sisend ei ole tühi, siis töödeldakse vastuse sisust asjakohaseid väärtused

            for (String paramName : inputsMap.keySet()) { // sisendväärtuste iteratsioon
                String[] paramValues = inputsMap.get(paramName); // võtamele vastava väärtuse massiv massiiv

                // TODO kas seda on vaja? sisendit valideeritakse juba
                /*if (paramValues.length != 1) { // Ootame sisendit kindlas vormis
                    throw new IOException("Vale sisend: " + Arrays.toString(paramValues));
                }*/

                //System.out.println("Parameter name: " + paramName);
                //System.out.println("Parameter values: " + Arrays.toString(paramValues));

                if (paramName.contains("url-input")) {

                    Pattern leiaAinekoodUrlist = Pattern.compile("[A-Z]{4}\\.[0-9]{2}\\.[0-9]{3}");
                    Matcher matcher = leiaAinekoodUrlist.matcher(paramValues[0]);

                    if(!matcher.find()) {
                        System.out.println(paramValues[0]);
                        throw new RuntimeException("EI leidnud koodi URL-ist >:(");
                    }

                    Oppeaine oa = AineCache.getAine(matcher.group());

                    // TODO siin tuleb formaadi üle vaadata
                    JSONObject jo = new JSONObject(oa);
                    String joString = jo.toString();
                   // JSONObject jo = oa.convertToJson(); // Õppeaine object to Json file


                    String filename = paramName + ".json";
                    try (FileWriter file = new FileWriter(filename)) {
                        file.write(joString);
                        file.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    addJsonArrayToJsonObject(jsonObject, "url-input", oa);
                    AineCache.addAine(oa); // Õppeaine objekt lisatakse Õppeaine objektide listi

                } else if (paramName.contains("cal-input")) { // kuna me tahame kalendrit vahepeal töödelda, siis ei saa tulemust kohe tagasi saata
                    CalendarDataServlet calendarDataServlet = new CalendarDataServlet();
                    String calendarData = calendarDataServlet.convert(paramValues[0]).toString();
                    addJsonArrayToJsonObject(jsonObject, "cal-input", calendarData);
                } else { // muul juhul on tegemist ainekoodiga
                    Oppeaine oa = AineCache.getAine(paramValues[0]);
                    AineCache.addAine(oa);
                    addJsonArrayToJsonObject(jsonObject, "text-input", paramValues);
                }
            }

            response.setContentType("application/json"); // vastus saadetakse JSON-kujul
            response.setCharacterEncoding("UTF-8"); // kodeering on UTF-8
            response.getWriter().write(String.valueOf(jsonObject)); // JSON-sõne vastusesse kirjutamine

            // TODO salvesta iga massiivi obj faili (KUI uuid hulgas pole): json
            // TODO kirjuta uuid eraldi faili
            // TODO uuid-d kuhugi cahce-i
        }
    }

    @Override
    public void destroy() {
        AineCache.writeCacheToFile();
        super.destroy();
    }
}
