package user;

import database.DBConnector;
import oppeaine.KasutajaOppeaine;
import oppeaine.Oppeaine;
import org.json.JSONObject;

import java.sql.ResultSet;
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

    public static User getUser() { // meetod kasutaja objekti tagastamiseks
        return userObj;
    }

    public static void updateCacheFromDatabase() {

        DBConnector db = DBConnector.instance;

        ResultSet rs = db.getAllFromTable("course");
        try {
            while (rs.next()) {
                users.put(UUID.fromString(rs.getString("uid")), new User()); // TODO see inner json tuleb korda teha
            }
        } catch (Exception e) {e.printStackTrace();}

        System.out.println("Cache'i loeti andmebaasist " + users.size() + " kasutajat.");
    }

    public static void writeCacheToDatabase() {
        DBConnector db = DBConnector.instance;
        db.updateUsers(users);
    }

}
