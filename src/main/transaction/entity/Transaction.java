package transaction.entity;

import java.sql.Date;

public class Transaction {
    public int id;
    public double amount;
    public String description;
    public Date date;
    public String username;

    public Transaction(int id, double amount, String description, Date date, String username) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.username = username;
    }
}
