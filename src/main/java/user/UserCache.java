package user;

import oppeaine.KasutajaOppeaine;
import org.json.JSONObject;

import java.util.UUID;

import java.util.HashMap;

public class UserCache {
    private static final HashMap<UUID, User> users = new HashMap<>();
    private static final String cacheFile = "salvestatudKasutaja.json";

    private static User userObj; // ajutine lahendus


    public static void addUser(User user) { // meetod kasutaja objekti lisamiseks
        userObj = user;
        users.put(user.getUid(), user);
    }

    public static void updateCacheFromFile() {
        //ArrayList<User> loetudKasutajad = JsonFileReader.readUsers(cacheFile);
        //ArrayList<User> loetudKasutajad = JsonFileReader.readUsers(cacheFile);

        /*Gson gson = new GsonBuilder().registerTypeAdapter(CalendarEvent.class, new CalendarEventSerializer()).create();
        JsonArray jsonArray = new JsonArray();
        events.stream().map(gson::toJsonTree).forEach(jsonArray::add);
        //return jsonArray;*/

        /*
        for (Oppeaine uusAine: loetudAined) {
            if (!users.contains(uusAine)) {
                users.add(uusAine);
                i++;
            }
        }*/
       // System.out.println("Cache'i loeti failist " + cacheFile + " " + i + " õppeainet.");
    }

    public static void clearCache() {
        users.clear();
    }

    public static void writeCacheToFile() {
        //JsonFileReader.writeOppeained(cacheFile, users);
    }

    public static void printCache() {
        System.out.println("\n===============");
        System.out.println("Cache sisaldab:");

        /*for (UUID userUid: users.keySet()) {
            System.out.println(users.get(userUid).toString());
        }*/
        System.out.println(userObj.toString());
        System.out.println("===============");
    }

    public static User getUser( ) {
        if (userObj == null) {
            userObj = new User();
        }
        return userObj;
    }

    public static User getUser(UUID uid) {
       /*User user = null;

        try {
            user = users.get(uid);

        } catch (Exception e) {
            System.err.println("Ootamatu exception:");
            e.printStackTrace();

            //System.out.println("mille kood on " + kood + ", mida cache'ist ei leitud.");
            user = new User();
            users.put(user.getUid(), user);
        }*/


        // TODO: FOR DEBUGGING ONLY!
        // TODO: Hetkel kirjutab see cache'i kettale iga kord kui sealt ainet query'takse.
        // See oli debuggimisel vajalik. Hetkel on ta ka hea demo sellest, et cache tegelikult ka töötab.
        writeCacheToFile();
        ///////////////////////////////////////////////
        return userObj;
    }

    public static HashMap<String, KasutajaOppeaine> getUserCourses() {return userObj.getUserCourses();}
}
