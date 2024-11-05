package expense.dto;

import expense.entity.Expense;
import expense.entity.ExpenseCategory;

import java.sql.Date;

public class AddNewExpense extends Expense {

    public AddNewExpense(double amount, String description, Date date, String username, ExpenseCategory category) {
        super(-1, amount, description, date, username, category);
    }
}
