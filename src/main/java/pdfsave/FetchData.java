package pdfsave;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import oppeaine.Oppeaine;

public class FetchData {
    public static String main(String[] args) {
        StringBuilder result = new StringBuilder();
        for (String courseUrl : args) {
            Oppeaine response = fetchAPIData(courseUrl);

            System.out.println(response); //For now,

            result.append(response);
        }
        return result.toString();
    }

    /**
     * Funktsioon Httpst JSON textiks tegemiseks, et seda hiljem töödelda.
     * @param urlString "link OIS API leheküljele"
     * @return String kõik leheküljel olevaga.
     */
    public static Oppeaine fetchAPIData(String urlString) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(generateAPILink(urlString));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");

            if (urlConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + urlConnection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                result.append(output);
            }

            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Oppeaine(result.toString());
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
