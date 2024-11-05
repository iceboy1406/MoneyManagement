package income.repository;

import income.entity.IncomeSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeSourceRepository {
    private final Connection conn;

    public IncomeSourceRepository(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<IncomeSource> findAll() throws SQLException {
        String sql = "SELECT * FROM income_source ORDER BY name";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            var result = statement.executeQuery();
            ArrayList<IncomeSource> expenseCategories = new ArrayList<>();
            while (result.next()) {
                expenseCategories.add(new IncomeSource(result.getInt("id"), result.getString("name")));
            }
            return expenseCategories;
        }
    }
}
