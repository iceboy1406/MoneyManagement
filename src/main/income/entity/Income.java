package income.entity;

import transaction.entity.Transaction;

import java.sql.Date;

public class Income extends Transaction {
    public IncomeSource incomeSource;

    public Income(int id, double amount, String description, Date date, String username, IncomeSource incomeSource) {
        super(id, amount, description, date, username);
        this.incomeSource = incomeSource;
    }

    public Income(double amount, String description, Date date, String username, IncomeSource incomeSource) {
        super(-1, amount, description, date, username);
        this.incomeSource = incomeSource;
    }

}
