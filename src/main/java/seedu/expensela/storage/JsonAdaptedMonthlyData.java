package seedu.expensela.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.expensela.commons.exceptions.IllegalValueException;
import seedu.expensela.model.monthlydata.Budget;
import seedu.expensela.model.monthlydata.Expense;
import seedu.expensela.model.monthlydata.Income;
import seedu.expensela.model.monthlydata.MonthlyData;

/**
 * Jackson-friendly version of {@link MonthlyData}.
 */
public class JsonAdaptedMonthlyData {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "MonthlyData %s field is missing!";

    private final String budget;
    private final String expense;
    private final String income;

    /**
     * Constructs a {@code JsonAdaptedMonthlyData} with the given transaction details.
     */
    @JsonCreator
    public JsonAdaptedMonthlyData(@JsonProperty("budget") String budget, @JsonProperty("expense") String expense,
                                  @JsonProperty("income") String income) {
        this.budget = budget;
        this.expense = expense;
        this.income = income;
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedMonthlyData(MonthlyData source) {
        budget = source.getBudget().budgetAmount.toString();
        expense = source.getExpense().expenseAmount.toString();
        income = source.getIncome().incomeAmount.toString();
    }

    /**
     * Converts this Jackson-friendly adapted monthly data object into the model's {@code MonthlyData} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transaction.
     */
    public MonthlyData toModelType() throws IllegalValueException {

        if (budget == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Budget.class.getSimpleName()));
        }
        if (!Budget.isValidAmount(budget)) {
            throw new IllegalValueException(Budget.MESSAGE_CONSTRAINTS);
        }
        final Budget modelBudget = new Budget(budget);

        if (expense == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Expense.class.getSimpleName()));
        }
        if (!Expense.isValidAmount(expense)) {
            throw new IllegalValueException(Expense.MESSAGE_CONSTRAINTS);
        }
        final Expense modelExpense = new Expense(expense);

        if (income == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Income.class.getSimpleName()));
        }
        if (!Income.isValidAmount(income)) {
            throw new IllegalValueException(Income.MESSAGE_CONSTRAINTS);
        }
        final Income modelIncome = new Income(income);

        return new MonthlyData("1", modelBudget, modelExpense, modelIncome);
    }
}
