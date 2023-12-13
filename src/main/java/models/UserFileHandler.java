package models;

public class UserFileHandler extends FileHandler<User>{
    private static final String FILE_PATH = "src/main/java/models/users.json";
    public UserFileHandler() {
        super(FILE_PATH);
    }

    @Override
    protected String convertObjectToJson(User user) {
        return String.format("{\"username\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"phone\":\"%s\",\"country\":\"%s\"}",
                user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone(), user.getCountry());
    }

    @Override
    protected User convertJsonToObject(String json) {
        // remove { and } from json string
        // split string by comma. each element is a field
        // split each field by colon. first element is field name, second element is field value
        // remove quotes from field value
        String[] userFields = json.replace("{", "").replace("}", "").split(",");
        String username = userFields[0].split(":")[1].replace("\"", "");
        String password = userFields[1].split(":")[1].replace("\"", "");
        String email = userFields[2].split(":")[1].replace("\"", "");
        String phone = userFields[3].split(":")[1].replace("\"", "");
        String country = userFields[4].split(":")[1].replace("\"", "");
        return new User(username, password, email, phone, country);
    }
}