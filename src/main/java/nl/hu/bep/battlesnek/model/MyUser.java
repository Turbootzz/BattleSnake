package nl.hu.bep.battlesnek.model;

import nl.hu.bep.battlesnek.utils.PasswordUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyUser implements Principal {

    private final String name;
    private final String role;
    private final String password;
    private static final List<MyUser> users = new ArrayList<>();

    public MyUser(String name, String password) {
        this.name = name;
        this.password = password;
        this.role = "admin";
    }

    public MyUser(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public static String validateLogin(String username, String password) {
        for (MyUser user : users) {
            if (user.getName().equals(username) && PasswordUtils.validatePassword(password, user.password)) {
                return user.getRole();  // Gets role after logging in successfully
            }
        }
        return null;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyUser myUser = (MyUser) o;
        return Objects.equals(name, myUser.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public static boolean addUser(MyUser user) {
        if (users.contains(user)) {
            System.out.println("User " + user.getName() + " already exists");
            return false;
        }
        users.add(user);
        System.out.println("User " + user.getName() + " added");
        return true;
    }

    public static MyUser getUserByName(String user) {
        return users.stream().filter(u -> u.getName().equals(user)).findFirst().orElse(null);
    }

    @Override
    public String getName() {
        return name;
    }
}