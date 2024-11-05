package auth.dto;

public class SignUp {
    public String username;
    public String name;
    public String password;

    public SignUp(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }
}
