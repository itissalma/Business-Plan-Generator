package models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class UserFileHandler {
    private static final String FILE_PATH = "src/main/java/models/users.json";

    private static void writeUsersToJson(List<User> userList) {
        // if file does not exist, create it
        try {
            FileWriter file = new FileWriter(FILE_PATH, true);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error creating JSON file.");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            writer.println("[");
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                String jsonEntry = String.format(
                        "  {\"username\":\"%s\",\"email\":\"%s\",\"password\":\"%s\",\"phone\":\"%s\",\"country\":\"%s\"}%s",
                        user.getUsername(), user.getEmail(), user.getPassword(), user.getPhone(), user.getCountry(),
                        i < userList.size() - 1 ? "," : ""
                );
                writer.println(jsonEntry);
            }
            writer.println("]");
            System.out.println("Users written to JSON file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing users to JSON file.");
        }
    }

    private static List<User> readUsersFromJson() {
        // if file does not exist, create it
        try {
            File f = new File(FILE_PATH);
            if (f.createNewFile()) {
                System.out.println("JSON file created.");
            } else {
                System.out.println("JSON file exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error creating JSON file.");
        }

        List<User> userList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString().trim();
            // Remove leading and trailing square brackets to parse individual objects
            if (jsonString.startsWith("[")) jsonString = jsonString.substring(1);
            if (jsonString.endsWith("]")) jsonString = jsonString.substring(0, jsonString.length() - 1);

            if (jsonString.isEmpty()) {
                System.out.println("No users found in JSON file.");
                return userList;
            }
            // Split by "}," to separate individual user entries
            String[] userEntries = jsonString.split("\\},");

            for (String entry : userEntries) {
                // Add back the "}" to the last entry
                if (!entry.endsWith("}")) {
                    entry += "}";
                }

                // Manually parse the JSON entry
                String username = getValueFromJson(entry, "username");
                String email = getValueFromJson(entry, "email");
                String password = getValueFromJson(entry, "password");
                String phone = getValueFromJson(entry, "phone");
                String country = getValueFromJson(entry, "country");

                User user = new User(username, email, password, phone, country);
                userList.add(user);
            }

            System.out.println("Users read from JSON file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading users from JSON file.");
        }

        return userList;
    }

    private static String getValueFromJson(String json, String key) {
        String keyWithQuotes = "\"" + key + "\":";
        int startIndex = json.indexOf(keyWithQuotes) + keyWithQuotes.length();
        int endIndex = json.indexOf(',', startIndex);
        if (endIndex == -1) {
            endIndex = json.indexOf('}', startIndex);
        }

        return json.substring(startIndex, endIndex).replaceAll("\"", "").trim();
    }

    public static List<User> getUsers() {
        return readUsersFromJson();
    }

    public static void addUser(User user) {
        List<User> userList = getUsers();
        userList.add(user);
        writeUsersToJson(userList);
    }

    public static void updateUser(User user) {
        List<User> userList = getUsers();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(user.getUsername())) {
                userList.set(i, user);
                break;
            }
        }
        writeUsersToJson(userList);
    }

    public static void deleteUser(String username) {
        List<User> userList = getUsers();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username)) {
                userList.remove(i);
                break;
            }
        }
        writeUsersToJson(userList);
    }
}
