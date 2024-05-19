package servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import user.User;
import user.UserCache;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        User user = new User(); // luuakse uus kasutaja objekt
        UserCache.addUser(user); // objekt lisatakse vahemällu
        sce.getServletContext().setAttribute("user", user);
        System.out.println("Loodi uus kasutaja objekt");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    // TODO @mattiasmoor
    }
}