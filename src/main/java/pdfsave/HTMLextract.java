package pdfsave;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HTMLextract {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://ois2.ut.ee/api/courses/LTAT.03.007/versions/fde0bca8-705f-74c9-456e-e68104c23b53").get();
        Elements elements = doc.body().select("*");

        for (Element element : elements) {
            System.out.println(element.ownText());
        }
    }

    private static void log(String msg, String... vals) {
        System.out.println(String.format(msg, vals));
    }
}