package servlet;

import java.io.*;
import oppeaine.AineCache;
import oppeaine.KasutajaOppeaine;
import oppeaine.Oppeaine;
import user.User;
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
import java.util.Map;
import java.util.UUID;
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
        if (!jsonObject.has(key)) {jsonObject.put(key,  new JSONArray());} // TODO asendada serialiseerimisega
        if (value instanceof Oppeaine) {jsonObject.getJSONArray(key).put(((Oppeaine) value).convertToJsonForDisplay());} // Õppeaine objektil kasutada sisemist JSON-tagastus meetodit
        else {jsonObject.getJSONArray(key).put(value);} // Muidu arvaku ise, mida teha (vist tundmatu objekti korral kutsutakse välja toString?)
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO asendata mapi iteratsiooni Jacksoniga või muu parallellne lähenemine
        Map<String, String[]> inputsMap = request.getParameterMap(); // sisend teisedatakse Mapiks
        JSONObject jsonObject = new JSONObject(); // luuakse uus JSON objekt
        File tempFile;

        User user = UserCache.getUser();
        HashMap<UUID, KasutajaOppeaine> userCourses = user.getUserCourses();

        if (inputsMap.isEmpty()) {System.out.println("Tühi sisend");return;}
        // TODO arraysse lisamine kustutada

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
                if(!userCourses.containsKey(oa.getCode())) {user.addCourse(new KasutajaOppeaine(UUID.fromString(oa.getProperty("uuid")), oa.getECTs()), oa.getECTs());}

                addJsonArrayToJsonObject(jsonObject, "course-input", oa);

            } else if (paramName.contains("text-input")){ // muul juhul on tegemist ainekoodiga
                Oppeaine oa = AineCache.getAine(paramValues[0]);
                addJsonArrayToJsonObject(jsonObject, "course-input", oa);
                if(!userCourses.containsKey(oa.getCode())) {user.addCourse(new KasutajaOppeaine(UUID.fromString(oa.getProperty("uuid")), oa.getECTs()), oa.getECTs());}

            } else {

                iCalObj iCalObj;
                if (paramName.contains("cal-input")) {
                     iCalObj = CalendarDataServlet.convertUrl(paramValues[0]);
                    UserCache.getUser().addCalendar(iCalObj); // kasutaja kalendri lisamine

                    System.out.println(iCalObj.toJson().toString());
                    if (!userCourses.isEmpty()) {addJsonArrayToJsonObject(jsonObject, "course-input", userCourses.values());} // kui sisendist leiti kursusi, siis lisatakse need vastusesse

                    addJsonArrayToJsonObject(jsonObject, "ect-total", user.getTotalECTs());
                    addJsonArrayToJsonObject(jsonObject, "cal-input", iCalObj.toJson().toString());
                } else {

                    iCalObj = CalendarDataServlet.convertJson(paramValues[0]);
                    if (paramName.equals("mod-cal")){ // kui tegemist on muudetud kalendriga
                        tempFile = iCalObj.saveToFile(true);
                        addJsonArrayToJsonObject(jsonObject, "cal-save", tempFile.getAbsolutePath());
                    }

                    if (paramName.equals("generate")){ // kui tegemist on muudetud kalendriga
                        tempFile = iCalObj.saveToFile(false);
                        addJsonArrayToJsonObject(jsonObject, "cal-url", tempFile.getName());
                    }

                }
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
        AineCache.writeCacheToDatabase();
        AineCache.writeCacheToFile(); // MAttias: muidu ei uuendu cache kunagi
        //UserCache.writeCacheToFile();
        super.destroy();
    }
}
