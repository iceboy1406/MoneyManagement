package dashboard.service;

import dashboard.repository.StatisticRepository;

import java.sql.SQLException;
import java.util.prefs.Preferences;

public class StatisticService {
    private final StatisticRepository statisticRepository;
    private final String username;

    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
        this.username = Preferences.userRoot().get("username", "");
    }

    public double getTotalExpense() throws SQLException {
        return this.statisticRepository.getTotalExpense(username);
    }

    public double getTotalIncome() throws SQLException {
        return this.statisticRepository.getTotalIncome(username);
    }

    public double getBalance() throws SQLException {
        return this.statisticRepository.getBalance(username);
    }

}
