package dashboard.ui;

import javax.swing.*;
import java.util.prefs.Preferences;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        initComponents();
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        welcomeLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        Preferences pref = Preferences.userRoot();
        welcomeLabel.setText("Welcome " + pref.get("name", ""));
        welcomeLabel.setToolTipText("");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        add(welcomeLabel, gridBagConstraints);
    }

    private javax.swing.JLabel welcomeLabel;
}
