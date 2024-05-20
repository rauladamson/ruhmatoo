package database;
import oppeaine.Oppeaine;
import user.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DBConnector {

    private static Connection conn;
    public static DBConnector instance;

    public DBConnector() {
        connect();
        instance = this;
    }

    public static void saveUser(User user) {
        if (!existsInTable("user_table", "uid", user.getUid().toString())) { // kui ainet pole veel andmebaasis, siis see lisatakse
            try {
                Statement stmt = conn.createStatement();
                String sql = "INSERT INTO user_table (uid) VALUES ('" + user.getUid() + "')";
                stmt.execute(sql);
                System.out.println("Kasutaja  lisati andmebaasi");
            } catch (Exception e) {e.printStackTrace();}
        } else {
            System.out.println("Kasutaja on juba andmebaasis");
        }
    }

    public void connect() {
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");

            System.out.println("Loodi ühendus andmebaasiga");

        } catch (Exception e) {e.printStackTrace();}
    }

    public void closeConnection() {
        try {conn.close();}
        catch (Exception e) {e.printStackTrace();}
    }

    public void createTable(String tableName, String columns) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS "+ tableName  + columns;
            stmt.execute(sql);
            System.out.println("Table created");
        } catch (Exception e) {e.printStackTrace();}
    }

    public void dropTable(String tableName) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DROP TABLE "+ tableName;
            stmt.execute(sql);
            System.out.println("Table deleted");
        } catch (Exception e) {e.printStackTrace();}
    }

    public void dropAllTables() {
        try {
            // Drop tables in the correct order considering foreign key constraints
            dropTable("course");
            dropTable("occurrences");
            dropTable("calendar_event");
            dropTable("calendar");
            dropTable("user_table");
        } catch (Exception e) {e.printStackTrace();}
    }

    public static boolean existsInTable(String tableName, String column, String value) {
        try { // kui leitakse vähemalt üks vaste, siis tagastatakse true
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM " + tableName + " WHERE " + column + " = '" + value + "'";
            ResultSet rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (Exception e) {e.printStackTrace();}
        return false;
    }

    public void updateOppeained(HashMap<String, Oppeaine> ained) {
        for (String aineUuid: ained.keySet()) {
            if (!existsInTable("course", "uid", aineUuid)) { // kui ainet pole veel andmebaasis, siis see lisatakse
                try {
                    Oppeaine aine = ained.get(aineUuid);
                    Statement stmt = conn.createStatement();
                    String sql = "INSERT INTO course (uid, parent_uid) VALUES ('" +  UUID.randomUUID() + "', '" + aine.getProperty("uid")  + ")";
                    stmt.execute(sql);
                    System.out.println("Kursus " + aine.getName() + " lisati andmebaasi");
                } catch (Exception e) {e.printStackTrace();}
            } //TODO: kui ainet on muudetud, siis see uuendatakse

        }
    }

    public ResultSet getAllFromTable(String tableName) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM " + tableName;
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (Exception e) {e.printStackTrace();}
        return null;
    }

    public static void main(String[] args) {
        DBConnector db = new DBConnector();
        //db.dropTable("course");
        // tabelite loomine
        /*db.createTable("user_table", "(uid VARCHAR(255) PRIMARY KEY)");
        db.createTable("calendar", "(uid VARCHAR(255) PRIMARY KEY, user_uid VARCHAR(255), FOREIGN KEY (user_uid) REFERENCES user_table(uid) ON DELETE SET NULL)");
        db.createTable("calendar_event", "(uid VARCHAR(255) PRIMARY KEY, summary VARCHAR(255), location VARCHAR(255), description VARCHAR(255), categories VARCHAR(255))");
        db.createTable("occurrences", "(uid VARCHAR(255) PRIMARY KEY, event_uid VARCHAR(255), FOREIGN KEY (event_uid) REFERENCES calendar_event(uid) ON DELETE SET NULL, datetime DATE)");
         db.createTable("course", "(uid VARCHAR(255) PRIMARY KEY, calendar_event VARCHAR(255), FOREIGN KEY (calendar_event) REFERENCES calendar_event(uid) ON DELETE SET NULL, course_code VARCHAR(255), course_name VARCHAR(255), course_ect INT)");
        */

    }

    public void updateUsers(HashMap<UUID, User> users) {
        for (UUID userUUid: users.keySet()) {
            User user = users.get(userUUid);
            if (!existsInTable("user_table", "uid", user.getUid().toString())) { // kui ainet pole veel andmebaasis, siis see lisatakse
                saveUser(user);
            } else {
                //TODO: kui kasutaja andmeid on muudetud, siis neid uuendatakse ka andmebaasis
                System.out.println("Kasutaja andmeid uuendati");
            }
        }
    }
}
