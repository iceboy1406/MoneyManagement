package auth.repository;

import auth.dto.SignUp;
import auth.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthRepository {
    private Connection conn;

    public AuthRepository(Connection conn) {
        this.conn = conn;
    }

    public User signUp(SignUp signUp) throws SQLException {
        String sql = "INSERT INTO users (username, name, password) VALUES (?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, signUp.username);
            statement.setString(2, signUp.name);
            statement.setString(3, signUp.password);

            statement.executeUpdate();
            return new User(signUp.username, signUp.name, signUp.password);
        }

    }

    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            try (var result = statement.executeQuery()) {
                if (result.next()) {
                    return new User(result.getString("username"), result.getString("name"), result.getString("password"));
                }
            }

            return null;
        }
    }
}
