package oppeaine;

import database.DBConnector;
import org.junit.jupiter.api.Test;
import pdfsave.JsonFileReader;

public class AineCacheTest {

    @Test
    public void testwriteCacheToFile()  {
        AineCache.updateCacheFromFile();
        AineCache.writeCacheToFile();
    }

    @Test
    public void testUpdateCacheFromFile()  {
        //JsonFileReader.readOppeained("test6.json");
        AineCache.updateCacheFromFile();
    }

    @Test
    public void testwriteCacheToDatabase()  {
        DBConnector db = new DBConnector(); // andmebaasiga ühenduse loomine
        AineCache.updateCacheFromFile();
        AineCache.writeCacheToDatabase();
    }

    @Test
    public void testupdateCacheFromDatabase() {
        DBConnector db = new DBConnector(); // andmebaasiga ühenduse loomine
        AineCache.updateCacheFromDatabase();
    }
}
