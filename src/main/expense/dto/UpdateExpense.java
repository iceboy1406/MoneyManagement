package expense.dto;

import expense.entity.Expense;
import expense.entity.ExpenseCategory;

import java.sql.Date;

public class UpdateExpense extends Expense {

    public UpdateExpense(int id, double amount, String description, Date date, String username, ExpenseCategory category) {
        super(id, amount, description, date, username, category);
    }
}
