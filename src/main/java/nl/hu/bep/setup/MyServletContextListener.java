package nl.hu.bep.setup;

import nl.hu.bep.battlesnek.model.MyUser;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class    MyServletContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("initializing application");
        MyUser testUser = new MyUser("test", "test", "user");
        if (MyUser.addUser(testUser)) {
            sce.getServletContext().log("User 'test' successfully added with role 'user'");
        } else {
            sce.getServletContext().log("User 'test' already existed");
        }

        // Check if user is available
        MyUser loaded = MyUser.getUserByName("test");
        if (loaded == null) {
            sce.getServletContext().log("Could not load User 'test'");
        } else {
            sce.getServletContext().log("✅ User 'test' exists and has the role: " + loaded.getRole());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("terminating application");
    }
}
