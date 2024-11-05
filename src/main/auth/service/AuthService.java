package auth.service;

import auth.dto.Login;
import auth.dto.SignUp;
import auth.entity.User;
import auth.repository.AuthRepository;
import config.Database;
import exception.ValidationException;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthService {
    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public User login(Login login) throws SQLException, ValidationException {
        try {
            Database.beginTransaction();
            User user = this.authRepository.findByUsername(login.username);

            if (user == null) {
                throw new ValidationException("User not found");
            }

            if (!BCrypt.checkpw(login.password, user.password)) {
                throw new ValidationException("Password is incorrect");
            }

            Database.commitTransaction();

            return user;
        } catch (SQLException error) {
            Database.rollbackTransaction();
            throw error;
        }
    }

    public User signUp(SignUp signUp) throws SQLException, ValidationException {
        try {
            Database.beginTransaction();
            User user = this.authRepository.findByUsername(signUp.username);

            if (user != null) {
                throw new ValidationException("Username already exists");
            }

            signUp.password = BCrypt.hashpw(signUp.password, BCrypt.gensalt());

            user = this.authRepository.signUp(signUp);
            Database.commitTransaction();

            return user;
        } catch (SQLException error) {
            Database.rollbackTransaction();
            throw error;
        }
    }
}
