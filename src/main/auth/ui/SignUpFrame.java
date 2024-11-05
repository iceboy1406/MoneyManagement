package auth.ui;

import auth.dto.SignUp;
import auth.repository.AuthRepository;
import auth.service.AuthService;
import config.Database;
import exception.ValidationException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class SignUpFrame extends javax.swing.JFrame {
    private final AuthService authService;

    public SignUpFrame() {
        AuthRepository authRepository = new AuthRepository(Database.getConnection());
        this.authService = new AuthService(authRepository);

        initComponents();
    }

    private void loginLinkMouseClicked(MouseEvent evt) {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        this.dispose();
    }

    private void signUpButtonActionPerformed(ActionEvent evt) {
        String username = usernameField.getText();
        String name = nameField.getText();
        String password = new String(passwordField.getPassword());
        String passwordConfirmation = new String(passwordConfirmationField.getPassword());

        try {
            if (username.isEmpty()) {
                throw new ValidationException("Username is required");
            } else if (name.isEmpty()) {
                throw new ValidationException("Name is required");
            } else if (password.isEmpty()) {
                throw new ValidationException("Password is required");
            } else if (passwordConfirmation.isEmpty()) {
                throw new ValidationException("Password Confirmation is required");
            } else if (!password.equals(passwordConfirmation)) {
                throw new ValidationException("Password and Password Confirmation must same");
            }

            authService.signUp(new SignUp(username, name, password));
            JOptionPane.showMessageDialog(this, "User successfully registered");

            this.dispose();
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        }  catch (ValidationException e) {
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
        signUpButton = new javax.swing.JButton();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        haveAnAccountQuestionContainer = new javax.swing.JPanel();
        haveAnAccountQuestionLabel = new javax.swing.JLabel();
        signUpLink = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        titleLabel = new javax.swing.JLabel();
        passwordConfirmationField = new javax.swing.JPasswordField();
        passwordConfirmationLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();

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

        signUpButton.setText("Sign Up");
        signUpButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signUpButton.setPreferredSize(new java.awt.Dimension(72, 32));
        signUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signUpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        container.add(signUpButton, gridBagConstraints);

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
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        container.add(passwordLabel, gridBagConstraints);

        haveAnAccountQuestionContainer.setLayout(new java.awt.GridBagLayout());

        haveAnAccountQuestionLabel.setText("Already have an account?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
        haveAnAccountQuestionContainer.add(haveAnAccountQuestionLabel, gridBagConstraints);

        signUpLink.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        signUpLink.setForeground(new java.awt.Color(0, 153, 204));
        signUpLink.setText("Login");
        signUpLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signUpLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginLinkMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        haveAnAccountQuestionContainer.add(signUpLink, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        container.add(haveAnAccountQuestionContainer, gridBagConstraints);

        passwordField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        passwordField.setMargin(new java.awt.Insets(2, 10, 2, 6));
        passwordField.setMinimumSize(new java.awt.Dimension(64, 32));
        passwordField.setPreferredSize(new java.awt.Dimension(19, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        container.add(passwordField, gridBagConstraints);

        titleLabel.setFont(new java.awt.Font("Noto Sans", 0, 24)); // NOI18N
        titleLabel.setText("SIGNUP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 24, 0);
        container.add(titleLabel, gridBagConstraints);

        passwordConfirmationField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        passwordConfirmationField.setMargin(new java.awt.Insets(2, 10, 2, 6));
        passwordConfirmationField.setMinimumSize(new java.awt.Dimension(64, 32));
        passwordConfirmationField.setPreferredSize(new java.awt.Dimension(19, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        container.add(passwordConfirmationField, gridBagConstraints);

        passwordConfirmationLabel.setLabelFor(passwordConfirmationField);
        passwordConfirmationLabel.setText("Confirm Password");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        container.add(passwordConfirmationLabel, gridBagConstraints);

        nameLabel.setLabelFor(usernameField);
        nameLabel.setText("Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        container.add(nameLabel, gridBagConstraints);

        nameField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        nameField.setPreferredSize(new java.awt.Dimension(75, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        container.add(nameField, gridBagConstraints);

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
    private javax.swing.JLabel haveAnAccountQuestionLabel;
    private javax.swing.JPanel haveAnAccountQuestionContainer;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPasswordField passwordConfirmationField;
    private javax.swing.JLabel passwordConfirmationLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JButton signUpButton;
    private javax.swing.JLabel signUpLink;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
}
