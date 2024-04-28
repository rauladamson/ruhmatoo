package pdfsave;

import oppeaine.Oppeaine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JsonFileReader {
    public static Set<String> readUuids() {
        Set<String> uuids = new HashSet<>();
        File uuidFile = new File("uuids.json");

        if (uuidFile.exists()) {
            try (FileReader reader = new FileReader(uuidFile)) {

                JSONTokener tokener = new JSONTokener(reader);
                JSONArray jsonArray = new JSONArray(tokener);

                for (int i = 0; i < jsonArray.length(); i++) {
                    // Teeb igast uuid'st Oppeaine objekti, läbi jsonObject.toString.
                    // TODO - pole kindel, kas see oli soovitud tulemus?
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Oppeaine oa = new Oppeaine(jsonObject.toString());

                    uuids.add(jsonArray.getString(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return uuids;
    }


    public static void saveUuids(Set<String> uuids) {
        JSONArray jsonArray = new JSONArray();
        for (String uuid : uuids) {
            jsonArray.put(uuid);
        }
        try (FileWriter file = new FileWriter("uuids.json")) {
            file.write(jsonArray.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addUuid(String newUuid) {
        Set<String> uuids = readUuids();
        boolean isNew = uuids.add(newUuid);

        if (isNew) {
            saveUuids(uuids);
        }
    }
}

