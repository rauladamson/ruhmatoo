package oppeaine;

import OIS_API.CoursesApi;
import pdfsave.JsonFileReader;

import java.util.ArrayList;
import java.util.HashMap;

public class AineCache {
    private static final HashMap<String, Oppeaine> ained = new HashMap<>();
    private static final ArrayList<Oppeaine> ainedArrayList = new ArrayList<>();
    private static final String cacheFile = "salvestatudOppeained.json";

    /*
    public AineCache(String aineteJsonFail) {
        try {
            File fail = new File(aineteJsonFail);
            if (!fail.exists()) {
                if (!fail.createNewFile()) {
                    throw new IOException("Ei saanud faili luua.");
                }
            }

        } catch (Exception e) {
            System.err.println("Ootamatu exception:");
            e.printStackTrace();
        }
    }
    public AineCache(JSONArray ainedJson) {
        for (int i = 0; i < ainedJson.length(); i++) {
            JSONObject aineJSON = ainedJson.optJSONObject(i);
        }
    }*/

    public static void updateCacheFromFile() {
        ArrayList<Oppeaine> loetudAined = JsonFileReader.readOppeained(cacheFile);
        System.out.println("Loeti failist " + cacheFile + " " + loetudAined.size() + " õppeainet.");
        int i = 0;
        for (Oppeaine uusAine: loetudAined) {
            if (!ained.containsKey(uusAine.getCode())) {
                ained.put(uusAine.getCode(), uusAine);
                ainedArrayList.add(uusAine);
                i++;
            }
        }
        System.out.println("Cache'i loeti failist " + cacheFile + " " + i + " õppeainet.");
    }

    public static void clearCache() {
        ained.clear();
    }

    public static void writeCacheToFile() {
        JsonFileReader.writeOppeained(cacheFile, ainedArrayList);
    }

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
        Oppeaine aine = null;
        try {
            aine = ained.get(kood);
            //System.out.println("Aine leiti vahemälust: " + aine);
          /*  if (aineHasChanged(aine)) { // TODO: Api päring ning seotud meetod parandada. aineHasChanged ja getAineFromCode loogikat võiks ka kuidagi paremini kokku panna.
                Oppeaine uusAine = CoursesApi.getAineFromCode(aine.getCode());

                ained.put(uusAine.getCode(), uusAine);*/
       } catch (Exception e) {
           System.err.println("Aine pole vahemälus, otsime ainet:");
           aine = CoursesApi.getAineFromCode(kood);
            ained.put(aine.getCode(), aine);
       }
        /*Oppeaine aine = null;
        int i = 0;

        for (Oppeaine potAine: ained) {
            System.out.println(potAine.getCode());
            if (potAine.getCode().equals(kood)) {
                aine = potAine;
                break;
            }
            i++;
        }

        //System.out.print("Cache'ist otsiti ainet, ");
        if (aine != null) {
            //System.out.print(aine + " mis leiti cache'ist ");
            if (aineHasChanged(aine)) { // TODO: Api päring ning seotud meetod parandada. aineHasChanged ja getAineFromCode loogikat võiks ka kuidagi paremini kokku panna.
                Oppeaine uusAine = CoursesApi.getAineFromCode(aine.getCode());

                ained.set(i, uusAine);
                //System.out.println("ja mis oli muutunud.");
            } else {
                //; Debuggimiseks mõeldud sout-id. Uncommenctida kui tahta näha, kuidas cache töötab.
                //System.out.println(" mis ei olnud muutunud.");
            }
            return ained.get(i);
        }

        //System.out.println("mille kood on " + kood + ", mida cache'ist ei leitud.");
        Oppeaine uusAine = CoursesApi.getAineFromCode(kood);
        ained.add(uusAine);

        // TODO: FOR DEBUGGING ONLY!
        // TODO: Hetkel kirjutab see cache'i kettale iga kord kui sealt ainet query'takse.
        // See oli debuggimisel vajalik. Hetkel on ta ka hea demo sellest, et cache tegelikult ka töötab.
        writeCacheToFile();
        ///////////////////////////////////////////////

        return uusAine;*/
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
