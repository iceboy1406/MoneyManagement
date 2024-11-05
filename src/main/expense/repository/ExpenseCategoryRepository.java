package expense.repository;

import expense.entity.ExpenseCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExpenseCategoryRepository {
    private final Connection conn;

    public ExpenseCategoryRepository(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<ExpenseCategory> findAll() throws SQLException {
        String sql = "SELECT * FROM expense_category ORDER BY name";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {

            var result = statement.executeQuery();
            ArrayList<ExpenseCategory> expenseCategories = new ArrayList<>();
            while (result.next()) {
                expenseCategories.add(new ExpenseCategory(result.getInt("id"), result.getString("name")));
            }
            return expenseCategories;
        }
    }
}
