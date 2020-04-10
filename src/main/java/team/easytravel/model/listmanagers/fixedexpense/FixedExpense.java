package team.easytravel.model.listmanagers.fixedexpense;

import java.util.Objects;

import team.easytravel.model.util.uniquelist.UniqueListElement;

/**
 * Represent a FixedExpense in the FixedExpenseManager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class FixedExpense implements UniqueListElement {

    private final Amount amount;
    private final Description description;
    private final FixedExpenseCategory fixedExpenseCategory;

    public FixedExpense(Amount amount, Description description, FixedExpenseCategory fixedExpenseCategory) {
        this.amount = amount;
        this.description = description;
        this.fixedExpenseCategory = fixedExpenseCategory;
    }

    public Amount getAmount() {
        return amount;
    }

    public Description getDescription() {
        return description;
    }

    public FixedExpenseCategory getFixedExpenseCategory() {
        return fixedExpenseCategory;
    }

    /**
     * Returns true if this fixed expense is equal to {@code other} as per {@link #equals(Object)}.
     * This defines an equality between two fixed expenses.
     */
    @Override
    public boolean isSame(UniqueListElement other) {
        return equals(other);
    }

    /**
     * Returns true if both persons have the same data fields.
     * This defines a notion of equality between two fixed expenses.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FixedExpense)) {
            return false;
        }

        FixedExpense otherFixedExpense = (FixedExpense) other;
        return amount.equals(otherFixedExpense.amount)
                && description.equals(otherFixedExpense.description)
                && fixedExpenseCategory.equals(otherFixedExpense.fixedExpenseCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, description, fixedExpenseCategory);
    }

    @Override
    public String toString() {
        return "Fixed Expense Entry - Description: " + description
                + " Amount: " + amount
                + " Category: " + fixedExpenseCategory;
    }
}
