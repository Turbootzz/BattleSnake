package nl.hu.bep.setup;

import nl.hu.bep.battlesnek.model.MyUser;
import nl.hu.bep.battlesnek.persistence.FilePersistenceManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashSet;
import java.util.Set;

@WebListener
public class    MyServletContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("initializing application");

        String testPassword = System.getenv().getOrDefault("TEST_USER_PASSWORD", "test");
        String adminPassword = System.getenv().getOrDefault("ADMIN_USER_PASSWORD", "admin");

        MyUser testUser = new MyUser("test", testPassword, "user");
        MyUser adminUser = new MyUser("admin", adminPassword);

        if (MyUser.addUser(testUser) && MyUser.addUser(adminUser)) {
            sce.getServletContext().log("User 'test' and 'admin' successfully added with role their respective role");
        } else {
            sce.getServletContext().log("User 'test' and 'admin' already exists");
        }

        // Check if user is available
        MyUser loaded = MyUser.getUserByName("test");
        if (loaded == null) {
            sce.getServletContext().log("Could not load User 'test'");
        } else {
            sce.getServletContext().log("User 'test' exists and has the role: " + loaded.getRole());
        }

        MyUser loadedAdmin = MyUser.getUserByName("admin");
        if (loadedAdmin == null) {
            sce.getServletContext().log("Could not load User 'admin'");
        } else {
            sce.getServletContext().log("User 'admin' exists and has the role: " + loaded.getRole());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("terminating application");
    }
}
