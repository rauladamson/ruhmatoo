package pdfsave;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import ical.iCalObj;
import oppeaine.Oppeaine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonFileReader {
    public static Set<String> readUuids() {
        Set<String> uuids = new HashSet<>();
        File uuidFile = new File("uuids.json");

        if (uuidFile.exists()) {
            try (FileReader reader = new FileReader(uuidFile)) {

                JSONTokener tokener = new JSONTokener(reader);
                JSONArray jsonArray = new JSONArray(tokener);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    // Õppeainete cahce'imise loogika tegin ma praegu eraldi funktsiooni -
                    // cahce'i mõte oleks salvestada ainete kohta info, et seda ei peaks uuesti pärima
                    // ning seega ei ole UUID eraldi kirjutamine vajalik, kuna töötamise ajal hoiab
                    // cache neid sisemälus, ehk faili salvestamine on vajalik ainult serveri sulgemisel.

                    uuids.add(jsonArray.getString(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return uuids;
    }

    //Kuna tegemist on JSON-iga seotud klassiga, siis lisab üldise meetodi JSON failide kirjutamiseks
    // Kas oleks võimalik neid funktsioone kuidagi templatiseerida?
    //TODO: Lisada ka generic JSON-i lugeja funktsioon
    protected static void writeJsonToFile(String fail, JSONArray json) {
        try (FileWriter file = new FileWriter(fail)) {
            file.write(json.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void writeJsonToFile(String fail, JsonArray json) {
        try (FileWriter file = new FileWriter(fail)) {
            file.write(json.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void writeJsonToFile(String fail, JSONObject json) {
        try (FileWriter file = new FileWriter(fail)) {
            file.write(json.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void saveUuids(Set<String> uuids) {
        JSONArray jsonArray = new JSONArray();
        for (String uuid : uuids) {
            jsonArray.put(uuid);
        }
        writeJsonToFile("uuids.json", jsonArray);
    }

    public static void addUuid(String newUuid) {
        Set<String> uuids = readUuids();
        boolean isNew = uuids.add(newUuid);

        if (isNew) {
            saveUuids(uuids);
        }
    }

    //TODO: Muuta Set-iks?
    public static ArrayList<Oppeaine> readOppeained(String fail) {
        //ArrayList<Oppeaine> uuedAined = new ArrayList<>();
        File aineteFail = new File(fail);
        Gson gson = new Gson();
        //return gson.fromJson(icals, iCalObj.class);


        if (!aineteFail.exists()) {
            System.err.println("Ei leidnud faili " + fail + ".");
            return null;
            //throw new RuntimeException("Ei leidnud faili " + fail + ".");
        }

        try (FileReader reader = new FileReader(aineteFail)) {
            ArrayList<Oppeaine> uuedAined = new ArrayList<>();

            JSONTokener tokener = new JSONTokener(reader);
            JSONArray jsonArray = new JSONArray(tokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Oppeaine oa = new Oppeaine(jsonObject);

                uuedAined.add(oa);
            }
            return uuedAined;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void writeOppeained(String fail, ArrayList<Oppeaine> ained) {
        JSONArray kirjutatavadOppeained = new JSONArray();
        for (Oppeaine aine: ained) {
            kirjutatavadOppeained.put(aine.convertToJson());
        }
        writeJsonToFile(fail, kirjutatavadOppeained);
    }

    public static void writeOppeained(String fail, JsonArray ained) {
        writeJsonToFile(fail, ained);
    }
}

