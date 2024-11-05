package expense.entity;

public class ExpenseCategory {
    public int id;
    public String name;

    public ExpenseCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name; // Display name in JComboBox
    }
}
