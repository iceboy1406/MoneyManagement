package expense.repository;

import expense.entity.Expense;
import expense.entity.ExpenseCategory;
import transaction.repository.TransactionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class ExpenseRepository implements TransactionRepository<Expense> {
    private final Connection conn;

    public ExpenseRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void add(Expense newExpense) throws SQLException {
        String sql = "INSERT INTO expenses (amount, category_id, date, description, username) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setDouble(1, newExpense.amount);
            statement.setInt(2, newExpense.category.id);
            statement.setDate(3, newExpense.date);
            statement.setString(4, newExpense.description);
            statement.setString(5, newExpense.username);
            statement.executeUpdate();
        }
    }

    @Override
    public ArrayList<Expense> findAll() throws SQLException {
        Preferences pref = Preferences.userRoot();
        String sql = "SELECT * FROM expenses INNER JOIN expense_category ON expenses.category_id=expense_category.id WHERE username = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, pref.get("username", ""));

            var result = statement.executeQuery();
            ArrayList<Expense> expenses = new ArrayList<>();
            while (result.next()) {
                expenses.add(new Expense(
                        result.getInt("id"),
                        result.getDouble("amount"),
                        result.getString("description"),
                        result.getDate("date"),
                        result.getString("username"),
                        new ExpenseCategory(result.getInt("expense_category.id"), result.getString("expense_category.name"))
                ));
            }
            return expenses;
        }
    }

    @Override
    public Expense findById(int id) throws SQLException {
        Preferences pref = Preferences.userRoot();
        String sql = "SELECT * FROM expenses INNER JOIN expense_category ON expenses.category_id=expense_category.id WHERE username = ? AND expenses.id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, pref.get("username", ""));
            statement.setInt(2, id);

            var result = statement.executeQuery();
            if (result.next()) {
                return new Expense(
                        result.getInt("id"),
                        result.getDouble("amount"),
                        result.getString("description"),
                        result.getDate("date"),
                        result.getString("username"),
                        new ExpenseCategory(result.getInt("expense_category.id"), result.getString("expense_category.name"))
                );
            }

            return null;
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM expenses WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteMany(int[] ids) throws SQLException {
        // Delete multiple ids
        StringBuilder sql = new StringBuilder("DELETE FROM expenses WHERE id IN (");
        for (int i = 0; i < ids.length; i++) {
            sql.append("?");
            if (i != ids.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");

        try (PreparedStatement statement = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < ids.length; i++) {
                statement.setInt(i + 1, ids[i]);
            }
            statement.executeUpdate();
        }

    }

    @Override
    public void update(Expense newExpense) throws SQLException {
        String sql = "UPDATE expenses SET amount = ?, category_id = ?, date = ?, description = ? WHERE id = ? AND username = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setDouble(1, newExpense.amount);
            statement.setInt(2, newExpense.category.id);
            statement.setDate(3, newExpense.date);
            statement.setString(4, newExpense.description);
            statement.setInt(5, newExpense.id);
            statement.setString(6, newExpense.username);
            statement.executeUpdate();
        }
    }
}
