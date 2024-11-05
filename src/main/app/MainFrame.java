
package app;

import auth.ui.LoginFrame;
import dashboard.ui.DashboardPanel;
import expense.ui.ExpensePanel;
import income.ui.IncomePanel;

import java.util.prefs.Preferences;
import javax.swing.JOptionPane;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        initComponents();
    }

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int ans = JOptionPane.showConfirmDialog(this, "Are you sure want to leave?", "Close Confirmation", JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION)
            this.dispose();
    }

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int ans = JOptionPane.showConfirmDialog(this, "Are you sure want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION) {
            this.dispose();
            LoginFrame loginView = new LoginFrame();
            loginView.setVisible(true);

            Preferences pref = Preferences.userRoot();
            pref.remove("username");
            pref.remove("name");
        }
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mainTabbedPane = new javax.swing.JTabbedPane();
        dashboardPanel = new DashboardPanel();
        expensePanel = new ExpensePanel(this);
        incomePanel = new IncomePanel(this);
        actionPanel = new javax.swing.JPanel();
        logoutButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1120, 630));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        mainTabbedPane.addTab("Dashboard", dashboardPanel);
        mainTabbedPane.addTab("Expense", expensePanel);
        mainTabbedPane.addTab("Income", incomePanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(mainTabbedPane, gridBagConstraints);

        actionPanel.setLayout(new java.awt.GridBagLayout());

        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        actionPanel.add(logoutButton, gridBagConstraints);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 8);
        actionPanel.add(closeButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        getContentPane().add(actionPanel, gridBagConstraints);

        pack();
        setLocationRelativeTo(null);
    }

    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JPanel expensePanel;
    private javax.swing.JPanel incomePanel;
    private javax.swing.JButton logoutButton;
    private javax.swing.JTabbedPane mainTabbedPane;
    // End of variables declaration//GEN-END:variables
}
