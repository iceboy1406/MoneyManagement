package expense.service;

import config.Database;
import exception.ValidationException;
import expense.entity.Expense;
import expense.repository.ExpenseRepository;
import transaction.service.TransactionService;

import java.sql.SQLException;
import java.util.ArrayList;

public class ExpenseService implements TransactionService<Expense> {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void add(Expense newExpense) throws SQLException, ValidationException {
        try {
            Database.beginTransaction();
            this.expenseRepository.add(newExpense);
            Database.commitTransaction();
        } catch (SQLException e) {
            Database.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public ArrayList<Expense> findAll() throws SQLException, ValidationException {
        return this.expenseRepository.findAll();
    }

    @Override
    public Expense findById(int id) throws SQLException, ValidationException {
        Expense expense = this.expenseRepository.findById(id);
        if (expense == null) throw new ValidationException("Expense not found");

        return expense;
    }

    @Override
    public void delete(int id) throws SQLException, ValidationException {
        if (this.expenseRepository.findById(id) == null)
            throw new ValidationException("Expense with id : " + id + " not found.");
        try {
            Database.beginTransaction();
            this.expenseRepository.delete(id);
            Database.commitTransaction();
        } catch (SQLException e) {
            Database.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public void deleteMany(int[] ids) throws SQLException, ValidationException {
        try {
            Database.beginTransaction();
            this.expenseRepository.deleteMany(ids);
            Database.commitTransaction();
        } catch (SQLException e) {
            Database.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public void update(Expense newExpense) throws SQLException, ValidationException {
        if (this.expenseRepository.findById(newExpense.id) == null)
            throw new ValidationException("Expense with id : " + newExpense.id + " not found.");

        try {
            Database.beginTransaction();
            this.expenseRepository.update(newExpense);
            Database.commitTransaction();
        } catch (SQLException e) {
            Database.rollbackTransaction();
            throw e;
        }
    }
}
