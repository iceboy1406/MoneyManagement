package expense.ui;

import app.MainFrame;
import config.Database;
import exception.ValidationException;
import expense.entity.Expense;
import expense.repository.ExpenseRepository;
import expense.service.ExpenseService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExpensePanel extends JPanel {
    MainFrame mainFrame;
    ExpenseService expenseService;
    DefaultTableModel expenseTableModel;

    public ExpensePanel(MainFrame mainFrame) {
        initComponents();
        this.mainFrame = mainFrame;
        this.expenseService = new ExpenseService(new ExpenseRepository(Database.getConnection()));
        this.expenseTableModel = (DefaultTableModel) expenseTable.getModel();

        initData();
    }

    private void initData() {
        try {
            ArrayList<Expense> expenses = expenseService.findAll();
            expenseTableModel.setRowCount(0);
            for (Expense expense : expenses) {
                expenseTableModel.addRow(new Object[]{
                        expense.id, expense.amount, expense.category.name, expense.date, expense.description
                });
            }
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Something went wrong");
        }
    }

    private void deleteSelectedRowButtonActionPerformed(ActionEvent evt) {
        try {
            if (expenseTable.getSelectedRows().length == 0) {
                throw new ValidationException("Please select at least one row");
            } else if (expenseTable.getSelectedRows().length > 1) {
                int ans = JOptionPane.showConfirmDialog(this.mainFrame, "Are you sure want to delete these rows?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION
                );

                if (ans == JOptionPane.YES_OPTION) {
                    int[] selectedRows = expenseTable.getSelectedRows();
                    int[] ids = new int[selectedRows.length];

                    for (int i = 0; i < selectedRows.length; i++) {
                        ids[i] = (int) expenseTableModel.getValueAt(selectedRows[i], 0);
                    }

                    expenseService.deleteMany(ids);

                    JOptionPane.showMessageDialog(this.mainFrame, "Successfully deleted selected rows");
                    this.refreshData();
                }
            } else {
                int ans = JOptionPane.showConfirmDialog(this.mainFrame, "Are you sure want to delete this row?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION
                );

                if (ans == JOptionPane.YES_OPTION) {
                    int selectedID = (int) expenseTableModel.getValueAt(expenseTable.getSelectedRow(), 0);
                    expenseService.delete(selectedID);
                    JOptionPane.showMessageDialog(this.mainFrame, "Successfully deleted selected row");
                    expenseTableModel.removeRow(expenseTable.getSelectedRow());
                }
            }
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Something went wrong");
        }
    }

    private void editSelectedRowButtonActionPerformed(ActionEvent evt) {
        if (expenseTable.getSelectedRows().length == 0) {
            JOptionPane.showMessageDialog(this.mainFrame, "Please select a row first");
        } else if (expenseTable.getSelectedRows().length > 1) {
            JOptionPane.showMessageDialog(this.mainFrame, "You can only edit one table each time");
        } else {
            int id = (int) expenseTableModel.getValueAt(expenseTable.getSelectedRow(), 0);
            EditExpenseDialog editExpenseDialog = new EditExpenseDialog(this.mainFrame, true, this, id);
            editExpenseDialog.setLocationRelativeTo(this.mainFrame);
            editExpenseDialog.setVisible(true);
        }
    }

    private void addNewExpenseButtonActionPerformed(ActionEvent evt) {
        AddNewExpenseDialog addNewExpenseDialog = new AddNewExpenseDialog(this.mainFrame, true, this);
        addNewExpenseDialog.setLocationRelativeTo(this.mainFrame);
        addNewExpenseDialog.setVisible(true);
    }

    public void refreshData() {
        this.initData();
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        tableContainerPanel = new javax.swing.JPanel();
        expenseTableScrollPane = new javax.swing.JScrollPane();
        expenseTable = new javax.swing.JTable();
        actionPanel = new javax.swing.JPanel();
        addNewExpenseButton = new javax.swing.JButton();
        deleteSelectedRowButton = new javax.swing.JButton();
        editSelectedRowButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        tableContainerPanel.setLayout(new java.awt.GridBagLayout());

        expenseTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "ID", "Amount", "Category", "Date", "Description"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        expenseTableScrollPane.setViewportView(expenseTable);
        if (expenseTable.getColumnModel().getColumnCount() > 0) {
            expenseTable.getColumnModel().getColumn(0).setMinWidth(48);
            expenseTable.getColumnModel().getColumn(0).setPreferredWidth(48);
            expenseTable.getColumnModel().getColumn(0).setMaxWidth(48);
            expenseTable.getColumnModel().getColumn(4).setMinWidth(160);
            expenseTable.getColumnModel().getColumn(4).setPreferredWidth(160);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        tableContainerPanel.add(expenseTableScrollPane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(tableContainerPanel, gridBagConstraints);

        actionPanel.setLayout(new java.awt.GridBagLayout());

        addNewExpenseButton.setText("Add New Expense");
        addNewExpenseButton.setMinimumSize(new java.awt.Dimension(105, 32));
        addNewExpenseButton.setPreferredSize(new java.awt.Dimension(148, 32));
        addNewExpenseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewExpenseButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        actionPanel.add(addNewExpenseButton, gridBagConstraints);

        deleteSelectedRowButton.setText("Delete Selected Rows");
        deleteSelectedRowButton.setMinimumSize(new java.awt.Dimension(105, 32));
        deleteSelectedRowButton.setPreferredSize(new java.awt.Dimension(156, 32));
        deleteSelectedRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSelectedRowButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        actionPanel.add(deleteSelectedRowButton, gridBagConstraints);

        editSelectedRowButton.setText("Edit Selected Row");
        editSelectedRowButton.setMinimumSize(new java.awt.Dimension(105, 32));
        editSelectedRowButton.setPreferredSize(new java.awt.Dimension(148, 32));
        editSelectedRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editSelectedRowButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        actionPanel.add(editSelectedRowButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        add(actionPanel, gridBagConstraints);
    }

    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton addNewExpenseButton;
    private javax.swing.JButton deleteSelectedRowButton;
    private javax.swing.JButton editSelectedRowButton;
    private javax.swing.JTable expenseTable;
    private javax.swing.JPanel tableContainerPanel;
    private javax.swing.JScrollPane expenseTableScrollPane;
}
