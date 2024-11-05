package income.ui;

import config.Database;
import exception.ValidationException;
import income.entity.Income;
import income.entity.IncomeSource;
import income.repository.IncomeRepository;
import income.repository.IncomeSourceRepository;
import income.service.IncomeService;
import income.service.IncomeSourceService;
import raven.datetime.component.date.DatePicker;
import util.DateConverter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class AddNewIncomeDialog extends JDialog {
    private IncomeService incomeService;
    private IncomeSourceService incomeSourceService;
    private IncomePanel parentPanel;

    public AddNewIncomeDialog(java.awt.Frame parent, boolean modal, IncomePanel parentPanel) {
        super(parent, modal);
        this.parentPanel = parentPanel;
        initComponents();

        incomeSourceService = new IncomeSourceService(new IncomeSourceRepository(Database.getConnection()));
        incomeService = new IncomeService(new IncomeRepository(Database.getConnection()));
        initDatePicker();
        initIncomeSource();
    }

    private void initIncomeSource() {
        try {
            ArrayList<IncomeSource> incomeSources = incomeSourceService.findAll();
            DefaultComboBoxModel<IncomeSource> comboBoxModel = new DefaultComboBoxModel<>();
            for (IncomeSource incomeSource : incomeSources) {
                comboBoxModel.addElement(incomeSource);
            }
            categoryComboBox.setModel(comboBoxModel);
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Something went wrong");
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        try {
            if (amountField.getText().isEmpty()) {
                throw new ValidationException("Amount field is required");
            } else if (dateField.getText().equals("--/--/----")) {
                throw new ValidationException("Date field is required");
            } else if (descriptionField.getText().isEmpty()) {
                throw new ValidationException("Description field is required");
            } else {
                double amount = Double.parseDouble(amountField.getText());
                IncomeSource incomeSource = (IncomeSource) categoryComboBox.getSelectedItem();
                Date date = DateConverter.toSqlDate(dateField.getText());
                String description = descriptionField.getText();
                Preferences pref = Preferences.userRoot();

                incomeService.add(new Income(amount, description, date, pref.get("username", ""), incomeSource));
                JOptionPane.showMessageDialog(this, "Successfully added new income");
                this.parentPanel.refreshData();
                this.dispose();
            }
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this.parentPanel, e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this.parentPanel, "Amount must be a number");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.parentPanel, "Something went wrong");
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this.parentPanel, "Date format is invalid");
        }
    }

    private void initDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setEditor(dateField);
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new JPanel();
        saveButton = new JButton();
        amountLabel = new JLabel();
        amountField = new JTextField();
        descriptionField = new JTextField();
        descriptionLabel = new JLabel();
        dateLabel = new JLabel();
        dateField = new JFormattedTextField();
        categoryLabel = new JLabel();
        categoryComboBox = new JComboBox<IncomeSource>();
        jLabel1 = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(512, 512));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        saveButton.setText("Save");
        saveButton.setPreferredSize(new java.awt.Dimension(72, 32));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(saveButton, gridBagConstraints);

        amountLabel.setText("Amount");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(amountLabel, gridBagConstraints);

        amountField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        amountField.setPreferredSize(new java.awt.Dimension(75, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        jPanel1.add(amountField, gridBagConstraints);

        descriptionField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        descriptionField.setPreferredSize(new java.awt.Dimension(75, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        jPanel1.add(descriptionField, gridBagConstraints);

        descriptionLabel.setText("Description");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(descriptionLabel, gridBagConstraints);

        dateLabel.setText("Date");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(dateLabel, gridBagConstraints);

        dateField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        dateField.setPreferredSize(new java.awt.Dimension(133, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        jPanel1.add(dateField, gridBagConstraints);

        categoryLabel.setText("Category");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(categoryLabel, gridBagConstraints);

        categoryComboBox.setModel(new DefaultComboBoxModel<IncomeSource>());
        categoryComboBox.setPreferredSize(new java.awt.Dimension(72, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        jPanel1.add(categoryComboBox, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Noto Sans", 0, 24)); // NOI18N
        jLabel1.setText("Add New Income");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 32, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 64, 0, 64);
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }

    private JTextField amountField;
    private JLabel amountLabel;
    private JComboBox<IncomeSource> categoryComboBox;
    private JLabel categoryLabel;
    private JFormattedTextField dateField;
    private JLabel dateLabel;
    private JTextField descriptionField;
    private JLabel descriptionLabel;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JButton saveButton;
}
