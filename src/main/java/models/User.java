package models;

public class User {
    private final String username;
    private String email;
    private final String password;
    private String phone;
    private String country;

    public User(String username, String password, String email, String phone, String country) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.country = country;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.email = "";
        this.phone = "";
        this.country = "";
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
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
