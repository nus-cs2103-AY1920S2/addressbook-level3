package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Month;
import java.time.Year;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.transaction.exceptions.BudgetNotFoundException;

/**
 * A list of Budgets to record individual months' budgets.
 * Supports a limited set of list operations.
 */
public class BudgetList implements Iterable<Budget> {

    private final ObservableList<Budget> internalList = FXCollections.observableArrayList();
    private final ObservableList<Budget> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    private Budget defaultBudget = Budget.getDefault();

    public Budget getDefaultBudget() {
        return defaultBudget;
    }

    public void setDefaultBudget(Budget budget) {
        requireNonNull(budget);
        this.defaultBudget = budget;
    }

    /**
     * Returns true if the list contains an equivalent budget as the given argument.
     */
    public boolean contains(Budget toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the list contains an equivalent budget of the date given.
     */
    public boolean containsBudgetOf(Month month, Year year) {
        requireAllNonNull(month, year);
        Budget tempBudget = new Budget(Amount.zero(), month, year);
        return internalList.stream().anyMatch(tempBudget::dateEquals);
    }

    /**
     * Adds a Budget to the list, replacing if there is a Budget that already exists.
     */
    public void add(Budget toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Returns a budget of the {@code month} and {@code year}. If the individual budget doesn't exist, the default
     * budget is returned.
     */
    public Budget get(Month month, Year year) {
        requireAllNonNull(month, year);

        for (Budget b : internalList) {
            if (b.getMonth().equals(month) && b.getYear().equals(year) && !b.getAmount().isZero()) {
                return b;
            }
        }

        return defaultBudget;
    }

    /**
     * Replaces the budget {@code target} in the list with {@code editedBudget}.
     * {@code target} must exist in the list.
     */
    public void setBudget(Budget target, Budget editedBudget) {
        requireAllNonNull(target, editedBudget);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BudgetNotFoundException();
        }

        internalList.set(index, editedBudget);
    }

    /**
     * Replaces the contents of this list with {@code budgets}.
     */
    public void setBudgets(List<Budget> budgets) {
        requireAllNonNull(budgets);
        internalList.setAll(budgets);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Budget> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Budget> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof BudgetList // instanceof handles nulls
                && internalList.equals(((BudgetList) other).internalList)
                && defaultBudget.equals(((BudgetList) other).getDefaultBudget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalList, defaultBudget);
    }
}
