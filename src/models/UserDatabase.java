package models;

import java.util.HashMap;

public class UserDatabase {
    public static HashMap<String, User> loadUsers() {
        HashMap<String, User> users = new HashMap<>();
        users.put("svar", new User("svar", "123", "buyer"));
        users.put("murad", new User("murad", "123", "seller"));
        users.put("lemuel", new User("lemuel", "123", "admin"));
        return users;
    }
}