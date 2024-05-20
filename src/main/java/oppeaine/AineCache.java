package oppeaine;

import OIS_API.CoursesApi;
import database.DBConnector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import ical.CalendarEvent;
import ical.CalendarEventSerializer;
import ical.iCalObj;
import org.json.JSONObject;
import pdfsave.JsonFileReader;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class AineCache {
    private static final HashMap<String, Oppeaine> ained = new HashMap<>();
    private static final ArrayList<Oppeaine> ainedArrayList = new ArrayList<>();
    private static final String cacheFile = "salvestatudOppeained.json";


    public static void updateCacheFromFile() {

        for (Oppeaine aine: JsonFileReader.readOppeained("salvestatudOppeained.json")) {
            ained.put(aine.getCode(), aine);
            ainedArrayList.add(aine);
        }
        System.out.println("Cache'i loeti failist " + cacheFile + " " + ained.size() + " õppeainet.");
    }

    public static void updateCacheFromDatabase() {

        DBConnector db = DBConnector.instance;
        ResultSet rs = db.getAllFromTable("course");
        try {
            while (rs.next()) {
                ained.put(rs.getString("course_name"), new Oppeaine()); // TODO see inner json tuleb korda teha
            }
        } catch (Exception e) {e.printStackTrace();}

        System.out.println("Cache'i loeti andmebaasist " + ained.size() + " õppeainet.");
    }

    public static void clearCache() {
        ained.clear();
    }

    public static void writeCacheToFile() {JsonFileReader.writeOppeained(cacheFile, ainedArrayList);}

    public static void writeCacheToDatabase() {DBConnector.instance.updateOppeained(ained);}

    public static void printCache() {
        System.out.println("\n===============");
        System.out.println("Cache sisaldab:");
        for (Oppeaine aine: ainedArrayList) {
            System.out.println(aine.toString());
        }
        System.out.println("===============");
    }

    public static Oppeaine getAine(String kood) {
        if (ained.isEmpty()) {updateCacheFromFile();}

        System.out.println("Otsitakse ainet koodiga " + kood);
        Oppeaine aine;
        try {
            aine = ained.get(kood);
            //System.out.println("Aine leiti vahemälust: " + aine);
            //System.out.println("Aine leiti vahemälust: " + aine);
          /*  if (aineHasChanged(aine)) { // TODO: Api päring ning seotud meetod parandada. aineHasChanged ja getAineFromCode loogikat võiks ka kuidagi paremini kokku panna.
                Oppeaine uusAine = CoursesApi.getAineFromCode(aine.getCode());

                ained.put(uusAine.getCode(), uusAine);*/
       } catch (Exception e) {
           System.err.println("Aine pole vahemälus, otsime ainet:");
           aine = CoursesApi.getAineFromCode(kood);
            ained.put(aine.getCode(), aine);
       }

        return aine;
    }

    // Mattias: Märkisin praegu selle private-iks, et keelata selle kasutamist. Kui tekib tunne, et seda
    //          on ikka vaja kasutada, paluks teavitada mind enne.
    /**
     * Lisab õppeaine objekti puhvrisse. NB! Ained lisatakse automaatselt getAine() meetodi korral.
     * Hetkel pole see meetod kasutuses. Ilmselt pole sellel enam tulevikus kasutust, kuna see vajab
     * lisamiseks Oppeaine objekti, aga (algseid) Oppeaine objekte peaksime me saama ainult AineCache.getAine()
     * meetodilt, mis ise juba tegeleb vajadusel aine lisamisega.
     * Praegu ikkagi jätab igaksjuhuks alles.
     * @param oa Õppeaine objekt
     * @return True, kui ainet polnud varem olemas. False, kui oli.
     */
    private static boolean addAine(Oppeaine oa) {
        //TODO: optimiseerida meetod kasutades kas latestUuid või latestChanged
        //TODO: kasutada HashSeti(?)
        //System.out.println(oa.convertToJson().toString());
        printCache();
        if (ained.containsKey(oa)) {
            System.out.println("Prooviti lisada aine " + oa.toString() + " mis oli juba olemas.\n");
            System.out.println();
            return false;
        } else {
            ained.put(oa.getCode(), oa);
            System.out.println("Lisati edukalt aine " + oa.toString() + "\n");
            return true;
        }
    }

    private static boolean aineHasChanged(Oppeaine aine) {
        return !(CoursesApi.getLatestCourseChange(aine).equals(aine.getLastUpdated()));
    }
}
