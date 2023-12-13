package models;

import java.util.List;

public class TestFiles {
    public static void main(String[] args) {
        // create dummy users
        User user1 = new User("salma", "salma", "s1@s.s", "123456789", "USA");
        User user2 = new User("shady", "shady", "s2@s.s", "123456789", "EGY");

        // add users to file
        UserFileHandler userFileHandler = new UserFileHandler();
        userFileHandler.addObject(user1);
        userFileHandler.addObject(user2);

        // get users from file
        List<User> userList = userFileHandler.getObjects();
        System.out.println(userList);
    }
}
