package team.easytravel.model.listmanagers;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import team.easytravel.model.listmanagers.fixedexpense.Amount;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpense;
import team.easytravel.model.util.uniquelist.UniqueList;

/**
 * Wraps all data at the FixedExpenseManager level
 * Duplicates are not allowed (by.equals comparison)
 */
public class FixedExpenseManager implements ReadOnlyFixedExpenseManager {
    private final UniqueList<FixedExpense> uniqueFixedExpenseLists;

    /**
     * Instantiates a new FixedExpenseManager.
     */
    public FixedExpenseManager() {
        uniqueFixedExpenseLists = new UniqueList<>();
    }

    /**
     * Creates an FixedExpenseManager using the FixedExpenses in the {@code} toBeCopied}
     */
    public FixedExpenseManager(ReadOnlyFixedExpenseManager toBeCopied) {
        uniqueFixedExpenseLists = new UniqueList<>();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the fixed expense list with {@code fixedExpenses}.
     * {@code fixedExpenses} must not contain duplicate fixed expenses.
     */
    public void setFixedExpenses(List<FixedExpense> fixedExpenses) {
        this.uniqueFixedExpenseLists.setElements(fixedExpenses);
    }


    // List overwrite operations

    /**
     * Resets the existing data of this {@code FixedExpenseManager} with {@code newData}
     */
    public void resetData(ReadOnlyFixedExpenseManager newData) {
        requireNonNull(newData);
        setFixedExpenses(newData.getFixedExpenseList());
    }

    // FixedExpense-level operations

    /**
     * Returns true if a fixedExpense with the same identity as {@code fixedExpense} exists in the FixedExpenseManager.
     */
    public boolean hasFixedExpense(FixedExpense fixedExpense) {
        requireNonNull(fixedExpense);
        return uniqueFixedExpenseLists.contains(fixedExpense);
    }

    /**
     * Adds a fixed expense to the FixedExpenseManager.
     * The fixed expense must not already exist in the FixedExpenseManager.
     */
    public void addFixedExpense(FixedExpense f) {
        uniqueFixedExpenseLists.add(f);
    }

    /**
     * Replaces the given fixed expense {@code target} in the list with {@code editedFixedExpense}.
     * {@code target} must exist in the FixedExpenseManager.
     * The person identity of {@code editedFixedExpense} must not be the same as another existing fixed expense
     * in the FixedExpenseManager.
     */
    public void setFixedExpense(FixedExpense target, FixedExpense editedFixedExpense) {
        requireNonNull(editedFixedExpense);

        uniqueFixedExpenseLists.setElement(target, editedFixedExpense);
    }


    /**
     * Remove a single fixed expense from the fixed expense list
     * Removes {@code key} from this {@code FixedExpenseManager}.
     * {@code key} must exist in the FixedExpenseManager.
     */
    public void removeFixedExpense(FixedExpense key) {
        uniqueFixedExpenseLists.remove(key);
    }

    /**
     * Sorts the fixed expense list with the specified comparator.
     */
    public void sortFixedExpenseList(Comparator<FixedExpense> cmp) {
        uniqueFixedExpenseLists.sort(cmp);
    }


    // Util methods

    public String getStatus(double budget) {
        int numOfExpense = uniqueFixedExpenseLists.size();
        if (numOfExpense == 0) {
            return "[❗] There is no expense entered. You can add expense using the \"addexpense\" command.";
        }
        double totalExpense = getTotalExpense();
        if (totalExpense <= budget) {
            return String.format("[✔] Your remaining budget is $%.2f.", budget - totalExpense);
        } else {
            return String.format("[❌] You have exceeded your budget by %.2f!", totalExpense - budget);
        }
    }

    public double getTotalExpense() {
        return uniqueFixedExpenseLists.stream()
                .map(FixedExpense::getAmount)
                .map(Amount::toString)
                .mapToDouble(Double::parseDouble)
                .sum();
    }

    @Override
    public String toString() {
        return uniqueFixedExpenseLists.asUnmodifiableObservableList().size() + " fixed expenses";
        // TODO: refine later
    }

    @Override
    public ObservableList<FixedExpense> getFixedExpenseList() {
        return uniqueFixedExpenseLists.asUnmodifiableObservableList();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FixedExpenseManager // instanceof handles nulls
                && uniqueFixedExpenseLists.equals(((FixedExpenseManager) other).uniqueFixedExpenseLists));
    }

    @Override
    public int hashCode() {
        return uniqueFixedExpenseLists.hashCode();
    }


}
