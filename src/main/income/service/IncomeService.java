package income.service;

import config.Database;
import exception.ValidationException;
import income.entity.Income;
import income.repository.IncomeRepository;
import transaction.service.TransactionService;

import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeService implements TransactionService<Income> {
    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public void add(Income newIncome) throws SQLException, ValidationException {
        try {
            Database.beginTransaction();
            this.incomeRepository.add(newIncome);
            Database.commitTransaction();
        } catch (SQLException e) {
            Database.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public ArrayList<Income> findAll() throws SQLException, ValidationException {
        return this.incomeRepository.findAll();
    }

    @Override
    public Income findById(int id) throws SQLException, ValidationException {
        Income income = this.incomeRepository.findById(id);
        if (income == null) throw new ValidationException("Income not found");

        return income;
    }

    @Override
    public void delete(int id) throws SQLException, ValidationException {
        if (this.incomeRepository.findById(id) == null)
            throw new ValidationException("Income with id : " + id + " not found.");
        try {
            Database.beginTransaction();
            this.incomeRepository.delete(id);
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
            this.incomeRepository.deleteMany(ids);
            Database.commitTransaction();
        } catch (SQLException e) {
            Database.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public void update(Income newIncome) throws SQLException, ValidationException {
        if (this.incomeRepository.findById(newIncome.id) == null)
            throw new ValidationException("Income with id : " + newIncome.id + " not found.");

        try {
            Database.beginTransaction();
            this.incomeRepository.update(newIncome);
            Database.commitTransaction();
        } catch (SQLException e) {
            Database.rollbackTransaction();
            throw e;
        }
    }
}
