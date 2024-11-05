package expense.ui;

import config.Database;
import exception.ValidationException;
import expense.entity.ExpenseCategory;
import expense.repository.ExpenseCategoryRepository;
import expense.repository.ExpenseRepository;
import expense.service.ExpenseCategoryService;
import expense.service.ExpenseService;
import raven.datetime.component.date.DatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddNewExpenseDialog extends JDialog {
    private ExpenseService expenseService;
    private ExpenseCategoryService expenseCategoryService;
    private ExpensePanel parentPanel;

    public AddNewExpenseDialog(java.awt.Frame parent, boolean modal, ExpensePanel parentPanel) {
        super(parent, modal);
        this.parentPanel = parentPanel;
        initComponents();

        expenseCategoryService = new ExpenseCategoryService(new ExpenseCategoryRepository(Database.getConnection()));
        expenseService = new ExpenseService(new ExpenseRepository(Database.getConnection()));
        initDatePicker();
        initCategoryList();
    }

    private void initCategoryList() {
        try {
            ArrayList<ExpenseCategory> expenseCategories = expenseCategoryService.findAll();
            DefaultComboBoxModel<ExpenseCategory> comboBoxModel = new DefaultComboBoxModel<ExpenseCategory>();
            for (ExpenseCategory expenseCategory : expenseCategories) {
                comboBoxModel.addElement(expenseCategory);
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
    }

    private void initDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setEditor(dateField);
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        amountLabel = new javax.swing.JLabel();
        amountField = new javax.swing.JTextField();
        descriptionField = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        dateField = new javax.swing.JFormattedTextField();
        categoryLabel = new javax.swing.JLabel();
        categoryComboBox = new JComboBox<ExpenseCategory>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(512, 512));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        saveButton.setText("Save");
        saveButton.setPreferredSize(new java.awt.Dimension(72, 32));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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

        categoryComboBox.setModel(new DefaultComboBoxModel<ExpenseCategory>());
        categoryComboBox.setPreferredSize(new java.awt.Dimension(72, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        jPanel1.add(categoryComboBox, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Noto Sans", 0, 24)); // NOI18N
        jLabel1.setText("Add New Expense");
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

    private javax.swing.JTextField amountField;
    private javax.swing.JLabel amountLabel;
    private JComboBox<ExpenseCategory> categoryComboBox;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JFormattedTextField dateField;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JTextField descriptionField;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton saveButton;
}
