package seedu.expensela.model.monthlydata;

import java.util.Objects;

/**
 * Represents a User's monthly data for a particular month.
 */
public class MonthlyData {

    private final String id;
    private Budget budget;
    private Expense expense = new Expense("0.0");
    private Income income = new Income("0.0");

    public MonthlyData(String id, Budget budget) {
        this.id = id;
        this.budget = budget;
    }

    public MonthlyData(String id, Budget budget, Expense expense, Income income) {
        this.id = id;
        this.budget = budget;
        this.expense = expense;
        this.income = income;
    }

    public String getId() {
        return id;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public Income getIncome() {
        return income;
    }

    public void setIncome(Income income) {
        this.income = income;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    /**
     * Returns true if both monthly data have the same identity and id.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MonthlyData)) {
            return false;
        }

        MonthlyData otherMonthlyData = (MonthlyData) other;
        return otherMonthlyData.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getId())
                .append(" Expense: ")
                .append(getExpense())
                .append(" Budget: ")
                .append(getBudget())
                .append(" Income: ")
                .append(getIncome());
        return builder.toString();
    }
}
