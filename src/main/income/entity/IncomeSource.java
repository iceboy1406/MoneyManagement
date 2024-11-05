package income.entity;

public class IncomeSource {
    public int id;
    public String name;

    public IncomeSource(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name; // Return the name of the income source for JComboBox
    }
}
