package auth.entity;

public class User {
    public String username;
    public String name;
    public String password;

    public User(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }
}
