package app;

import auth.ui.LoginFrame;
import com.formdev.flatlaf.FlatDarkLaf;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it
        // Set Look And Feel to Flatlaf Dark
        FlatDarkLaf.setup();

        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }
}