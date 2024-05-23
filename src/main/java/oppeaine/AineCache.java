package oppeaine;

import OIS_API.CoursesApi;
import custom_debug_help.CustomDebugPrinter;
import database.DBConnector;
import pdfsave.JsonFileReader;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class AineCache {
    private static final HashMap<String, Oppeaine> ained = new HashMap<>(); // Mattias: siin oleks hea kasutada mingit kas custom HashSeti sarnast laadset objekti, mis lubaks koodi põhjal otsida, või siis peaks Oppeaine klassi ümber kirjutama
    private static final HashMap<UUID, String> uuidKoodiMap = new HashMap<>();
    //    private static final HashSet<Oppeaine> aineteHashSet = new HashSet<>();
    private static final String cacheFile = "salvestatudOppeained.json";


    public static void updateCacheFromFile() {
        for (Oppeaine aine : JsonFileReader.readOppeained("salvestatudOppeained.json")) {
            ained.put(aine.getCode(), aine);
        }
        System.out.println("Cache'i loeti failist " + cacheFile + " " + ained.size() + " õppeainet.");
    }

    public static void updateCacheFromDatabase() {

        DBConnector db = DBConnector.instance;
        ResultSet rs = db.getAllFromTable("course");
        try {
            while (rs.next()) {
                ained.put(rs.getString("course_name"), new Oppeaine()); // TODO see inner json tuleb korda teha
                // Mattias: see tuleks kindlasti eemaldada ning asendada meetodiga mis konstrueeerib õige Õppeaine objekti
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Cache'i loeti andmebaasist " + ained.size() + " õppeainet.");
    }

    public static void clearCache() {
        ained.clear();
    }

    public static void writeCacheToFile() {
        System.out.println("Writing cache with " + ained.size() + " elements to file.");
        JsonFileReader.writeOppeained(cacheFile, new ArrayList<>(ained.values()));
    }

    public static void writeCacheToDatabase() {
        DBConnector.instance.updateOppeained(ained);
    }

    public static void printCache() {
        System.out.println("\n===============");
        System.out.println("Cache sisaldab:");
        for (Oppeaine aine : ained.values()) {
            System.out.println(aine.toString());
        }
        System.out.println("===============");
    }

    /**
     * hetkel deprecated kuna AIneCache ei kasuta enam HashSeti
     * giga omega reflection häkk hetkel HashSeti piirangutest mööda hiilimiseks
     * https://stackoverflow.com/questions/26986587/accessing-a-hashset-using-the-hashcode-directly-java
     */
    private static <Oppeaine> Oppeaine getFromHashCode(final int hashcode, HashSet<Oppeaine> set) {
        // reflection stuff
        Field field;
        try {
            field = set.getClass().getDeclaredField("map");
        } catch (NoSuchFieldException e) {
            return null; // omega halb kood
        }
        field.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<Oppeaine, Object> interalMap;
        // get the internal map
        try {
            interalMap = (Map<Oppeaine, Object>) (field.get(set));
        } catch (IllegalAccessException e) {
            return null;
        }

        // attempt to find a key with an identical hashcode
        for (Oppeaine elem : interalMap.keySet()) {
            if (elem.hashCode() == hashcode) return elem;
        }
        return null;
    }

    public static Oppeaine getAine(String kood) {
        if (ained.isEmpty()) {
            CustomDebugPrinter.dbgMsgLvl1("getAine triggers empty cache. Cache file doesn't exist or didn't get read upon launch of servlet?");
            updateCacheFromFile();
        }

        if (ained.containsKey(kood)) { // kui oleme ainet varem näinud
            CustomDebugPrinter.dbgMsgLvl1("Aine " + kood + " leiti puhvrist");

            // Leiame, millal viimati uuendasime ainet
            long vimmatiUuendatudMinutites = ChronoUnit.MINUTES.between(ained.get(kood).getLastUpdatedByCache(), LocalDateTime.now());
            CustomDebugPrinter.dbgMsgLvl1("Viimasest uuendusest on möödunud " + ((vimmatiUuendatudMinutites < 100) ? vimmatiUuendatudMinutites : ">100") + " minutit");

            //siin määratud mingi suht suvaline konstant, reaalsuses võiks olla palju suurem, aga demo jaoks hea kui väike
            if (vimmatiUuendatudMinutites > 5) {
                CustomDebugPrinter.dbgMsgLvl1("..seega uuendatakse ainet API-st");
                if (aineHasChanged(ained.get(kood))) {
                    CustomDebugPrinter.dbgMsgLvl1("Ainet on muudetud, seega tagastame uue versiooni ÕIS-ist");
                    Oppeaine oa = CoursesApi.getAineFromCode(kood);
                    ained.put(kood, oa);
                    uuidKoodiMap.put(UUID.fromString(oa.getProperty("uuid")), kood);
                } else {
                    CustomDebugPrinter.dbgMsgLvl1("Ainet pole vahepeal muudetud, seega tagastame ikkagi otse mälust");
                }
            } else {
                CustomDebugPrinter.dbgMsgLvl1("..seega tagastame aine otse mälust");
            }
        } else { // kui ei ole varem näinud
            Oppeaine oa = CoursesApi.getAineFromCode(kood);
            ained.put(kood, oa);
            uuidKoodiMap.put(UUID.fromString(oa.getProperty("uuid")), kood);
        }
        return ained.get(kood);
    }

    /**
     * Leiab aine UUID (mitte versiooni UUID) järgi.
     * @param aineUuid Aine UUID.
     * @return Otsitav Oppeaine objekt.
     */
    public static Oppeaine getAine(UUID aineUuid) {
        String kood = uuidKoodiMap.get(aineUuid);
        if (kood == null) {
            return null;
        }
        return getAine(kood);
    }

    /**
     * DEPRCATED! Ei kasuta UUID cache funktsionaalsust. Mitte kasutada!
     * Lisab õppeaine objekti puhvrisse. NB! Ained lisatakse automaatselt getAine() meetodi korral.
     * Hetkel pole see meetod kasutuses. Ilmselt pole sellel enam tulevikus kasutust, kuna see vajab
     * lisamiseks Oppeaine objekti, aga (algseid) Oppeaine objekte peaksime me saama ainult AineCache.getAine()
     * meetodilt, mis ise juba tegeleb vajadusel aine lisamisega.
     * Praegu ikkagi jätab igaksjuhuks alles.
     *
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
        return !(CoursesApi.getLatestCourseChange(aine).equals(aine.getProperty("last_update")));
    }
}
