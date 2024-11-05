package dashboard.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatisticRepository {
    private Connection conn;

    public StatisticRepository(Connection conn) {
        this.conn = conn;
    }

    public double getTotalExpense(String username) throws SQLException {
        String sql = "SELECT SUM(amount) as total_expense FROM expenses WHERE username = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            var result = statement.executeQuery();
            if (result.next()) {
                return result.getDouble("total_expense");
            }

            return 0;
        }
    }

    public double getTotalIncome(String username) throws SQLException {
        String sql = "SELECT SUM(amount) as total_income FROM incomes WHERE username = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            var result = statement.executeQuery();
            if (result.next()) {
                return result.getDouble("total_income");
            }

            return 0;
        }
    }

    public double getBalance(String username) throws SQLException {
        return getTotalIncome(username) - getTotalExpense(username);
    }
}
