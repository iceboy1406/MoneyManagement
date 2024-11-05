package dashboard.ui;

import com.formdev.flatlaf.ui.FlatLineBorder;
import config.Database;
import dashboard.repository.StatisticRepository;
import dashboard.service.StatisticService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class DashboardPanel extends JPanel {
    StatisticService statisticService;

    public DashboardPanel() {
        this.statisticService = new StatisticService(new StatisticRepository(Database.getConnection()));
        initComponents();
        initTotalExpense();
        initTotalIncome();
        initTotalBalance();
    }

    public void initTotalExpense() {
        try {
            expenseValueLabel.setText("Rp. " + statisticService.getTotalExpense());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initTotalIncome() {
        try {
            incomeValueLabel.setText("Rp. " + statisticService.getTotalIncome());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initTotalBalance() {
        try {
            balanceValueLabel.setText("Rp. " + statisticService.getBalance());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        welcomeLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        Preferences pref = Preferences.userRoot();
        welcomeLabel.setText("Welcome " + pref.get("name", ""));
        welcomeLabel.setToolTipText("");
        welcomeLabel.setFont(new Font("Noto Sans", Font.PLAIN, 24));

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(16, 16, 0, 16);
        add(welcomeLabel, gridBagConstraints);

        JPanel statsListPanel = new javax.swing.JPanel();
        statsListPanel.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(16, 16, 16, 16);
        add(statsListPanel, gridBagConstraints);


        JPanel expenseCard = new JPanel();
        expenseCard.setLayout(new GridBagLayout());
        expenseCard.setBackground(Color.decode("#55585a"));
        expenseCard.setBorder( new FlatLineBorder( new Insets(0,0,0,0), Color.decode("#616365"), 2, 12 ) );

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        statsListPanel.add(expenseCard, gridBagConstraints);
        JLabel expenseLabel = new JLabel("Total Expense");

        expenseValueLabel = new JLabel("Rp 0");
        expenseValueLabel.setFont(new Font("Noto Sans", Font.BOLD, 24));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(16, 16, 4, 16);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        expenseCard.add(expenseValueLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(4, 16, 16, 144);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        expenseCard.add(expenseLabel, gridBagConstraints);

        JPanel incomeCard = new JPanel();
        incomeCard.setLayout(new GridBagLayout());
        incomeCard.setBackground(Color.decode("#55585a"));
        incomeCard.setBorder( new FlatLineBorder( new Insets(0,0,0,0), Color.decode("#616365"), 2, 12 ) );

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 16, 0, 0);

        statsListPanel.add(incomeCard, gridBagConstraints);

        incomeValueLabel = new JLabel("Rp 0");
        incomeValueLabel.setFont(new Font("Noto Sans", Font.BOLD, 24));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(16, 16, 4, 16);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        incomeCard.add(incomeValueLabel, gridBagConstraints);
        JLabel incomeLabel = new JLabel("Total Income");

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(4, 16, 16, 144);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        incomeCard.add(incomeLabel, gridBagConstraints);

        JPanel balanceCard = new JPanel();
        balanceCard.setLayout(new GridBagLayout());
        balanceCard.setBackground(Color.decode("#55585a"));
        balanceCard.setBorder( new FlatLineBorder( new Insets(0,0,0,0), Color.decode("#616365"), 2, 12 ) );

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 16, 0, 0);

        statsListPanel.add(balanceCard, gridBagConstraints);

        balanceValueLabel = new JLabel("Rp 0");
        balanceValueLabel.setFont(new Font("Noto Sans", Font.BOLD, 24));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(16, 16, 4, 16);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        balanceCard.add(balanceValueLabel, gridBagConstraints);
        JLabel balanceLabel = new JLabel("Total Balance");

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(4, 16, 16, 144);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        balanceCard.add(balanceLabel, gridBagConstraints);

        JPanel dummyFillPanel = new javax.swing.JPanel();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(dummyFillPanel, gridBagConstraints);

    }

    private JLabel welcomeLabel;
    private JLabel expenseValueLabel;
    private JLabel incomeValueLabel;
    private JLabel balanceValueLabel;
}
