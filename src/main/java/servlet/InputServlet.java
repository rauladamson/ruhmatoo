package servlet;

import java.io.*;
import oppeaine.AineCache;
import oppeaine.KasutajaOppeaine;
import oppeaine.Oppeaine;
import user.UserCache;

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
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ical.iCalObj;

@WebServlet("/inputServlet")
public class InputServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public InputServlet() {
        super();
        AineCache.updateCacheFromFile();
        //UserCache.updateCacheFromFile();
    }

    protected void addJsonArrayToJsonObject(JSONObject jsonObject, String key, Object value) {
        if (!jsonObject.has(key)) {jsonObject.put(key,  new JSONArray());}
        if (value instanceof Oppeaine) {jsonObject.getJSONArray(key).put(((Oppeaine) value).convertToJsonForDisplay());} // Õppeaine objektil kasutada sisemist JSON-tagastus meetodit
        else {jsonObject.getJSONArray(key).put(value);} // Muidu arvaku ise, mida teha (vist tundmatu objekti korral kutsutakse välja toString?)
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO asendata mapi iteratsiooni Jacksoniga või muu parallellne lähenemine
        Map<String, String[]> inputsMap = request.getParameterMap(); // sisend teisedatakse Mapiks
        JSONObject jsonObject = new JSONObject(); // luuakse uus JSON objekt
        File tempFile = null;

        if (inputsMap.isEmpty()) {System.out.println("Tühi sisend");return;}

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

                Oppeaine oa = AineCache.getAine(matcher.group());  // Siin pole eraldi vaja ainet faili salvestada. AineCache.getAine() tegeleb sellega ise automaatselt.
                addJsonArrayToJsonObject(jsonObject, "course-input", oa);

            } else if (paramName.contains("cal-input")) { // kuna me tahame kalendrit vahepeal töödelda, siis ei saa tulemust kohe tagasi saata
                CalendarDataServlet calendarDataServlet = new CalendarDataServlet();
                JSONObject calendarData = calendarDataServlet.convertUrl(paramValues[0]);

                HashMap<String, KasutajaOppeaine> userCourses = UserCache.getUserCourses();
                if (!userCourses.isEmpty()) {addJsonArrayToJsonObject(jsonObject, "course-input", userCourses.values());} // kui sisendist leiti kursusi, siis lisatakse need vastusesse
                addJsonArrayToJsonObject(jsonObject, "ect-total", UserCache.getUser().getTotalECTs());

                addJsonArrayToJsonObject(jsonObject, "cal-input", calendarData.toString());

            } else if (paramName.contains("text-input")){ // muul juhul on tegemist ainekoodiga
                Oppeaine oa = AineCache.getAine(paramValues[0]);
                // Samuti eemaldatud cache'i aine lisamine.
                addJsonArrayToJsonObject(jsonObject, "course-input", oa);

            } else if (paramName.equals("mod-cal")){ // kui tegemist on muudetud kalendriga
                CalendarDataServlet calendarDataServlet = new CalendarDataServlet();
                iCalObj iCalObj = calendarDataServlet.convertJson(paramValues[0]);
                tempFile = iCalObj.saveToFile();
                addJsonArrayToJsonObject(jsonObject, "cal-save", tempFile.getAbsolutePath());

            } else if (paramName.equals("generate")){ // kui tegemist on muudetud kalendriga
                CalendarDataServlet calendarDataServlet = new CalendarDataServlet();
                iCalObj iCalObj = calendarDataServlet.convertJson(paramValues[0]);
               // System.out.println(iCalObj);
            }
        }


        response.setContentType( "application/json"); // vastus saadetakse JSON-kujul
        response.setCharacterEncoding("UTF-8"); // kodeering on UTF-8
        response.getWriter().write(String.valueOf(jsonObject)); // JSON-sõne vastusesse kirjutamine

        //tempFile.delete();
        // Mattias: Varasemad todo'd eemaldatud, ainete cache'imisega tegeletakse AineCache klassis.
    }

    // Public method to call doGet
    public void callDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    public void destroy() {
        // Enne kui server läheb kinni, salvestada puhvris olevad ained.
        AineCache.writeCacheToFile();
        UserCache.writeCacheToFile();
        super.destroy();
    }
}
