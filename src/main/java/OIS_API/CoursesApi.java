package OIS_API;

import oppeaine.Oppeaine;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CoursesApi {
    private static final String baseUrl = "https://ois2.ut.ee/api/";

    /**
     * Meetod, mis teeb API-le GET-calli.
     * @param url Täispikkuses URL, millele request teha ( nt http://ois2.ut.ee/api/coures/LTAT.03.003 )
     * @return Otsene output.
     * @throws IOException
     */
    private static String callApiDirectlyGET(URL url) throws IOException {
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Accept", "application/json");
        // 302 välitmsieks
        urlConnection.setInstanceFollowRedirects(true);

        System.out.println("URL: " + url.toString());
        if (urlConnection.getResponseCode() != 200 && urlConnection.getResponseCode() != 302) {
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

    // TODO: panna kõik get* meetodit kokku ühte getAine() meetodisse (?)
    /**
     * Funktsioon Httpst JSON textiks tegemiseks, et seda hiljem töödelda.
     * @param urlString "link OIS API leheküljele"
     * @return String kõik leheküljel olevaga.
     */
    public static Oppeaine getAineFromUserURL(String urlString) {
        String result = null;
        try {
            URL url = new URL(generateAPILink(urlString));
            result = callApiDirectlyGET(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            throw new RuntimeException("Viga aine leidmises");
        }

        return new Oppeaine(result);
    }

    /**
     * Leiab aine viimase versiooni struktuurikoodi põhjal.
     * @param code Ainekood kujul AAAA.00.0000.
     * @return Uus Õppeaine objekt.
     */
    public static Oppeaine getAineFromCode(String code) {
        String result = null;
        try {
            URL url = new URL (baseUrl + "courses/" + code);
            result = callApiDirectlyGET(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == null || result.equals("")) {
            throw new RuntimeException("Viga aine leidmises");
        }
        System.out.println(result);
        return new Oppeaine(result);
    }

    /**
     * Giga hakk, api lingi genereerimiseks :)
     */
    public static String generateAPILink(String courseUrl) {
        if (courseUrl.contains("details") || courseUrl.contains("#")) {
            String newlink = courseUrl.replace("#", "api");
            newlink = newlink.replace("/details", "");
            if (newlink.contains("versions")) {
                return newlink;
            }
            newlink = newlink.replace("version", "versions");
            return newlink;
        }
        return courseUrl;
    }
}
