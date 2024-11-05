package income.ui;

import app.MainFrame;
import config.Database;
import exception.ValidationException;
import income.entity.Income;
import income.repository.IncomeRepository;
import income.service.IncomeService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class IncomePanel extends JPanel {
    MainFrame mainFrame;
    IncomeService incomeService;
    DefaultTableModel incomeTableModel;

    public IncomePanel(MainFrame mainFrame) {
        initComponents();
        this.mainFrame = mainFrame;
        this.incomeService = new IncomeService(new IncomeRepository(Database.getConnection()));
        this.incomeTableModel = (DefaultTableModel) incomeTable.getModel();

        initData();
    }

    private void initData() {
        try {
            ArrayList<Income> incomes = incomeService.findAll();
            incomeTableModel.setRowCount(0);
            for (Income income : incomes) {
                incomeTableModel.addRow(new Object[]{
                        income.id, income.amount, income.incomeSource.name, income.date, income.description
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
            if (incomeTable.getSelectedRows().length == 0) {
                throw new ValidationException("Please select at least one row");
            } else if (incomeTable.getSelectedRows().length > 1) {
                int ans = JOptionPane.showConfirmDialog(this.mainFrame, "Are you sure want to delete these rows?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION
                );

                if (ans == JOptionPane.YES_OPTION) {
                    int[] selectedRows = incomeTable.getSelectedRows();
                    int[] ids = new int[selectedRows.length];

                    for (int i = 0; i < selectedRows.length; i++) {
                        ids[i] = (int) incomeTableModel.getValueAt(selectedRows[i], 0);
                    }

                    incomeService.deleteMany(ids);

                    JOptionPane.showMessageDialog(this.mainFrame, "Successfully deleted selected rows");
                    this.refreshData();
                }
            } else {
                int ans = JOptionPane.showConfirmDialog(this.mainFrame, "Are you sure want to delete this row?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION
                );

                if (ans == JOptionPane.YES_OPTION) {
                    int selectedID = (int) incomeTableModel.getValueAt(incomeTable.getSelectedRow(), 0);
                    incomeService.delete(selectedID);
                    JOptionPane.showMessageDialog(this.mainFrame, "Successfully deleted selected row");
                    incomeTableModel.removeRow(incomeTable.getSelectedRow());
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
        if (incomeTable.getSelectedRows().length == 0) {
            JOptionPane.showMessageDialog(this.mainFrame, "Please select a row first");
        } else if (incomeTable.getSelectedRows().length > 1) {
            JOptionPane.showMessageDialog(this.mainFrame, "You can only edit one table each time");
        } else {
            int id = (int) incomeTableModel.getValueAt(incomeTable.getSelectedRow(), 0);
            EditIncomeDialog editIncomeDialog = new EditIncomeDialog(this.mainFrame, true, this, id);
            editIncomeDialog.setLocationRelativeTo(this.mainFrame);
            editIncomeDialog.setVisible(true);
        }
    }

    private void addNewIncomeButtonActionPerformed(ActionEvent evt) {
        AddNewIncomeDialog addNewIncomeDialog = new AddNewIncomeDialog(this.mainFrame, true, this);
        addNewIncomeDialog.setLocationRelativeTo(this.mainFrame);
        addNewIncomeDialog.setVisible(true);
    }

    public void refreshData() {
        this.initData();
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        tableContainerPanel = new JPanel();
        incomeTableScrollPane = new JScrollPane();
        incomeTable = new JTable();
        actionPanel = new JPanel();
        addNewIncomeButton = new JButton();
        deleteSelectedRowButton = new JButton();
        editSelectedRowButton = new JButton();

        setLayout(new java.awt.GridBagLayout());

        tableContainerPanel.setLayout(new java.awt.GridBagLayout());

        incomeTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "ID", "Amount", "Income Source", "Date", "Description"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        incomeTableScrollPane.setViewportView(incomeTable);
        if (incomeTable.getColumnModel().getColumnCount() > 0) {
            incomeTable.getColumnModel().getColumn(0).setMinWidth(48);
            incomeTable.getColumnModel().getColumn(0).setPreferredWidth(48);
            incomeTable.getColumnModel().getColumn(0).setMaxWidth(48);
            incomeTable.getColumnModel().getColumn(4).setMinWidth(160);
            incomeTable.getColumnModel().getColumn(4).setPreferredWidth(160);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        tableContainerPanel.add(incomeTableScrollPane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(tableContainerPanel, gridBagConstraints);

        actionPanel.setLayout(new java.awt.GridBagLayout());

        addNewIncomeButton.setText("Add New Income");
        addNewIncomeButton.setMinimumSize(new java.awt.Dimension(105, 32));
        addNewIncomeButton.setPreferredSize(new java.awt.Dimension(148, 32));
        addNewIncomeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addNewIncomeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        actionPanel.add(addNewIncomeButton, gridBagConstraints);

        deleteSelectedRowButton.setText("Delete Selected Rows");
        deleteSelectedRowButton.setMinimumSize(new java.awt.Dimension(105, 32));
        deleteSelectedRowButton.setPreferredSize(new java.awt.Dimension(156, 32));
        deleteSelectedRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
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
            public void actionPerformed(ActionEvent evt) {
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

    private JPanel actionPanel;
    private JButton addNewIncomeButton;
    private JButton deleteSelectedRowButton;
    private JButton editSelectedRowButton;
    private JTable incomeTable;
    private JPanel tableContainerPanel;
    private JScrollPane incomeTableScrollPane;
}
