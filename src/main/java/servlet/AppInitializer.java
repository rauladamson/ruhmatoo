package servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import database.DBConnector;
import user.User;
import user.UserCache;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        User userObj = new User(); // luuakse uus kasutaja objekt
        UserCache.addUser(userObj); // objekt lisatakse vahemällu
        sce.getServletContext().setAttribute("user", userObj);
        System.out.println("Loodi uus kasutaja objekt");

        new DBConnector(); // andmebaasiga ühenduse loomine
        System.out.println("Loodi ühendus andmebaasiga");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBConnector.saveUser(UserCache.getUser()); // kasutaja salvestamine
        DBConnector.instance.closeConnection();
        System.out.println("Andmebaasiühendus katkestatud");
    }
}