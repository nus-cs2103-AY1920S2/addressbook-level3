package team.easytravel.model.listmanagers;

import javafx.collections.ObservableList;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpense;

/**
 * Unmodifiable view of a FixedExpenseManager
 */
public interface ReadOnlyFixedExpenseManager {

    /**
     * Returns an unmodifiable view of the fixed expenses list.
     * This list will not contain any duplicate fixed expenses.
     */
    ObservableList<FixedExpense> getFixedExpenseList();

}
