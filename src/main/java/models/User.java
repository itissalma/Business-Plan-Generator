//Shady Nessim 900191322 Salma Aly 900203182
package models;

public class User implements identifiable {
    private final String username;
    private String email;
    private final String password;
    private String phone;
    private String country;
    private int id;
    private static int lastID = 0;

    public User(String username, String password, String email, String phone, String country) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.country = country;
    }

    public User(int id, String username, String password, String email, String phone, String country) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public String getCountry() { return country; }

    public void setEmail(String email) { this.email = email; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getLastId() {
        return lastID;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void incrementLastId() {
        lastID++;
    }
}
