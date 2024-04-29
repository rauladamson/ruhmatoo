package oppeaine;

import OIS_API.CoursesApi;
import OIS_API.OisUserController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AineCache {
    private static ArrayList<Oppeaine> ained = new ArrayList<>();

    public AineCache() {;
    }
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
/*
    public AineCache(JSONArray ainedJson) {
        for (int i = 0; i < ainedJson.length(); i++) {
            JSONObject aineJSON = ainedJson.optJSONObject(i);
        }
    }*/

    public static Oppeaine getAine(String kood) {
        Oppeaine aine = null;
        int i = 0;

        for (Oppeaine potAine: ained) {
            if (potAine.getCode().equals(kood)) {
                aine = potAine;
                break;
            }
            i++;
        }

        if (aine != null) {
            if (aineHasChanged(aine)) { // TODO: Api päring ning seotud meetod parandada
                Oppeaine uusAine = CoursesApi.getAineFromCode(aine.getCode());
                ained.set(i, uusAine);
            }
            return ained.get(i);
        }

        Oppeaine uusAine = CoursesApi.getAineFromCode(kood);
        ained.add(uusAine);

        return uusAine;
    }

    /**
     * Lisab õppeaine objekti puhvrisse.
     * @param oa Õppeaine objekt
     * @return True, kui ainet polnud varem olemas. False, kui oli.
     */
    public static boolean addAine(Oppeaine oa) {
        //TODO: optimiseerida meetod kasutades kas latestUuid või latestChanged
        //TODO: kasutada HashSeti(?)
        boolean aineExists = false;
        if (ained.contains(oa)) {
            System.out.println("Prooviti lisada aine " + oa.toString() + " mis oli juba olemas.");
            return false;
        } else {
            ained.add(oa);
            System.out.println("Lisati edukalt aine " + oa.toString());
            return true;
        }
    }

    private static boolean aineHasChanged(Oppeaine aine) {
        //return !(CoursesApi.getLatestCourseChange(aine).equals(aine.getLastChanged()));
        // Hetkel stub
        //TODO
        return false;
    }
}
