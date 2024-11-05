package expense.service;

import expense.entity.ExpenseCategory;
import expense.repository.ExpenseCategoryRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class ExpenseCategoryService {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseCategoryService(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    public ArrayList<ExpenseCategory> findAll() throws SQLException {
        return this.expenseCategoryRepository.findAll();
    }
}
