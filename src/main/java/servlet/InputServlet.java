package servlet;

import java.io.*;
import oppeaine.AineCache;
import oppeaine.Oppeaine;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ical.iCalObj;

@WebServlet("/inputServlet")
public class InputServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public InputServlet() {
        super();
        //AineCache.updateCacheFromFile();
    }

    protected void addJsonArrayToJsonObject(JSONObject jsonObject, String key, Object value) {
        if (!jsonObject.has(key)) {
            jsonObject.put(key,  new JSONArray());
        }
        if (value instanceof Oppeaine) { // Õppeaine objektil kasutada sisemist JSON-tagastus meetodit
            jsonObject.getJSONArray(key).put(((Oppeaine) value).convertToJsonForDisplay());
        } else { // Muidu arvaku ise, mida teha (vist tundmatu objekti korral kutsutakse välja toString?)
            jsonObject.getJSONArray(key).put(value);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //System.out.println("Reached doPost");
        Map<String, String[]> inputsMap = request.getParameterMap(); // sisend teisedatakse Mapiks
        JSONObject jsonObject = new JSONObject(); // luuakse uus JSON objekt
        boolean writeFile = false;
        String icalString = null;
        File tempFile = null;
       //System.out.println(inputsMap);
        if (inputsMap.isEmpty()) {
            return;
        }
        // kui sisend ei ole tühi, siis töödeldakse vastuse sisust asjakohaseid väärtused

        for (String paramName : inputsMap.keySet()) { // sisendväärtuste iteratsioon
            String[] paramValues = inputsMap.get(paramName); // võtamele vastava väärtuse massiv massiiv

            if (paramName.contains("url-input")) {

                Pattern leiaAinekoodUrlist = Pattern.compile("[A-Z]{4}\\.[0-9]{2}\\.[0-9]{3}");
                Matcher matcher = leiaAinekoodUrlist.matcher(paramValues[0]);

                if(!matcher.find()) {
                    System.out.println(paramValues[0]);
                    throw new RuntimeException("EI leidnud koodi URL-ist >:(");
                }

                Oppeaine oa = AineCache.getAine(matcher.group());
                // Siin pole eraldi vaja ainet faili salvestada. AineCache.getAine() tegeleb sellega ise automaatselt.

                addJsonArrayToJsonObject(jsonObject, "course-input", oa);

            } else if (paramName.contains("cal-input")) { // kuna me tahame kalendrit vahepeal töödelda, siis ei saa tulemust kohe tagasi saata
                CalendarDataServlet calendarDataServlet = new CalendarDataServlet();
                String calendarData = calendarDataServlet.convertUrl(paramValues[0]).toString();
                addJsonArrayToJsonObject(jsonObject, "cal-input", calendarData);
            } else if (paramName.contains("text-input")){ // muul juhul on tegemist ainekoodiga
                Oppeaine oa = AineCache.getAine(paramValues[0]);
                // Samuti eemaldatud cache'i aine lisamine.

                addJsonArrayToJsonObject(jsonObject, "course-input", oa);
            } else if (paramName.equals("mod-cal")){ // kui tegemist on muudetud kalendriga
                writeFile = true;
               //System.out.println(paramValues[0]);
                //System.out.println("mod-cal");
                CalendarDataServlet calendarDataServlet = new CalendarDataServlet();
                //System.out.println(paramValues[0].getClass());
                iCalObj iCalObj = calendarDataServlet.convertJson(paramValues[0]);
                tempFile = iCalObj.saveToFile();
                //response.setHeader("Content-Disposition", "attachment; filename=\"cal.ics\"");
                addJsonArrayToJsonObject(jsonObject, "cal-save", tempFile.getAbsolutePath());
            }
        }


        response.setContentType( "application/json"); // vastus saadetakse JSON-kujul
        response.setCharacterEncoding("UTF-8"); // kodeering on UTF-8
        response.getWriter().write(String.valueOf(jsonObject)); // JSON-sõne vastusesse kirjutamine


        //tempFile.delete();
        // Mattias: Varasemad todo'd eemaldatud, ainete cache'imisega tegeletakse AineCache klassis.
    }

    @Override
    public void destroy() {
        // Enne kui server läheb kinni, salvestada puhvris olevad ained.
        AineCache.writeCacheToFile();

        super.destroy();
    }
}
