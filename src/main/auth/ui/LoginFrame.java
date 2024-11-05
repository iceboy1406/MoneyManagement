package auth.ui;

import app.MainFrame;
import auth.dto.Login;
import auth.entity.User;
import auth.repository.AuthRepository;
import auth.service.AuthService;
import config.Database;
import exception.ValidationException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class LoginFrame extends javax.swing.JFrame {
    private final AuthService authService;

    public LoginFrame() {
        AuthRepository authRepository = new AuthRepository(Database.getConnection());
        this.authService = new AuthService(authRepository);

        initComponents();
    }

    private void signUpLinkMouseClicked(MouseEvent evt) {
        SignUpFrame signUpFrame = new SignUpFrame();
        signUpFrame.setVisible(true);
        this.dispose();
    }

    private void loginButtonActionPerformed(ActionEvent evt) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            if (username.isEmpty()) {
                throw new ValidationException("Username is required");
            } else if (password.isEmpty()) {
                throw new ValidationException("Password is required");
            }

            User user = authService.login(new Login(username, password));
            JOptionPane.showMessageDialog(this, "Login success");

            Preferences pref = Preferences.userRoot();
            pref.put("username", username);
            pref.put("name", user.name);

            MainFrame mainView = new MainFrame();
            this.dispose();
            mainView.setVisible(true);
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Something went wrong");
        }
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        container = new javax.swing.JPanel();
        usernameField = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        haveQuestionContainer = new javax.swing.JPanel();
        haveSignUpQuestionLabel = new javax.swing.JLabel();
        signUpLink = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        titleLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(512, 512));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        container.setLayout(new java.awt.GridBagLayout());

        usernameField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        usernameField.setPreferredSize(new java.awt.Dimension(75, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        container.add(usernameField, gridBagConstraints);

        loginButton.setText("Login");
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.setPreferredSize(new java.awt.Dimension(72, 32));
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        container.add(loginButton, gridBagConstraints);

        usernameLabel.setLabelFor(usernameField);
        usernameLabel.setText("Username");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        container.add(usernameLabel, gridBagConstraints);

        passwordLabel.setLabelFor(passwordField);
        passwordLabel.setText("Password");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        container.add(passwordLabel, gridBagConstraints);

        haveQuestionContainer.setLayout(new java.awt.GridBagLayout());

        haveSignUpQuestionLabel.setText("Doesn't have account yet?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
        haveQuestionContainer.add(haveSignUpQuestionLabel, gridBagConstraints);

        signUpLink.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        signUpLink.setForeground(new java.awt.Color(0, 153, 204));
        signUpLink.setText("Sign Up");
        signUpLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signUpLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signUpLinkMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        haveQuestionContainer.add(signUpLink, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        container.add(haveQuestionContainer, gridBagConstraints);

        passwordField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        passwordField.setMargin(new java.awt.Insets(2, 10, 2, 6));
        passwordField.setMinimumSize(new java.awt.Dimension(64, 32));
        passwordField.setPreferredSize(new java.awt.Dimension(19, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        container.add(passwordField, gridBagConstraints);

        titleLabel.setFont(new java.awt.Font("Noto Sans", 0, 24)); // NOI18N
        titleLabel.setText("LOGIN");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 24, 0);
        container.add(titleLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 80, 0, 80);
        getContentPane().add(container, gridBagConstraints);

        pack();
        setLocationRelativeTo(null);
    }


    private javax.swing.JPanel container;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel haveSignUpQuestionLabel;
    private javax.swing.JPanel haveQuestionContainer;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel signUpLink;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
}
