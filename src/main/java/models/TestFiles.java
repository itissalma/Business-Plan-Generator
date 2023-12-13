package models;

import java.util.List;

public class TestFiles {
    public static void createDefaultUsersFile(String[] args) {
        // create dummy users
        User user1 = new User("salma", "salma", "s1@s.s", "123456789", "USA");
        User user2 = new User("shady", "shady", "s2@s.s", "123456789", "EGY");

        // add users to file
        UserFileHandler.addUser(user1);
        UserFileHandler.addUser(user2);

        // get users from file
        List<User> userList = UserFileHandler.getUsers();
        System.out.println(userList);
    }
}
