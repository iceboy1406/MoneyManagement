package auth.dto;

public class Login {
    public String username;
    public String password;

    public Login(String username, String password) {
        this.password = password;
        this.username = username;
    }
}
