package OIS_API;

import oppeaine.AineCache;
import oppeaine.Oppeaine;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoursesApi {
    //TODO: Teha valmis meetod ainete muutuste saamiseks - hetkel kasutavad kõik meetodit ,,heavy" api kutset.
    private static final String baseUrl = "https://ois2.ut.ee/api/";
    private static final Pattern leiaAinekoodStringist = Pattern.compile("[A-Z]{4}\\.[0-9]{2}\\.[0-9]{3}");

    /**
     * generic meetod Regex-iga ainekoodi leidmiseks igaks tekstist
     * @param input Süne, kus sees on ainekod.
     * @return Ainekood.
     */
    private static String extractAinekoodFromString(String input) {
        Matcher matcher = leiaAinekoodStringist.matcher(input);

        if(!matcher.find()) {
            System.out.println(input);
            throw new RuntimeException("EI leidnud koodi argumendist");
        }

        return matcher.group();
    }

    /**
     * Meetod, mis teeb API-le GET-calli.
     * @param url Täispikkuses URL, millele request teha ( nt http://ois2.ut.ee/api/coures/LTAT.03.003 )
     * @return Otsene output.
     * @throws IOException kui midagi läheb valesti.
     */
    public static String callApiDirectlyGET(URL url) throws IOException {
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Accept", "application/json");
        // 302 välitmsieks
        urlConnection.setInstanceFollowRedirects(true);

        if (urlConnection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + urlConnection.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));

        String output;
        while ((output = br.readLine()) != null) {
            result.append(output);
        }
        urlConnection.disconnect();

        return result.toString();
    }

    /**
     * Otsib struktuurikoodi antud sõnest.
     * Leiab aine viimase versiooni struktuurikoodi põhjal.
     * @param stringWhichContainsCode Sõne, mis sisaldab ainekoodi kujul AAAA.00.0000 (ning ei sisalda muid samas vormis teksti, mis ei ole ainekood)
     * @return Uus Õppeaine objekt.
     */
    public static Oppeaine getAineFromCode(String stringWhichContainsCode) {
        String code = extractAinekoodFromString(stringWhichContainsCode);
        String result = null;
        try {
            URL url = new URL (baseUrl + "courses/" + code);
            result = callApiDirectlyGET(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            throw new RuntimeException("Viga aine leidmises, kood: " + code);
        }
        return new Oppeaine(result);
    }

    /**
     * Funktsioon, mis teeb "kergema" api calli, mida kasutada nt kontrollimiseks kas aine uus versioon on väljas,
     * Kasutab mitmekordselt vähem andmemakhtu kui getAineFromCode().
     * // Mattiss: Ei usu et see on lightweight? Võrdle objekte getAineFromCode(kood).convertToJson() vs lightweightOppeaineApiCall(kood).
     * //          Näha on suurt erinevust tagastatavate objektide vahel.
     * @param stringWhichContainsCode Sõne, mis sisaldab ainekoodi kujul AAAA.00.0000 (ning ei sisalda muid samas vormis teksti, mis ei ole ainekood)
     * @return JSONObject, mis sisaldab aine koodi, uuid-sid ja viimase uuenduse kuupöeva.
     */
    public static JSONObject lightweightOppeaineApiCall(String stringWhichContainsCode) {
        String code = extractAinekoodFromString(stringWhichContainsCode);
        String result = null;
        try {
            URL url = new URL (baseUrl + "courses/?code=" + code);
            result = callApiDirectlyGET(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            throw new RuntimeException("Viga aine leidmises, kood: " + code);
        }

        JSONArray tagastatudArray = new JSONArray(result);
        if (tagastatudArray.length() != 1) {
            throw new RuntimeException("API tagastas vigase objekti koodi " + code + " jaoks: " + result);
        }
        return tagastatudArray.getJSONObject(0);
    }

    /* Test lightweight ja tavalise koodi vahel.
    System.out.println("-------------");
    System.out.println(CoursesApi.lightweightOppeaineApiCall("LTAT.03.003").toString());
    System.out.println("-------------");
    System.out.println(AineCache.getAine("LTAT.03.003").convertToJson().toString());
    System.out.println("-------------");
    */

    public static String getLatestCourseChange(Oppeaine aine) {
        return lightweightOppeaineApiCall(aine.getCode()).getString("last_update");
    }
    public static String getLatestCourseChange(String ainekood) {
        return lightweightOppeaineApiCall(ainekood).getString("last_update");
    }
}
