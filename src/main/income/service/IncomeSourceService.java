package income.service;

import income.entity.IncomeSource;
import income.repository.IncomeSourceRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeSourceService {
    private final IncomeSourceRepository incomeSourceRepository;

    public IncomeSourceService(IncomeSourceRepository incomeSourceRepository) {
        this.incomeSourceRepository = incomeSourceRepository;
    }

    public ArrayList<IncomeSource> findAll() throws SQLException {
        return this.incomeSourceRepository.findAll();
    }

}
