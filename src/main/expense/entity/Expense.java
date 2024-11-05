package expense.entity;

import transaction.entity.Transaction;

import java.sql.Date;

public class Expense extends Transaction {
    public ExpenseCategory category;

    public Expense(int id, double amount, String description, Date date, String username, ExpenseCategory category) {
        super(id, amount, description, date, username);
        this.category = category;
    }
}
