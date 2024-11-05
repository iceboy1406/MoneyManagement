package income.repository;

import income.entity.Income;
import income.entity.IncomeSource;
import transaction.repository.TransactionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class IncomeRepository implements TransactionRepository<Income> {
    private final Connection conn;

    public IncomeRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void add(Income newIncome) throws SQLException {
        String sql = "INSERT INTO incomes (amount, income_source_id, date, description, username) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setDouble(1, newIncome.amount);
            statement.setInt(2, newIncome.incomeSource.id);
            statement.setDate(3, newIncome.date);
            statement.setString(4, newIncome.description);
            statement.setString(5, newIncome.username);
            statement.executeUpdate();
        }
    }

    @Override
    public ArrayList<Income> findAll() throws SQLException {
        Preferences pref = Preferences.userRoot();
        String sql = "SELECT * FROM incomes INNER JOIN income_source ON incomes.income_source_id=income_source.id WHERE username = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, pref.get("username", ""));

            var result = statement.executeQuery();
            ArrayList<Income> incomes = new ArrayList<>();
            while (result.next()) {
                incomes.add(new Income(
                        result.getInt("id"),
                        result.getDouble("amount"),
                        result.getString("description"),
                        result.getDate("date"),
                        result.getString("username"),
                        new IncomeSource(result.getInt("income_source.id"), result.getString("income_source.name"))
                ));
            }
            return incomes;
        }
    }

    @Override
    public Income findById(int id) throws SQLException {
        Preferences pref = Preferences.userRoot();
        String sql = "SELECT * FROM incomes INNER JOIN income_source ON incomes.income_source_id=income_source.id WHERE username = ? AND incomes.id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, pref.get("username", ""));
            statement.setInt(2, id);

            var result = statement.executeQuery();
            if (result.next()) {
                return new Income(
                        result.getInt("id"),
                        result.getDouble("amount"),
                        result.getString("description"),
                        result.getDate("date"),
                        result.getString("username"),
                        new IncomeSource(result.getInt("income_source.id"), result.getString("income_source.name"))
                );
            }

            return null;
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM incomes WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteMany(int[] ids) throws SQLException {
        // Delete multiple ids
        StringBuilder sql = new StringBuilder("DELETE FROM incomes WHERE id IN (");
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
    public void update(Income newIncome) throws SQLException {
        String sql = "UPDATE incomes SET amount = ?, income_source_id = ?, date = ?, description = ? WHERE id = ? AND username = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setDouble(1, newIncome.amount);
            statement.setInt(2, newIncome.incomeSource.id);
            statement.setDate(3, newIncome.date);
            statement.setString(4, newIncome.description);
            statement.setInt(5, newIncome.id);
            statement.setString(6, newIncome.username);
            statement.executeUpdate();
        }
    }
}
