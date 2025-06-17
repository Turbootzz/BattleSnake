package nl.hu.bep.setup;

import nl.hu.bep.battlesnek.model.MyUser;
import nl.hu.bep.battlesnek.persistence.FilePersistenceManager;
import nl.hu.bep.battlesnek.utils.PasswordUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashSet;
import java.util.Set;

@WebListener
public class MyServletContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("initializing application");

        String testPassword = System.getenv().getOrDefault("TEST_USER_PASSWORD", "test");
        String adminPassword = System.getenv().getOrDefault("ADMIN_USER_PASSWORD", "admin");

        // hashing password
        String testHashed = PasswordUtils.hashPassword(testPassword, PasswordUtils.generateSalt());
        String adminHashed = PasswordUtils.hashPassword(adminPassword, PasswordUtils.generateSalt());

        MyUser testUser = new MyUser("test", testHashed, "user");
        MyUser adminUser = new MyUser("admin", adminHashed);

        if (MyUser.addUser(testUser) && MyUser.addUser(adminUser)) {
            sce.getServletContext().log("User 'test' and 'admin' successfully added with role their respective role");
        } else {
            sce.getServletContext().log("User 'test' and 'admin' already exists");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("terminating application");
    }
}
