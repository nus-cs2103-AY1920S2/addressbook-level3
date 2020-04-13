package seedu.expensela.testutil;

import seedu.expensela.model.monthlydata.Budget;
import seedu.expensela.model.monthlydata.Expense;
import seedu.expensela.model.monthlydata.Income;
import seedu.expensela.model.monthlydata.MonthlyData;

/**
 * A utility class to help with building Monthly Data objects.
 */
public class MonthlyDataBuilder {

    public static final String DEFAULT_BUDGET = "1000.00";
    public static final String DEFAULT_INCOME = "1200.00";
    public static final String DEFAULT_EXPENSE = "0.00";
    public static final String DEFAULT_ID = "1";

    private String id;
    private Budget budget;
    private Expense expense;
    private Income income;

    public MonthlyDataBuilder() {
        id = DEFAULT_ID;
        budget = new Budget(DEFAULT_BUDGET);
        expense = new Expense(DEFAULT_EXPENSE);
        income = new Income(DEFAULT_INCOME);
    }

    /**
     * Initializes the MonthlyDataBuilder with the data of {@code monthlyDataToCopy}.
     */
    public MonthlyDataBuilder(MonthlyData monthlyDataToCopy) {
        id = monthlyDataToCopy.getId();
        budget = monthlyDataToCopy.getBudget();
        expense = monthlyDataToCopy.getExpense();
        income = monthlyDataToCopy.getIncome();
    }

    /**
     * Sets the {@code Name} of the {@code Transaction} that we are building.
     */
    public MonthlyDataBuilder withId(String id) {
        this.id = id;
        return this;
    }
    /**
     * Sets the {@code Budget} of the {@code MonthlyData} that we are building.
     */
    public MonthlyDataBuilder withBudget(String budget) {
        this.budget = new Budget(budget);
        return this;
    }

    /**
     * Sets the {@code Expense} of the {@code MonthlyData} that we are building.
     */
    public MonthlyDataBuilder withExpense(String expense) {
        this.expense = new Expense(expense);
        return this;
    }

    /**
     * Sets the {@code Income} of the {@code MonthlyData} that we are building.
     */
    public MonthlyDataBuilder withIncome(String income) {
        this.income = new Income(income);
        return this;
    }

    public MonthlyData build() {
        return new MonthlyData(id, budget, expense, income);
    }
}
