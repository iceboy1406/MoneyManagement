package expense.dto;

public class DeleteMultipleExpense {
    int[] ids;

    public DeleteMultipleExpense(int[] ids) {
        this.ids = ids;
    }
}
